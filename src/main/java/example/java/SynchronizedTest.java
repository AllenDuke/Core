package example.java;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/3/20
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        SynchronizedTest o=new SynchronizedTest();
        synchronized (o){}
//        synchronized (SynchronizedTest.class){}

    }

    public synchronized static void synchronizedStatic(){
        int i=0;
    }

    public synchronized void synchronizedMember(){
        int i=1;
    }
}
