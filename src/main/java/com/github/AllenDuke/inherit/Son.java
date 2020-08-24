package com.github.AllenDuke.inherit;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/24
 */
public class Son extends Dad {

    @Override
    public void say() {
        /**
         * 子类重写的方法，访问权限>=父类。
         * 在父类中是public的方法，如果子类中将其降低访问权限为private，那么子类中重写以后的方法对于外部对象就不可访问了，
         * 这个就破坏了继承的含义。
         */
        super.say();
    }
}
