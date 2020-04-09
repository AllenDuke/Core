package example.java;

/**
 * @author 杜科
 * @description 快速排序
 * @contact AllenDuke@163.com
 * @date 2020/4/1
 */
public class QuickSort {

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
    }
}
