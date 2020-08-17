package com.github.AllenDuke.dataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author 杜科
 * @description 根据给定的字符串进行哈夫曼编码
 * 哈夫曼编码是最优二进制编码
 * @contact AllenDuke@163.com
 * @date 2020/8/10
 */
public class HaffmanTree {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s=scanner.nextLine();
            sum=0;
            map=new HashMap<>();
            /* 记录出现的频率 */
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if(map.containsKey(ch)){
                    map.put(ch,map.get(ch)+1);
                    continue;
                }
                map.put(ch,1);
            }
            /* 构建优先队列 */
            PriorityQueue<Node> queue = new PriorityQueue<>();
            for (Character character : map.keySet()) {
                Node node = new Node();
                node.ch=character;
                node.count=map.get(character);
                queue.add(node);
            }
            /**
             * 开始建树，每次从队列中拿取最小的两个出来合并，然后加回队列
             */
            while(queue.size()>=2){
                Node left = queue.poll();
                Node right = queue.poll();
                Node root = new Node();
                root.count=left.count+right.count;
                root.left=left;
                root.right=right;
                queue.add(root);
            }
            /* 遍历树 */
            trace(queue.poll(),0);
            System.out.println(sum);
        }

    }

    static Map<Character,Integer> map;

    static int sum;

    /* 遍历树，到达叶子节点后计算深度 */
    static void trace(Node root,int depth){
        if(root==null) return;
        if(root.left==null&&root.right==null){
            sum+=map.get(root.ch)*depth;
            return;
        }
        trace(root.left,depth+1);
        trace(root.right,depth+1);
    }

    /* 树的节点 */
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
