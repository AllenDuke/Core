package example.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n=scanner.nextInt();
        int[] num=new int[n];
        for (int i = 0; i < n; i++) {
            num[i]=scanner.nextInt();
        }
        trace(num,new ArrayList<Integer>(),-1);
        System.out.println((sum-1)%998244353);
    }

    static long sum=0;
    public static void trace(int[] num, List<Integer> list, int r){
        boolean isFun=true;
        for(int i=1;i<=list.size();i++){
            if(list.get(i-1)%i!=0) {
                isFun=false;
                break;
            }
        }
        if(isFun) sum++;
        for (int i = r+1; i < num.length; i++) {
            list.add(num[i]);
            trace(num,list,i);
            list.remove(list.size()-1);
        }
    }

}

