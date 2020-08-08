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
        //3552MB
//        System.out.println("文件大小：" + channel.size() / (1024 * 1024) + "MB");

//        long start1=System.currentTimeMillis();
//        expensiveMove(100000);
//        System.out.println("expensiveMove耗时: "+(System.currentTimeMillis() - start1)+"ms ");

//        long start1=System.currentTimeMillis();
//        lines=0;
//        readLine(fileInputStream);
//        //14s
//        System.out.println("bufferRead耗时: "+(System.currentTimeMillis() - start1)+"ms "+lines);

//        long start2=System.currentTimeMillis();
//        lines=0;
//        readInHeapBuf(channel,0,channel.size()-1);
//        System.out.println("nio heapByteBuffer耗时: "+(System.currentTimeMillis() - start2)+"ms "+lines);

        long start3 = System.currentTimeMillis();
        lines = 0;
        readInMapBuf(channel, 0, channel.size() - 1);
        System.out.println("nio mapByteBuffer耗时耗时: " + (System.currentTimeMillis() - start3) + "ms " + lines);

        fileInputStream.close();
    }

    //模仿一个耗时操作
    static long expensiveMove(byte[] bytes) {
        long j = bytes.length;
        for (long i = 1; i <= bytes.length; i++) {
            j += i;
        }
        return j;
    }

    static long expensiveMove(String s) {
        long j = s.length();
        for (long i = 1; i <= s.length(); i++) {
            j += i;
        }
        return j;
    }

    static long lines = 0;

    public static void readLine(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 5 * 1024 * 1024);
        while (bufferedReader.readLine() != null) {
            lines++;
        }
    }

    public static void readInHeapBuf(FileChannel channel, long cur, long tail) throws IOException {
        while (tail - cur + 1 >= 5 * 1024 * 1024) {//在当前处理范围内，每1MB建立一个内存映射
            ByteBuffer byteBuffer = ByteBuffer.allocate(5 * 1024 * 1024);
            channel.position(cur);
            channel.read(byteBuffer);
            byteBuffer.flip();
            cur = handleBufByLine(cur, byteBuffer, tail);//更新cur
//                        System.out.println("处理完第 " + num++ + "块");
        }
        if (cur <= tail) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) (tail - cur + 1));
            channel.position(cur);
            channel.read(byteBuffer);
            byteBuffer.flip();
            cur = handleBufByLine(cur, byteBuffer, tail);
//                        System.out.println("thread-" + Thread.currentThread().getName() + " reach: " + cur);
        }
    }

    public static void readInMapBuf(FileChannel channel, long cur, long tail) throws IOException {
        while (tail - cur + 1 >= 32 * 1024 * 1024) {//在当前处理范围内，每1MB建立一个内存映射
            //注意可用的直接内存,避免频繁发生操作系统页面置换
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, cur,
                    32 * 1024 * 1024);
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

    private static long handleBufByLine(long cur, ByteBuffer byteBuffer, long tail) {
        byte[] bytes=new byte[50];
        int i=0;
        while (byteBuffer.position() < byteBuffer.capacity()) {
            byte b = byteBuffer.get();
            if (b == '\n') {//这个判断不耗时
                lines++;//1s约可以进行4000万次加法运算
                expensiveMove(new String(bytes));
                i=0;
                bytes=new byte[50];
            }
            else bytes[i++]=b;
        }

        return cur + byteBuffer.capacity();
    }

}
