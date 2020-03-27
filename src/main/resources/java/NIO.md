# NIO
## Channel(相当于内核缓冲区)
## Buffer（相当于用户缓冲区，MappedByteBuffer相当于mmap）
## Selector
1. select()阻塞或者超时等待，对应epoll_wait
2. wakeup()别的线程调用后，可唤醒在select中阻塞的线程。
   * 原理：JavaNIO 中的Channel时用Linux中的管道创建的，一个管道产生两个文件描述符，JavaNIO把它分为读文件描述符和写文件描述符，
   而后把读文件描述符通过epoll_ctl注册到红黑树上。当调用wakeup时，会通过写文件描述符，
   往channel（Liunx管道）中写入一个字节标记信息，然后就可以唤醒在select()中阻塞的线程了。
   * 管道：从管道读数据是一次性操作，数据一旦被读，它就从管道中被抛弃，释放空间以便写更多的数据。所以如果先调用wakeup那么下一次
   调用select()的时候会立即返回，如果先调用select()那么在select期间，多次调用wakeup()产生的效果与调用一次是一样的，
   因为后面的调用将不会满足唤醒条件。
   * windows的实现与linux的实现：windows环境下wakeup()的实现原理，它通过一个可写的SinkChannel和一个可读的SourceChannel组成的
   pipe来实现唤醒的功能，而Linux环境则使用其本身的Pipe来实现唤醒功能。无论windows还是linux，wakeup的思想是完全一致的，
   只不过windows没有Pipe这种信号通知的机制，所以通过TCP来实现了Pipe，建立了一对自己和自己的loopback的TCP连接来发送信号。
   请注意，每创建一个Selector对象，都会创建一个Pipe实例，这会导致消耗两个文件描述符FD和两个端口号，
   实际开发中需注意端口号和文件描述符的限制。