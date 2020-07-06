package example.java;

/**
 * @author 杜科
 * @description 测试new String 与new StirngBuilder的耗时
 * @contact AllenDuke@163.com
 * @date 2020/6/10
 */
public class StringAndStirngBuilderTest {

    public static void main(String[] args) {
        testString();
        testStringBuilder();
    }

    public static void testString(){
        long start=System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            new String();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void testStringBuilder(){//消耗更大
        long start=System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            new StringBuilder();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
