package com.github.AllenDuke.math;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/9/4
 */
public class SqrtTest {

    public static void main(String[] args) {
        System.out.println(sqrt(3, 0.001));
        System.out.println(sqrt1(3, 0.001));
//        System.out.println(sqrt(0.01, 0.0001));
    }

    static double sqrt(double num,double error){
        if(num==1) return 1;
        double begin=0;
        double end=num;
        if(num<1){
            end=1;
        }
        while (true){
            double mid=begin+(end-begin)/2;
            double cur=mid*mid;
            if(Math.abs(cur-num)<=error) return mid;
            if(cur>num) end=mid;
            else begin=mid;
        }
    }

    static double sqrt1(double num,double error){
        if(num==1) return 1;
        double begin=0;
        double end=num;
        if(num<1){
            end=1;
        }
        while (true){
            double mid=begin+(end-begin)/2;
            double cur=mid*mid;
            if((cur+num-error*error)*(cur+num-error*error)<=4*cur*num) return mid;
            if(cur>num) end=mid;
            else begin=mid;
        }
    }
}
