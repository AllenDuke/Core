package com.github.AllenDuke;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author 杜科
 * @description 时间与空间折中的LinkedList
 * @contact AllenDuke@163.com
 * @date 2020/7/23
 */
public class QuickList<E> implements List<E> {

    private static int SINGLE_CAPACITY=30;

    private static class Node<E>{

        ArrayQueue<E> queue=new ArrayQueue<>(SINGLE_CAPACITY);

        Node<E> pre;

        Node<E> next;
    }

    private int len=0;

    private Node head=new Node();

    private Node tail=head;

    @Override
    public int size() {
        return (len-1)*SINGLE_CAPACITY+tail.queue.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        try {
            tail.queue.add(e);
        } catch (Exception ex) {
            Node<Object> node = new Node<>();
            tail.next=node;
            len++;
            tail=node;
            tail.queue.add(e);
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        head.queue.remove(o);
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        int i=index/SINGLE_CAPACITY;
        Node cur=head;
        while(i-->0) cur=cur.next;
        return (E) cur.queue.get(index%SINGLE_CAPACITY);
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

}
