# Concurency
参考自：https://www.jianshu.com/p/506c1e38a922

并发包的设计在于volatile与cas。（ConcurrentHashMap中也使用到了synchronized）

由于Java的CAS同时具有volatile读和volatile写的内存语义，因此Java线程之间的通信现在有了下面4种方式。
1. A线程写volatile变量，随后B线程读这个volatile变量。
2. A线程写volatile变量，随后B线程用CAS更新这个volatile变量。
3. A线程用CAS更新一个volatile变量，随后B线程用CAS更新这个volatile变量。
4. A线程用CAS更新一个volatile变量，随后B线程读这个volatile变量。
## volatile
首先应该知道的是，每个cpu都有自己的缓存，而当cpu没有命中缓存的时候，就会去主存中读取数据，然后放到缓存。

所以这是一个cpu的同步、缓存一致性问题。（类似的有分布式下，redis与mysql缓存一致性）

如上，A线程修改了volatile变量时，会锁定其他cpu下该变量所在的缓存行（或者锁定总线，不让访问内存），不让其他cpu访问该变量，让
这次修改成原子性。修改的时候，因为遵循MESI缓存一致性协议，其他cpu会通过嗅探在总线上传播的数据来检查自己缓存的数据有效性，
当发现自己缓存行对应的内存地址的数据被修改，就会将该缓存行设置成无效状态，当下次CPU读取该变量时，发现所在的缓存行被设置为无效，
就会重新从内存中读取数据到缓存中。
### 应用
1. 多线程之间的通信。（可见性，JMM只是一种概念，单核cpu是没有这种忧虑的）
2. 并发单例double-check。（防止指令重排序）
### java层面 volatile关键字
```java
public class VolatileTest {

    static volatile int i;

    public static void main(String[] args){
        i = 10;
    }
}
```
### 字节码层面
```java
0 bipush 10
2 putstatic #2 <example/java/VolatileTest.i>
5 return
```
并没有发现什么异样，但可以看到变量i被标记成了volatile。

![volatile](../images/Volatile.PNG)jclasslib工具
![volatile](../images/VolatileJavap.PNG)javap命令
### jvm层面、内存屏障，汇编层面，lock指令
accessFlags.hpp文件

![accessFlages](../images/accessFlags.PNG)

通过is_volatile()可以判断一个变量是否被volatile修饰，然后再全局搜"is_volatile"被使用的地方，
最后在bytecodeInterpreter.cpp文件中，找到putstatic字节码指令的解释器实现，里面有is_volatile()方法。

![isVolatile](../images/isVolatile.PNG)

其中cache变量是java代码中变量i在常量池缓存中的一个实例，因为变量i被volatile修饰，所以cache->is_volatile()为真，
给变量i的赋值操作由release_int_field_put方法实现。

![releaseField](../images/releaseField.PNG)
![releaseStore](../images/releasStore.PNG)

其中 jint* p指向的是java变量i（即p存放的是i的地址），而c++关键字volatile的作用同样是禁止指令优化（重排序），保持可见性。

这里我感觉原文说的有点混乱，忽略了c++ volatile这一层或者说与jvm中内联汇编代码混为一谈了。以下给出我的看法。
1. release_store**方法内**的指针p，使用了volatile修饰，其实只起到了防止重排序的作用，因为它是线程私有的，
每个线程执行到这个函数时，都会拷贝一个形参p（指向变量i）。
2. 这个时候对i的赋值只是写到了各自的缓存。
3. 赋值后插入一个storeload内存屏障，

![storeload](../images/storeload.PNG)
![fence](../images/fence.PNG)
![lock](../images/lock.PNG)

lock指令可以禁止两端指令重排序，同时将缓存中的数据立即写回主存（保留观点），写的时候禁止别人访问该缓存行（或者锁定总线），
这个时候其他cpu会嗅探（如上），当自己写完之后，会重新从主存中加载值。
### 完整的内存屏障，都有防止指令重排序的作用
保守策略下：
1. 在每个volatile写操作的前面插入一个StoreStore屏障。
2. 在每个volatile写操作的后面插入一个StoreLoad屏障。//将数据写回，重新从主存中读取
3. 在每个volatile读操作的前面插入一个LoadLoad屏障。//相当于只有指令重排序的作用
4. 在每个volatile读操作的后面插入一个LoadStore屏障。
很多时候编译器也可以删除某些屏障，但不会删除StoreLoad。
## Synchronized
## 应用
多线程之间的通信，同步。
### java层面 synchronized关键字
synchronized对象
```java
public class SynchronizedTest {

    public static void main(String[] args) {
        SynchronizedTest o=new SynchronizedTest();
        synchronized (o){}//这个对象可以是普通对象，也可以是Class对象，如synchronized(SynchronizedTest.class)，一样的
    }
}
```
synchronized加在方法上
```java
public class SynchronizedTest {

    public synchronized void synchronizedMember(){//加在静态方法上也一样
        int i=1;
    }
}
```
### 字节码层面
synchronized对象

![synchronizedObject](../images/synchronizedObject.PNG)

synchronized加在方法上

![synchronizedMethod](../images/synchronizedMethod.PNG)
### jvm层面、内存屏障，汇编层面，lock指令
#### synchronized的可见性由内存屏障提供

![synchronizedBarrier](../images/synchronizedBarrier.PNG)

![acquire](../images/acquire.PNG)
![release](../images/release.PNG)

疑问：这里所说的避免多个线程命中同一行缓存究竟是什么意思？难道真的正如下面 **MESI与CAS** 所说，所谓的将数据写回主存只不过是在自己
的缓存修改，改变自己缓存的状态，然后发出invlidate信号交给总线去裁定是否可以将其他cpu的缓存行置为无效？

1. 在进入同步区域前，清空给缓存中的共享变量。acquire
2. 同步区域内要读取共享变量时从主存中读取。
3. 同步区域内是可以进行重排序的。（所以double-check要使用volatile变量来做判断，否则线程可能会拿到半初始化状态的对象）
4. 在离开同步区域时，将数据写回主存。release
#### 轻量级锁cas自旋阶段有用到lock cmpxchg指令
### 锁升级
![synchronizedUpdate](../images/synchronizedUpdate.PNG)

## MESI
### 锁总线
1. Lock前缀指令的实现最终都会引起处理器缓存回写到内存，但在实现数据一致方面却可能有一定差异
2. Lock前缀指令导致在执行指令期间，声言处理器的 LOCK# 信号。多处理器环境中，LOCK# 信号确保声言期间，处理器可独占使用任何共享内存。
它会锁住总线，导致其他CPU不能访问总线，不能访问总线就意味着不能访问系统内存，但是在最近的处理器里，LOCK＃信号一般不锁总线，
而是锁缓存，毕竟锁总线开销比较大
3. 对于Intel486和Pentium处理器，在锁操作时，总是在总线上声言LOCK#信号。
### 缓存行状态
1. x86架构的每个缓存块的大小为64 bytes，称为缓存行（ cache-line，引出缓存行对齐的问题）。其它种类的处理器的缓存行大小可能不同。
更大的缓存行容量降低延迟，但是需要更大的带宽。
2. MESI 是指4中状态的首字母。每个缓存行有4个状态，可用2个字节表示，它们分别是：

![mesi](../images/mesi.PNG)
1. MESI要求cpu间协同工作，关键在于一个处理器写缓存时可以让另一个cpu的缓存无效，缓存写入内存后还可以进行数据同步
2. 例，假设有三个CPU A、B、C，对应三个缓存分别是cache a、b、 c。在主内存中定义了x的引用值为0
```java
读取缓存：
CPU A发出了一条指令，从主内存中读取x。
CPU A从主内存通过bus读取到 cache a中并将该cache line 设置为E状态。
CPU B发出了一条指令，从主内存中读取x。
CPU B试图从主内存中读取x时，CPU A检测到了地址冲突。这时CPU A对相关数据做出响应。此时x 存储于cache a和cache b中，x在chche a和cache b中都被设置为S状态(共享)

修改数据：
CPU A 计算完成后发指令需要修改x.
CPU A 将x设置为M状态（修改）并通知缓存了x的CPU B, CPU B将本地cache b中的x设置为I状态(无效)
CPU A 对x进行赋值

同步数据
CPU B 发出了要读取x的指令。
CPU B 通知CPU A,CPU A将修改后的数据同步到主内存时cache a 修改为E（独享）
CPU A同步CPU B的x,将cache a和同步后cache b中的x设置为S状态（共享）
```
### MESI与CAS的关系（synchronized轻量级锁的阶段也有cas自旋）
在x86架构上，CAS被翻译为”lock cmpxchg...“，当两个core同时执行针对同一地址的CAS指令时,
其实他们是在试图修改每个core自己持有的Cache line。

假设两个core都持有相同地址对应cacheline,且各自cacheline 状态为S, 这时如果要想成功修改,就首先需要把S转为E或者M, 
则需要向其它core invalidate 这个地址的cacheline,则两个core都会向ring bus发出 invalidate这个操作, 
那么在ringbus上就会根据特定的设计协议仲裁是core0,还是core1能赢得这个invalidate, 胜者完成操作, 失败者需要接受结果, 
invalidate自己对应的cacheline,再读取胜者修改后的值, 回到起点.

对于我们的CAS操作来说, 其实锁并没有消失,只是转嫁到了ring bus的总线仲裁协议中. 
而且大量的多核同时针对一个地址的CAS操作会引起反复的互相invalidate 同一cacheline, 造成pingpong效应, 同样会降低性能。
当然如果真的有性能问题，我觉得这可能会在ns级别体现了,一般的应用程序中使用CAS应该不会引起性能问题。
