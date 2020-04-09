package example.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            String s=in.next();
            for (int j = 0; j < s.length(); j++) {
                int index=s.charAt(j)-'a';
                count[index]++;
            }
            resolve(s,0);
        }

    }

    public static void resolve(String s,int cur){
        boolean isHui=isHui();
        if(isHui&&cur==0) System.out.println("Cassidy");
        else if(isHui&&cur==1) System.out.println("Eleanore");
        else{
            while(!isHui){
                count[del]--;
                if(cur==0) cur=1;
                else cur=0;
                isHui=isHui();
            }
            if(cur==0) System.out.println("Cassidy");
            else System.out.println("Eleanore");
        }

    }

    static int[] count=new int[26];
    static int del=0;

    public static boolean isHui(){
        int oddNum=0;
        for(int i=0;i<26;i++){
            if(count[i]<1) continue;
            if((count[i]&1)==1) oddNum++;
            else del=i;
        }
        if(oddNum>1) return false;
        else return true;
    }
}

