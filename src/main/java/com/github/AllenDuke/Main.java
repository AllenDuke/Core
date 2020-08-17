package com.github.AllenDuke;

public class Main {

    public static void main(String[] args) {

        System.out.println(max(3, 2));
        System.out.println(max(-1, 5));
    }

    static int max(int a,int b){
        double mid=(a+b)/2.0;
        double detaV=abs(a-b);
        return (int) (mid+detaV/2.0);
    }

    static int abs(int n){
        int i=n>>31;
        return (n^i)-i;
    }

}