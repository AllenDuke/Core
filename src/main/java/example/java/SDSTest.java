package example.java;

/**
 * @author 杜科
 * @description 测试SDS可重用的性能
 * @contact AllenDuke@163.com
 * @date 2020/6/17
 */
public class SDSTest {

    private static int count=100000000;

    public static void main(String[] args) {
        long start=System.currentTimeMillis()/1000;
        testStringBuilder();
//        testSDS();
        System.out.println(System.currentTimeMillis() / 1000 - start);
    }

    public static void testStringBuilder(){
        for (int i = 0; i < count; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i).toString();
        }
    }

    public static void testSDS(){
        SDS sds=new SDS();
        for(int i=0;i<count;i++){
            sds.append(i).toString();
            sds.clear();
        }
    }
}
