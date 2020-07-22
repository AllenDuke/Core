package com.github.AllenDuke;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 杜科
 * @description 测试NIO读时非阻塞
 * @contact AllenDuke@163.com
 * @date 2020/4/12
 */
public class NIOTest {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\Chrome\\Download\\warmup-test.data");
        FileChannel channel = fileInputStream.getChannel();

//        long start=System.currentTimeMillis();
//        lines=0;
//        readLine(fileInputStream);
//        System.out.println("bufferRead耗时"+(System.currentTimeMillis() - start)/1000+" "+lines);

//        long start=System.currentTimeMillis();
//        lines=0;
//        readByte(channel,0,channel.size()-1);
//        System.out.println("nio no mmap耗时"+(System.currentTimeMillis() - start)/1000+" "+lines);
//
        long start=System.currentTimeMillis();
        lines=0;
        readByteInMmap(channel,0,channel.size()-1);
        System.out.println("nio mmap耗时"+(System.currentTimeMillis() - start)/1000+" "+lines);

        fileInputStream.close();
    }

    static long lines=0;

    public static void readLine(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream),1*1024*1024);
        String s=null;
        while((s=bufferedReader.readLine())!=null){
           lines++;
        }
    }

    public static void readByte(FileChannel channel,long cur,long tail) throws IOException {
        while (tail - cur + 1 >= 1 * 1024 * 1024) {//在当前处理范围内，每1MB建立一个内存映射
            ByteBuffer byteBuffer = ByteBuffer.allocate(1 * 1024 * 1024);
            channel.position(cur);
            channel.read(byteBuffer);
            byteBuffer.flip();
            cur = handleBufByLine(cur, byteBuffer, tail);//更新cur
//                        System.out.println("处理完第 " + num++ + "块");
        }
        if (cur <= tail) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) (tail-cur+1));
            channel.position(cur);
            channel.read(byteBuffer);
            byteBuffer.flip();
            cur = handleBufByLine(cur, byteBuffer, tail);
//                        System.out.println("thread-" + Thread.currentThread().getName() + " reach: " + cur);
        }
    }

    //slower than no mmap? besides, multi thread doesn's help?
    public static void readByteInMmap(FileChannel channel,long cur,long tail) throws IOException {
        while (tail - cur + 1 >= 300 * 1024 * 1024) {//在当前处理范围内，每1MB建立一个内存映射
            //注意可用的直接内存,避免频繁发生操作系统页面置换
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, cur,
                    300 * 1024 * 1024);
            cur = handleBufByLine(cur, byteBuffer, tail);//更新cur
//                        System.out.println("处理完第 " + num++ + "块");
        }
        if (cur <= tail) {
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, cur,
                    tail - cur + 1);

            cur = handleBufByLine(cur, byteBuffer, tail);
//                        System.out.println("thread-" + Thread.currentThread().getName() + " reach: " + cur);
        }
    }

    private static long handleBufByLine(long cur, ByteBuffer byteBuffer, long tail){
        while(byteBuffer.position()<byteBuffer.capacity()){
            char ch= (char) byteBuffer.get();
            if(ch=='\n'){
                lines++;
            }
        }
        return cur+byteBuffer.capacity();
    }

    private static void readLineInCommonIO(InputStream inputStream) throws IOException {

    }
}
