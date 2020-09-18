package com.github.AllenDuke.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/24
 */
public class UDPReceiver {

    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(9999);
        byte[] container = new byte[64*1024];
        DatagramPacket packet = new DatagramPacket(container, 0,container.length);
        server.receive(packet);

        byte[] data = packet.getData();
        System.out.println(new String(data));
        server.close();
    }
}
