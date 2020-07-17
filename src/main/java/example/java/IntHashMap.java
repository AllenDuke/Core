package example.java;

/**
 * @author 杜科
 * @description 定制化的HashMap
 * 当量级为百万级时，比起HashMap<Integer,V> put提升超8倍，get提升超10倍。似乎此时最佳
 * 当量级增大到千万级时，put的性能比HashMap持平，甚至稍弱，get的性能为HashMap 4倍
 * @contact AllenDuke@163.com
 * @date 2020/7/16
 */
public class IntHashMap<V> {

    private class IntEntry<V> {

        int key;

        V value;

        IntEntry next;
    }

    private IntEntry[] table;

    private int capacity;//2的n次方

    public IntHashMap(int capacity) {
        if(!checkCapacity(capacity)) throw new RuntimeException("capacity:"+capacity+",不是2的n次方");
        this.capacity = capacity;
        table = new IntEntry[capacity];
    }

    //检查容量是否为2的n次方 按位检查1的个数
    public static boolean checkCapacity(int capacity) {
        int oneCount = 0;
        for (int i = 0; i < 32; i++) {
            if ((capacity & 1) == 1) oneCount++;
            capacity >>>= 1;
        }
        if (oneCount == 1) return true;
        else return false;
    }

    //性能比checkCapacity稍好
    public static int bitCount(int i) {
        // HD, Figure 5-2
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;
    }

    //todo 更为更优秀的hash函数
    private static int indexFor(int key, int length) {
        return key & (length - 1);
    }


    public void put(int key, V value) {
        IntEntry<V> entry = new IntEntry<>();
        entry.key = key;
        entry.value = value;
        int i = indexFor(key, table.length);
        IntEntry head = table[i];
        if (head == null) {
            table[i] = entry;
            return;
        }
        while (head.next != null) head = head.next;
        head.next = entry;
    }


    public V get(int key) {
        int i = indexFor(key, table.length);
        IntEntry<V> head = table[i];
        while (true) {
            if (head == null) return null;//不做检查使用者保证，减少运算
            if (head.key == key) return head.value;
            head = head.next;
        }
    }

}
