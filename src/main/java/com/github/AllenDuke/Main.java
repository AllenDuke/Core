package com.github.AllenDuke;


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s=scanner.nextLine();
            sum=0;
            map=new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if(map.containsKey(ch)){
                    map.put(ch,map.get(ch)+1);
                    continue;
                }
                map.put(ch,1);
            }
            PriorityQueue<Node> queue = new PriorityQueue<>();
            for (Character character : map.keySet()) {
                Node node = new Node();
                node.ch=character;
                node.count=map.get(character);
                queue.add(node);
            }
            while(queue.size()>=2){
                Node left = queue.poll();
                Node right = queue.poll();
                Node root = new Node();
                root.count=left.count+right.count;
                root.left=left;
                root.right=right;
                queue.add(root);
            }
            trace(queue.poll(),0);
            System.out.println(sum);
        }

    }

    static Map<Character,Integer> map;

    static int sum;

    static void trace(Node root,int depth){
        if(root==null) return;
        if(root.left==null&&root.right==null){
            sum+=map.get(root.ch)*depth;
            return;
        }
        trace(root.left,depth+1);
        trace(root.right,depth+1);
    }

    static class Node implements Comparable<Node>{
        char ch;
        int count;
        Node left;
        Node right;

        @Override
        public int compareTo(Node o) {
            return count-o.count;
        }
    }


}