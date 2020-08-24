package com.github.AllenDuke;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s=scanner.next();
        int k=scanner.nextInt();
        for(int i=0;i<k;i++){
            scanner.nextInt();
            scanner.nextInt();
            System.out.println(1);
        }
    }

    static Set<String> set=new HashSet<>();

    static void trace(String s,Queue<String> queue,int k){
        for(int i=0;i<s.length();i++){
            for(int j=i+1;j<=s.length();j++) {
                String sub=s.substring(i,j);
                if(set.contains(sub)) continue;
                if(queue.size()<k){
                    queue.add(sub);
                    set.add(sub);
                    continue;
                }
                if(sub.compareTo(queue.peek())<0) {
                    queue.add(sub);
                    set.add(sub);
                    String poll = queue.poll();
                    set.remove(poll);
                }
            }
        }
    }


}