package com.github.AllenDuke.sortAlgorithm;

/**
 * @author 杜科
 * @description 冒泡排序
 * @contact AllenDuke@163.com
 * @date 2020/4/1
 */
public class BubbleSort {

    public static void sort(int[] a){
        for(int i=0;i<a.length;i++){//轮数
            for(int j=1;j<a.length-i;j++){//每轮最大值的位置
                if(a[j]<a[j-1]){
                    int t=a[j];
                    a[j]=a[j-1];
                    a[j-1]=t;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a={5,3,4,1,2};
        sort(a);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
}
