package com.github.AllenDuke.redisTest;

import redis.clients.jedis.Jedis;

/**
 * @author 杜科
 * @description 测试redis分布式锁
 * @contact AllenDuke@163.com
 * @date 2020/8/23
 */
public class DistributedLockTest {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("49.235.168.215");
        RedisWithReentrantLock redis = new RedisWithReentrantLock(jedis);
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.unlock("codehole"));
        System.out.println(redis.unlock("codehole"));
    }

}
