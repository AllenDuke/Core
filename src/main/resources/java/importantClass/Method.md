# Method
```java
public class MethodInvokeTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Class<MethodInvokeTest> methodInvokeTestClass = MethodInvokeTest.class;
        //每次getMethod都会copy、产生新对象，所以最好缓存下来
        Method test = methodInvokeTestClass.getMethod("test", String.class);
        /**
         * 当invoke次数少于等于15时，使用的是native方法调用
         * 当invoke次数超过15时，将生成一个类，类似动态代理一样的机制，生成文件并加载耗时
         * 因此，在这里要注意并发，假如有1000个线程都进入到创建GeneratedMethodAccessorXXX的逻辑里，
         * 那意味着多创建了999个无用的类，这些类会一直占着内存，直到能回收Perm的GC发生才会回
         */
        for (int i = 0; i < 16; i++) {
            test.invoke(new MethodInvokeTest(), "test");
        }
    }

    public void test(String s) {
        System.out.println(s);
    }
}
```
生成的大概文件内容
```java
    package sun.reflect;

    public class GeneratedMethodAccessor1 extends MethodAccessorImpl {
        public GeneratedMethodAccessor1() {
            super();
        }

        public Object invoke(Object obj, Object[] args)
                throws IllegalArgumentException, InvocationTargetException {
            // prepare the target and parameters
            if (obj == null) throw new NullPointerException();
            try {
                MethodInvokeTest target = (MethodInvokeTest) obj;
                if (args.length != 1) throw new IllegalArgumentException();
                String arg0 = (String) args[0];
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(e.toString());
            } catch (NullPointerException e) {
                throw new IllegalArgumentException(e.toString());
            }
            // make the invocation
            try {
                target.test(arg0);
            } catch (Throwable t) {
                throw new InvocationTargetException(t);
            }
        }
    }
```