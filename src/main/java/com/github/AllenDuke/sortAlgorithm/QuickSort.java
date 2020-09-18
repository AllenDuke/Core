package com.github.AllenDuke.sortAlgorithm;

/**
 * @author 杜科
 * @description 快速排序
 * @contact AllenDuke@163.com
 * @date 2020/4/1
 */
public class QuickSort {

    /*
    快速排序的不稳定性
    5 | 3 1 2 | 9 7 8 9 | 4 6
    4与第一个9交换了位置
     */

    public static void sort(int[] a){
        sort(a,0,a.length-1);
    }

    public static void sort(int[] a,int l,int r){
        if(l>=r) return;
        int i = partition(a, l, r);
        sort(a,l,i-1);
        sort(a,i+1,r);
    }

    public static int partition(int[] a,int l,int r){
        int index=l;
        r++;
        while(true){
            while(l<a.length-1&&a[++l]<a[index]);
            while(r>0&&a[--r]>a[index]);
            if(l<r){
                int t=a[l];
                a[l]=a[r];
                a[r]=t;
            }else break;
        }
        int t=a[index];
        a[index]=a[r];
        a[r]=t;
        return r;
    }

    public static void main(String[] args) {
        int[] a={5,3,4,1,2};
        sort(a);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();

        int[] b={0,2,2,11,11,11,10,55,44,33,22,22,66};
        p3sort(b,0,b.length-1);
        for(int i=0;i<b.length;i++){
            System.out.print(b[i]+" ");
        }
        System.out.println();
    }

    private static void p3sort(int[] array, int start, int end) {
        //初始化三指针
        //小于主元的指针和等于主元的指针都初始化为下标为数组第二个
        //大于主元的指针初始化为尾元
        int smaller = start+1;
        int equal = smaller;
        int bigger = end;
        //设置退出条件
        if(smaller > bigger) return ;
        //选择主元，一般为首元
        int num = array[start];
        /*
         * 当smaller扫描到的数字小于主元，则下标为smaller和equal的需要交换数据，这样就又将小于主元的放在一起了，然后smaller和equal都要自增
         * 当smaller扫描到的数字等于主元，直接将smaller自增
         * 当smaller扫描到的数字大于主元，就将小标为smaller和bigger上的数据交换，bigger再自减(和单向扫描分区法处理一样）
         */
        while(smaller <= bigger) {
            if(array[smaller] < num) {
                swap(array, equal, smaller);
                equal++;
                smaller++;
            }
            else if(array[smaller] == num) {
                smaller++;
            }
            else if(array[smaller] > num) {
                swap(array, smaller, bigger);
                bigger--;
            }
        }
        //交换首元与right上的数
        swap(array, start, equal-1);
        //继续将right两边的数组进行快速排序
        p3sort(array, start, equal-2);
        p3sort(array, bigger+1, end);
    }

    private static void swap(int[] array, int sc, int r) {
        int num = array[sc];
        array[sc] = array[r];
        array[r] = num;
    }
}
