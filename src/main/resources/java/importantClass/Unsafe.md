# Unsafe
这是jdk提供的可以直接操作内存的类，下面通过原子类来窥探。
## AtomicInteger
```java
public class AtomicInteger extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807050L;

    // setup to use Unsafe.compareAndSwapInt for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();//用户只能通过反射来创建Unsafe类
    private static final long valueOffset;//静态的 值在对象中的偏移量

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;

    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }

    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    //Unsafe中
    public final int getAndAddInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));//自旋加1

        return var5;
    }

    //Unsafe中
    public native int getIntVolatile(Object var1, long var2);//对象，域的偏移量

    //Unsafe中
    public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
```