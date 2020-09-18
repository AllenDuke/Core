package com.github.AllenDuke.sortAlgorithm;

import java.util.Arrays;

/**
 * @author 杜科
 * @description 归并排序
 * @contact AllenDuke@163.com
 * @date 2020/4/1
 */
public class MergeSort {

    private static int[] t;

    public static void sort(int[] a) {
        //一次性分配空间
        t = new int[a.length];
        sort(a, 0, a.length - 1);
    }

    public static void sort(int[] a,int l,int r){
        if(l>=r) return;
        int mid=l+((r-l)>>1); /* >> 优先级低于 + */
        sort(a,l,mid);
        sort(a,mid+1,r);
        merge(a,l,mid,r);
    }

    public static void merge(int[] a,int l,int mid,int r){
        t= Arrays.copyOf(a,a.length);
        int i=l;
        int j=mid+1;
        for(int k=l;k<=r;k++){
            if(i>mid) a[k]=t[j++];
            else if(j>r) a[k]=t[i++];
            else{
                if(t[i]<t[j]){
                    a[k]=t[i];
                    i++;
                }else{
                    a[k]=t[j];
                    j++;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a={5,3,1,4,2};
        sort(a);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
}
