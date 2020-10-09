package com.github.AllenDuke;

public class Main {

    private static volatile Main main;
    private static Object o=new Object();

    private Main(){

    }

    public static Main getInstance(){
        if(main!=null) return main;
        synchronized (o){
            if(main!=null) return main;
            main=new Main();
        }
        return main;
    }

    public static void main(String[] args) {
        int n=1000;
        for(int i=1;i<=n;i++){
            if(check(i)) {
                System.out.println(i);
            }
        }
    }

    public static boolean check(int n){
        for(int i=2;i<n;i++){
            if(n%i==0) return false;
        }
        return true;
    }

}


