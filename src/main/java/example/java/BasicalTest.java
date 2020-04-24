package example.java;

/**
 * @author 杜科
 * @description 基础测试
 * @contact AllenDuke@163.com
 * @date 2020/4/24
 */
public class BasicalTest {

    public static void main(String[] args) {
        float a=0.125f;
        double b=0.125d;
        System.out.println((a - b) == 0.0);//true

        double c=0.8;
        double d=0.7;
        double e=0.6;
        System.out.println(c - d == d - e);//false

        System.out.println(1.0 / 0);//Infinity

        String h=null;
        try {
            switch (h){
                case "null":
                    System.out.println("null");
                default:
                    System.out.println("不与其他匹配");
            }
        }catch (Exception e1){
            e1.printStackTrace();
        }

    }

    static void g(String s){
        System.out.println("gs");
    }

    static void g(Integer i){
        System.out.println("gi");
    }
}
