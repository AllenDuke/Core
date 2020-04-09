package example.java;

/**
 * @author 杜科
 * @description 测试克隆 浅拷贝
 * @contact AllenDuke@163.com
 * @date 2020/4/2
 */
public class CloneableTest implements Cloneable{

    private User user=new User();

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneableTest cloneableTest1 = new CloneableTest();
        CloneableTest cloneableTest2 = (CloneableTest) cloneableTest1.clone();//浅拷贝
    }
}
