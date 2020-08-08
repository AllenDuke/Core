package com.github.AllenDuke;


import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t=scanner.nextInt();
        for(int i=0;i<t;i++){
            int n=scanner.nextInt();
            int[] a=new int[n];
            int sum=0;
            for(int j=0;j<n;j++){
                a[j]= scanner.nextInt();
                sum+=a[j];
            }
            Arrays.sort(a);
            f(a,sum,0);
            for(int j=0;j<n;j++){
                sum-=a[j];
            }
            System.out.println(sum);
        }

    }

    static void f(int[] a,int sum,int l){
        if(sum%2==1){
            sum-=a[l];
            f(a,sum,l+1);
        }
        int left=0;
        for(int i=l;i<a.length;i++){
            left+=a[i];
            if(left==sum/2) break;
            if(left>sum/2){
                sum-=a[l];
                a[l]=0;
                f(a,sum,l+1);
                break;
            }
        }
    }

}
