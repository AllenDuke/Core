package com.github.AllenDuke.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/24
 */
public class UDPSender {

    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket(8888);//自身的端口号
        String data = "hello udp program!";
        byte[] datas = data.getBytes();
        //指定发往的目的地
        DatagramPacket packet = new DatagramPacket
                (datas, 0, datas.length,
                        new InetSocketAddress("localhost", 9999));
        client.send(packet);
        client.close();

    }
}
