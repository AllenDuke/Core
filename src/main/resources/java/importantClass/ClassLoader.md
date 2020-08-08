# ClassLoader

## 类加载机制
1. 加载，字节流读取。
2. 验证，验证是否完备、合法之类的。
3. 准备，为static变量分配内存，设定初值0。
4. 解析，将符号引用转化为直接引用。
5. 初始化，执行用户代码的初始化，为static变量设定用户代码指定的初值，执行static代码块。
## 双亲委派原则
1. 遵循双亲委派原则指的是，当类加载器要加载一个类的时候，首先是委托自己的父类加载器去加载，然后返回。有如下好处
   * 防止同一份字节码被多次加载。
   * 安全，防止被篡改，因为篡改后的的核心类即使加载了也是与原来的不相等的。
   * 注意：即使是同一份字节码，不同的类加载器加载出来的Class对象不相等。当类型转换时，会抛出ClassCastException。
2. 打破双亲委派原则的目的：
   * 为了隔离不同的应用相互之间的影响，如tomcat。如果A用的Spring3,B用的Spring5，这个时候如果是遵守双亲委派机制的话，
   只会加载一次Spring jar包，这样使得两者的某一个jar引用出错而启动失败。（每个web应用都有自己的自定义的类加载器）
### ClassLoader源码分析
loadClass方法
```java
    protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);//判断是否已经加载过了
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {//如果有父加载器，那么由父加载器去加载
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {捕捉从父加载器抛出的异常
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }
                //如果没有父加载器，父加载器没有加载过，那么自己去findClass，因此自定义类加载器要重写findClass
                //因为父类的findClass只是简单低抛出ClassNotFoundExcption
                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);//当来到顶层加载器时，如果也还没有加载过，那么从顶层开始抛出异常

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {//是否进行链接，即对静态变量进行赋初值和执行静态代码块
                resolveClass(c);
            }
            return c;
        }
    }
```
findClass方法
```java
    protected Class<?> findClass(String name) throws ClassNotFoundException {//简单地抛异常
        throw new ClassNotFoundException(name);//自定义类加载器要重写此方法
    }
```
## 只重写findClass遵循双亲委派机制
```java
    @Override
    public Class<?> findClass(String name) {//当父加载器没有加载过时，那么自己去findClass
        try {
            byte[] result = getClassByte(name);
            if (result == null) {
                System.out.println("cloudnot find "+name+" maybe loaded by parent");
            } else {
                return defineClass(name, result, 0, result.length);//把字节码交由虚拟机去定义类
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
```
## 重写loadClass打破双亲委派机制
```java
     @Override
     public Class<?> loadClass(String name) throws ClassNotFoundException {//不再如上检查父类是否加载过
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            if (inputStream == null) {
                return super.loadClass(name);
            } else {
                try {
                    byte[] buff = new byte[inputStream.available()];
                    inputStream.read(buff);
                    return defineClass(name, buff, 0, buff.length);//同样是找到字节码交由虚拟机定义
                } catch (IOException e) {
                    throw new ClassNotFoundException(e.toString());
                }
            }
     }
```
## Class.forName与loadClass
Class.forName(className)方法，内部实际调用的方法是  Class.forName(className,true,classloader);

第2个boolean参数表示类是否需要初始化，  Class.forName(className)默认是需要初始化。

一旦初始化，就会触发目标对象的 static块代码执行，static参数也也会被再次初始化。

    

ClassLoader.loadClass(className)方法，内部实际调用的方法是  ClassLoader.loadClass(className,false);

第2个 boolean参数，表示目标对象是否进行链接，false表示不进行链接，由上面介绍可以，

不进行链接意味着不进行包括初始化等一些列步骤，那么静态块和静态对象就不会得到执行                                                                                                                                           再看ClassLoader.loadClass(classN