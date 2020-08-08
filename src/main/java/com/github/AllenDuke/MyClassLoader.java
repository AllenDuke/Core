package com.github.AllenDuke;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author 杜科
 * @description 自定义类加载器
 * @contact AllenDuke@163.com
 * @date 2020/7/25
 */
public class MyClassLoader extends ClassLoader{

    /**
     * @description: 只重写findClass，遵循双亲委派原则
     * @param name 类全限定名
     * @return: java.lang.Class<?>
     * @author: 杜科
     * @date: 2020/7/25
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try{
            String filePath = "D:\\GitRepository\\Core\\target\\classes\\com\\github\\AllenDuke\\" + name+".class";
            //指定读取磁盘上的某个文件夹下的.class文件：
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            //调用defineClass方法，将字节数组转换成Class对象
            Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
            fis.close();
            return clazz;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return super.findClass(name);
    }
}
