package example.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 杜科
 * @description 测试无返回值方法的反射调用
 * @contact AllenDuke@163.com
 * @date 2020/4/19
 */
public class MethodTest {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        MethodTest methodTest = new MethodTest();
        Method[] methods = methodTest.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getReturnType() == Void.TYPE);
//            System.out.println(method.getReturnType() == void.class);
            if(method.getName()=="test") System.out.println(method.invoke(new MethodTest(), null) == null);
        }
    }

    public void test(){
        System.out.println("无返回值方法");
    }
}
