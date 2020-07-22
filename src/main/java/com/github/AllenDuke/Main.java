package com.github.AllenDuke;


public class Main {

    public static void main(String[] args) {
        try {
            int i=1/0;
        }catch (Throwable th){
            System.out.println(th.toString());
        }
        System.out.println("continue");

    }

}
