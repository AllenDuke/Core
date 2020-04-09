## ThreadLocal用法
```java
ThreadLocal<String> localName = new ThreadLocal();
localName.set("占小狼");
String name = localName.get();
```
### get set
```java 
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}

public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```
## ThreadLocalMap
### 线程的实例数据
位于线程类中
```java
ThreadLocal.ThreadLocalMap threadLocals = null;
```
### hash冲突
冲突时比较下一个（线性探测法），故而数据量大，冲突严重时，性能低下
```java
private void set(ThreadLocal<?> key, Object value) {
    Entry[] tab = table;
    int len = tab.length;
    int i = key.threadLocalHashCode & (len-1);

    for (Entry e = tab[i];
         e != null;
         e = tab[i = nextIndex(i, len)]) {
        ThreadLocal<?> k = e.get();

        if (k == key) {
            e.value = value;
            return;
        }

        if (k == null) {
            replaceStaleEntry(key, value, i);
            return;
        }
    }

    tab[i] = new Entry(key, value);
    int sz = ++size;
    if (!cleanSomeSlots(i, sz) && sz >= threshold)
        rehash();
}
```
### Entry 封装ThreadLocal与set定的值 内存泄漏
```java
static class Entry extends WeakReference<ThreadLocal<?>> {
    /** The value associated with this ThreadLocal. */
    Object value;

    Entry(ThreadLocal<?> k, Object v) {
        super(k);//key成了一个弱引用
        value = v;
    }
}
```
key被保存到了WeakReference对象中，这就导致了一个问题，ThreadLocal在**没有被外部强引用**时，发生GC时会被回收。

例如，ThreadLocal是在方法内创建然后set(value)，GC时，该方法对应的栈帧还在栈上，那么改Entry不会被回收，因为引用着强引用value, 
但是作为弱引用的key却被回收了，现在key=null。那么GC完毕后，再也不能用这个key去把value查出来，
但是Entry与value这种强引用关系还存在着，那么这块内存就再也回收不了。

![ThreadLocal](../images/ThreadLocal.png)

线程类的成员变量threadLocals强引用着ThreadLocalMap，ThreadLocalMap强引用着Entry数组，而Entry弱引用着ThreadLocal。
gc时会回收弱引用。
### 避免内存泄漏
```java
ThreadLocal<String> localName = new ThreadLocal();
try {
    localName.set("占小狼");
    // 其它业务逻辑
} finally {
    localName.remove();//使用完及时删除
}
```
## ThreadLocal在Spring事务管理中的应用
spring中使用ThreadLocal来设计TransactionSynchronizationManager类，实现了事务管理与数据访问服务的解耦，
同时也保证了多线程环境下connection的线程安全问题。

TransactionSynchronizationManager首先从数据库连接池中获得一个connection，并构造一个connection包装类，使用这个包装类开启事务，
最后通过TransactionSynchronizationManager将connection与ThreadLocal绑定，事务提交或者回滚后，解除绑定。
## 四种引用
1. 强引用。gc后仍发现内存不足，这样宁愿抛出OOM也不会回收强引用。
2. 软引用。gc后仍发现内存不足，这样会回收软引用。可用于缓存，当内存足够，可以正常的拿到缓存，当内存不够，就会先干掉缓存，
不至于马上抛出OOM。。
3. 弱引用。gc时回收，例如ThreadLocal.
4. 虚引用。get不到，常用于堆外内存的管理、释放，例如DirectByteBuffer的Cleaner。
    * 当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在垃圾回收后，将这个虚引用加入引用队列，在其关联的虚引用出队前，
    不会彻底销毁该对象。 所以可以通过检查引用队列中是否有相应的虚引用来判断对象是否已经被回收了。
    


