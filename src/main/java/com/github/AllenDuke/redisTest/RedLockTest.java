package com.github.AllenDuke.redisTest;

/**
 * @author 杜科
 * @description 阐述redlock算法
 * @contact AllenDuke@163.com
 * @date 2020/8/23
 */
public class RedLockTest {
    /**
     * 想象一下这样的场景，在一般的主从架构中，我们在主服务器上成功地申请到一个锁，但是在这锁要同步到其他的从服务器上时，主宕机了，
     * 那么在后来选取到一个新的主服务器时，该新主是没有该锁的，而这时又有新的客户端来申请该锁时，将会申请到。形成了这样的一个场面：
     * 集群中，有两个不一样的客户端持有同一把锁，这是不允许的，而且接下来，有可能这两个客户端互相释放掉了对方的锁。
     * 解决：
     *
     */
}
