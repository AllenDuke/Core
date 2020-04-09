package example.java;

/**
 * @author 杜科
 * @description 基于静态内部类的线程安全的懒加载单例
 * @contact AllenDuke@163.com
 * @date 2020/3/30
 */
public class SingletonTest {

    private SingletonTest(){}

    public static SingletonTest getInstance(){
        return Singleton.singletonTest;
    }

     static class Singleton{
         static SingletonTest singletonTest=new SingletonTest();
    }
}
