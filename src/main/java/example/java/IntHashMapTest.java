package example.java;

import java.util.HashMap;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/7/16
 */
public class IntHashMapTest {

    public static void main(String[] args) {
        int count=1024*1024;
        System.out.println("current count:" + count);
        HashMap<Integer, Integer> hashMap = new HashMap<>(count);
        IntHashMap<Integer> intHashMap = new IntHashMap<Integer>(count);
        long start1=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            hashMap.put(i*31,i);
        }
        long finish1=System.currentTimeMillis();
        System.out.println("HashMap put cost:"+(finish1 - start1) + "ms");

        start1=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            hashMap.get(i*31);
        }
        finish1=System.currentTimeMillis();
        System.out.println("HashMap get cost:"+(finish1 - start1) + "ms");

        long start2=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            intHashMap.put(i*31,i);
        }
        long finish2=System.currentTimeMillis();
        System.out.println("IntHashMap put cost:"+(finish2 - start2) + "ms");

        start2=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            intHashMap.get(i*31);
        }
        finish2=System.currentTimeMillis();
        System.out.println("IntHashMap get cost:"+(finish2 - start2) + "ms");

        for(int i=0;i<count;i++){
            if(!hashMap.get(i*31).equals(intHashMap.get(i*31)))
                throw new RuntimeException("IntMap error key:"+i*31+" hashMap:"+hashMap.get(i*31)+" intHashMap:"+intHashMap.get(i*31));

        }

        start1=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            IntHashMap.checkCapacity(i);
        }
        finish1=System.currentTimeMillis();
        System.out.println("IntHashMap checkCapacity cost:"+(finish1 - start1) + "ms");

        start2=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            IntHashMap.bitCount(i);
        }
        finish2=System.currentTimeMillis();
        System.out.println("IntHashMap bitCount cost:"+(finish2 - start2) + "ms");
    }
}
