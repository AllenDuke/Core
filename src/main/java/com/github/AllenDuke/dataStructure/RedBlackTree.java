package com.github.AllenDuke.dataStructure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 杜科
 * @description 红黑树 排序、搜索树，插入性能优于平衡二叉树，旋转次数少 不支持范围搜索
 * @contact AllenDuke@163.com
 * @date 2020/8/13
 */
public class RedBlackTree<K extends Comparable<K>,V> {

    private static boolean BLACK=true;

    private static boolean RED=false;

    private class Node{

        K k;

        V v;

        /* 颜色 false为红色，每个新节点为红色 */
        boolean color;

        Node pre;

        Node lChrild;

        Node rChrild;

        @Override
        public String toString() {
            return "Node{" +
                    "k=" + k +
                    ", v=" + v +
                    ", color=" + color +
                    '}';
        }
    }

    /* 根节点 */
    Node root;

    /* 如果存在旧值，那么返回旧值，否则返回新值 */
    public V put(K k,V v){
        Node node = new Node();
        node.color=RED; /* 新插入节点为红色 */
        node.k=k;
        node.v=v;

        if(root==null){ /* 如果还没有树，那么当前节点即为root */
            root=node;
            root.color=BLACK; /* 根节点为黑色 */
            return null;
        }

        Node tmp = find(k);
        if(tmp.k.equals(k)){ /* 存在旧值 */
            V oldV=tmp.v;
            tmp.v=v;
            return oldV;
        }

        Node pre=tmp;
        insert(pre,node);
        return null;
    }

    /**
     * @description: 如果key在树上，那么返回所在节点，否则返回应该插入的位置，即返回其父，用户自行判断返回节点是所在节点还是父节点
     * @param k key
     * @return: com.github.AllenDuke.dataStructure.RedBlackTree<K,V>.Node
     * @author: 杜科
     * @date: 2020/8/15
     */
    private Node find(K k){
        if(root==null) throw new RuntimeException("树还不存在");

        Node node=root;
        while(true){
            if(node.k.equals(k)) return node; /* 作为所在节点返回 */

            if(node.k.compareTo(k)>0){ /* 小于当前节点 */
                if(node.lChrild!=null) node=node.lChrild; /* 往左子树寻找 */
                else return node; /* 作为父节点返回 */
            } else {
                if(node.rChrild!=null) node=node.rChrild;
                else return node;
            }
        }
    }

    private void insert(Node pre, Node cur){
        cur.pre=pre;

        if(pre.k.compareTo(cur.k)>0){
            pre.lChrild=cur; /* cur应位于pre的左子树 */
        }else pre.rChrild=cur;

        /* 如果pre是黑色，那么可以直接插入 */
        if(pre.color==BLACK) return;

        Node pp=pre.pre; /* pre不是root，pp不为空，pp为黑色 */
        Node uncle=pp.lChrild==pre?pp.rChrild:pp.lChrild;

        /* 开始变换 */
        change(pp,pre,uncle,cur);
    }

    private void change(Node pp,Node pre,Node uncle,Node cur){
        /**
         * pre为红色，检查uncle。
         * 1. uncle为红色。
         * 2. uncle不存在或是黑色
         */
        if(uncle!=null&&uncle.color==RED){ /* uncle存在，且为红色 */
            /**
             * 祖父变红，父亲一层变黑
             */
            pp.color=RED;
            pre.color=BLACK;
            uncle.color=BLACK;

            if(pp==root){ /* 如果祖父为root，没有父亲，那么直接变回黑色即可 */
                pp.color=BLACK;
                return;
            }

            /**
             * 祖父成为当前，把调整当作一个插入动作
             */
            cur=pp;
            pre=cur.pre;
            insert(pre,cur);
            return;
        }

        /**
         * uncle不存在或者uncle是黑色，那么依据pp, pre, cur 检查当前模型，开始旋转
         */

        /* LL模型 */
        if(pre==pp.lChrild&&cur==pre.lChrild) handleLL(pp,pre,uncle,cur);

        /* LR模型 */
        if(pre==pp.lChrild&&cur==pre.rChrild) handleLR(pp,pre,uncle,cur);

        /* RL模型 */
        if(pre==pp.rChrild&&cur==pre.lChrild) handRL(pp,pre,uncle,cur);

        /* RR模型 */
        if(pre==pp.rChrild&&cur==pre.rChrild) handRR(pp,pre,uncle,cur);
    }

    /* LL模型 */
    private void handleLL(Node pp,Node pre,Node uncle,Node cur){
        /* 变色 */
        pp.color=RED;
        pre.color=BLACK;

        if(pp==root){ /* pp为root，pp没有父亲了，右旋一次后达到黑色平横*/
            pp.lChrild=pre.rChrild;
            pre.rChrild=pp;
            pp.pre=pre;

            pre.pre=null;
            root=pre;
            return;
        }

        /**
         * 祖父成为当前，把调整当作一个插入动作
         */
        cur=pp;
        pre=cur.pre;
        insert(pre,cur);
        return;
    }

    /* LR模型 左旋一次达到LL模型 */
    private void handleLR(Node pp,Node pre,Node uncle,Node cur){
        /* 左旋 */
        pp.lChrild=cur;
        cur.pre=pp;

        pre.rChrild=cur.lChrild;

        cur.lChrild=pre;
        pre.pre=cur;

        Node tmp=pre;
        pre=cur;
        cur=tmp;

        /* 达到LL模型 */
        handleLL(pp,pre,uncle,cur);
    }

    /* RL模型 右旋一次达到RR模型 */
    private void handRL(Node pp,Node pre,Node uncle,Node cur){
        /* 右旋 */
        pp.rChrild=cur;
        cur.pre=pp;

        pre.lChrild=cur.rChrild;

        cur.rChrild=pre;
        pre.pre=cur;

        Node tmp=pre;
        pre=cur;
        cur=tmp;

        /* 达到RR模型 */
        handRR(pp,pre,uncle,cur);
    }

    /* RR模型 */
    private void handRR(Node pp,Node pre,Node uncle,Node cur){
        /* 变色 */
        pp.color=RED;
        pre.color=BLACK;

        if(pp==root){ /* pp为root，pp没有父亲了，左旋一次后达到黑色平横*/
            pp.rChrild=pre.lChrild;
            pre.lChrild=pp;
            pp.pre=pre;

            pre.pre=null;
            root=pre;
            return;
        }

        /**
         * 祖父成为当前，把调整当作一个插入动作
         */
        cur=pp;
        pre=cur.pre;
        insert(pre,cur);
        return;
    }

    public V get(K k){
        Node node = find(k);
        if(node.k.equals(k)) return node.v;
        else return null;
    }

    public V remove(K k){
        Node cur = find(k);
        Node pre=cur.pre;
        if(cur.k.equals(k)){
            if(cur.lChrild==null&&cur.rChrild==null) { /* node无子，直接删除 */
                cur.pre=null;
                if(cur==root) root=null;
                return cur.v;
            }

            /* 单枝黑红 */
            if(cur.lChrild==null){
                if(cur==root) root = cur.rChrild;

                if(cur==pre.lChrild) pre.lChrild=cur.rChrild;

                if(cur==pre.rChrild) pre.rChrild=cur.rChrild;

                cur.rChrild.color=BLACK;
                return cur.v;
            }
            if(cur.rChrild==null){
                if(cur==root) root = cur.lChrild;

                if(cur==pre.lChrild) pre.lChrild=cur.lChrild;

                if(cur==pre.rChrild) pre.rChrild=cur.lChrild;

                cur.lChrild.color=BLACK;
                return cur.v;
            }

            /* todo 有双子 */
            throw new RuntimeException("有双子的情况，暂未实现");
        }
        return null;
    }

    /* 按层打印 */
    public void show(){
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        while(queue.size()>0){
            int curLevelSize=queue.size();
            for (int i = 0; i < curLevelSize; i++) {
                Node node = queue.poll();
                System.out.print(node+" ");
                if(node.lChrild!=null) queue.add(node.lChrild);
                if(node.rChrild!=null) queue.add(node.rChrild);
            }
            System.out.println();
        }
    }

}
