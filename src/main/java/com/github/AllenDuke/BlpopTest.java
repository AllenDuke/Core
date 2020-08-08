package com.github.AllenDuke;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author 杜科
 * @description jedis中 blpop命令使得java线程阻塞地从socketChannel中读取数据，如果设置了时间，会把超时信息发送给redis，
 * 而redis不管执行什么命令都是不会阻塞自己的，当到达超时时间后，redis会给jedis客户端发送数据，使得java线程可以从socketChanel中
 * 读取到数据而从read方法中返回。
 * @contact AllenDuke@163.com
 * @date 2020/7/29
 */
public class BlpopTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("49.235.168.215");
        jedis.del("blpop");
//        jedis.lpush("blpop","1");
        List<String> list = jedis.blpop(100*1000,"blpop");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
