package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 堆排序
 * @contact AllenDuke@163.com
 * @date 2020/4/1
 */
public class HeapSort {

    public static void sort(int[] a){
        for(int i=(a.length-1)/2;i>=0;i--){//从第一个非叶子节点开始往上调整建堆，复杂度的上限似乎比从上往下调整建堆要低
            adjust(a,i,a.length);
        }
        for (int i = a.length - 1; i > 0; i--){//将堆顶元素（最大）调整至末尾。
            int temp = a[i];
            a[i] = a[0];
            a[0] = temp;
            adjust(a, 0, i);
        }
    }

    //大顶堆
    public static void adjust(int[] a, int parent, int length){//从父节点开始往下调整
        int t=a[parent];
        int l=parent*2+1;//左节点
        while(l<length){
            int r=l+1;//右节点
            int max=l;
            if(r<length&&a[r]>a[l]) max=r;
            if(a[parent]>a[max]) break;
            a[parent]=a[max];
            parent=max;
            l=parent*2+1;
        }
        a[parent]=t;
    }

    public static void main(String[] args) {
        int[] a={5,3,4,1,2};
        sort(a);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
}
