package com.github.AllenDuke.dataStructure;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/7/31
 */
public class BitMap {

    long content=0;

    public void set(int i){
        long mask=1;
        while(i>0){
            mask=mask<<1;
            i--;
        }
        content|=mask;
    }

    public boolean get(int i){
        long mask=1;
        while(i>0){
            mask=mask<<1;
            i--;
        }
        if((content&mask)!=0) return true;
        else return false;
    }
}
