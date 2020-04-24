package example.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 杜科
 * @description 测试NIO读时非阻塞
 * @contact AllenDuke@163.com
 * @date 2020/4/12
 */
public class NIOTest {

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/java/NIO.md");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*100);
        byte[] bytes=new byte[1024*100];
        long pre = System.currentTimeMillis();
        channel.read(byteBuffer);
        inputStream.read(bytes);//用流读还比较快，约为用channel读的1.5倍
        System.out.println(System.currentTimeMillis() - pre);
        byteBuffer.flip();
        inputStream.close();
    }
}
