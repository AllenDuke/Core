# NIO
## Channel(相当于内核缓冲区)
## Buffer（相当于用户缓冲区，MappedByteBuffer相当于mmap）
### DirectByteBuffer
构造方法
```java
    DirectByteBuffer(int cap) {                   // package-private

        super(-1, 0, cap, cap);
        boolean pa = VM.isDirectMemoryPageAligned();
        int ps = Bits.pageSize();
        long size = Math.max(1L, (long)cap + (pa ? ps : 0));
        //空间不足时，会进行一定次数的System.gc()，目的是为了释放此前的已成为垃圾的堆外内存，
        //它会中断进程100ms,如果在这100ms的之间，系统未完成GC，仍会抛出OOM。
        //JVM启动参数DisableExplicitGC会使System.gc()无效。
        Bits.reserveMemory(size, cap);

        long base = 0;
        try {
            base = unsafe.allocateMemory(size);
        } catch (OutOfMemoryError x) {
            Bits.unreserveMemory(size, cap);
            throw x;
        }
        unsafe.setMemory(base, size, (byte) 0);
        if (pa && (base % ps != 0)) {
            // Round up to page boundary
            address = base + ps - (base & (ps - 1));
        } else {
            address = base;
        }
        //当堆内的DirectByteBuffer引用被回收时，cleaner负责堆外内存的释放
        cleaner = Cleaner.create(this, new Deallocator(base, size, cap));//cleaner负责堆外内存的释放
        att = null;



    }
``` 
Deallocator 
```java
    private static class Deallocator
        implements Runnable
    {

        public void run() {
            if (address == 0) {
                // Paranoia
                return;
            }
            unsafe.freeMemory(address);//释放堆外内存
            address = 0;
            Bits.unreserveMemory(size, capacity);
        }

    }
```
Cleaner
```java
public class Cleaner extends PhantomReference<Object> {
    private static final ReferenceQueue<Object> dummyQueue = new ReferenceQueue();//静态常量 引用队列
    private static Cleaner first = null;//静态
    private Cleaner next = null;//双向链表结构
    private Cleaner prev = null;
    private final Runnable thunk;//Deallocator 清理的逻辑

    private Cleaner(Object var1, Runnable var2) {
        super(var1, dummyQueue);//cleaner弱引用着DirectByteBuffer
        this.thunk = var2;
    }

    private static synchronized Cleaner add(Cleaner var0) {
        if (first != null) {
            var0.next = first;
            first.prev = var0;
        }

        first = var0;
        return var0;
    }

    public static Cleaner create(Object var0, Runnable var1) {
        return var1 == null ? null : add(new Cleaner(var0, var1));
    }


//如果该DirectByteBuffer对象在一次GC中被回收了，在下一次FGC时，Cleaner对象会被JVM挂到PendingList上。
// 然后有一个固定的线程扫描这个List，如果遇到Cleaner对象，那么就执行clean方法。
    public void clean() {
        if (remove(this)) {//从双向链表中移除
            try {
                this.thunk.run();//释放堆外内存
            } catch (final Throwable var2) {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        if (System.err != null) {
                            (new Error("Cleaner terminated abnormally", var2)).printStackTrace();
                        }

                        System.exit(1);
                        return null;
                    }
                });
            }

        }
    }
}
```
应用与回收
```java
  public static void clean(final ByteBuffer byteBuffer) { 
    if (byteBuffer.isDirect()) { 
      ((DirectBuffer)byteBuffer).cleaner().clean(); //释放堆外内存
    } 
 } 
  public static void sleep(long i) { 
    try { 
       Thread.sleep(i); 
     }catch(Exception e) { 
       /*skip*/
     } 
  } 
  public static void main(String []args) throws Exception { 
      ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 200); 
      System.out.println("start"); 
      sleep(5000); 
      clean(buffer);//执行垃圾回收
//     System.gc();//执行Full gc进行垃圾回收
      System.out.println("end"); 
      sleep(5000); 
  }
```
## Selector
1. select()阻塞或者超时等待，对应epoll_wait
2. wakeup()别的线程调用后，可唤醒在select中阻塞的线程。
   * 原理：JavaNIO 中的Channel时用Linux中的管道创建的，用两个文件描述符来创建管道，JavaNIO把它分为读文件描述符（socket描述符）
   和写文件描述符，而后把读文件描述符通过epoll_ctl注册到红黑树上。当调用wakeup时，会通过写文件描述符，
   往channel（Liunx管道）中写入一个字节标记信息，然后就可以唤醒在select()中阻塞的线程了。
   * 管道：从管道读数据是一次性操作，数据一旦被读，它就从管道中被抛弃，释放空间以便写更多的数据。所以如果先调用wakeup那么下一次
   调用select()的时候会立即返回，如果先调用select()那么在select期间，多次调用wakeup()产生的效果与调用一次是一样的，
   因为后面的调用将不会满足唤醒条件。
   * windows的实现与linux的实现：windows环境下wakeup()的实现原理，它通过一个可写的SinkChannel和一个可读的SourceChannel组成的
   pipe来实现唤醒的功能，而Linux环境则使用其本身的Pipe(命名管道)来实现唤醒功能。无论windows还是linux，wakeup的思想是完全一致的，
   只不过windows没有Pipe这种信号通知的机制，所以通过TCP来实现了Pipe，建立了一对自己和自己的loopback的TCP连接来发送信号。
   请注意，每创建一个Selector对象，都会创建一个Pipe实例，这会导致消耗两个文件描述符FD和两个端口号，
   实际开发中需注意端口号和文件描述符的限制。