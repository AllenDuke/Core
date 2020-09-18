package com.github.AllenDuke.math;

import java.util.Stack;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/9/9
 */
public class CalcuratorTest {

    public static void main(String[] args) {

        System.out.println(get("3 + 2 * 5 + 4"));
        System.out.println(get("10 + 3 * 4 + 5 * 4 * 5"));
    }

    /**
     * @description: 利用两个栈，一个操作数栈，一个运算符栈（其实也可以合并成一个栈）完成中缀表达式的求值
     * @param s 中缀表达式
     * @return: int
     * @author: 杜科
     * @date: 2020/9/9
     */
    static int get(String s){
        Stack<Integer> operandStack = new Stack<>(); /* 操作数栈 */
        Stack<String> operatorStack = new Stack<>(); /* 操作符栈 */
        String[] split = s.split(" ");
        for (String s1: split) {
            if(s1.charAt(0)<'0'||s1.charAt(0)>'9') {
                operatorStack.push(s1); /* 这是一个操作符 */
                continue;
            }
            Integer cur=Integer.valueOf(s1);
            if(operatorStack.size()==0) {
                operandStack.push(cur);
                continue;
            }
            if(operatorStack.peek().equals("*")){
                Integer pop=operandStack.pop();
                operandStack.push(cur*pop);
                operatorStack.pop();
                continue;
            }
            if(operatorStack.peek().equals("/")){
                Integer pop=operandStack.pop();
                operandStack.push(pop/cur);
                operatorStack.pop();
                continue;
            }
            operandStack.push(cur);
        }
        while(operatorStack.size()>0){
            Integer num1=operandStack.pop();
            Integer num2=operandStack.pop();
            String operator=operatorStack.pop();
            switch (operator) {
                case "+": operandStack.push(num1+num2);
                    break;
                case "-": operandStack.push(num2-num1);
                    break;
            }
        }
        return operandStack.pop();
    }
}
