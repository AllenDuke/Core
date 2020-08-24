package com.github.AllenDuke.redisTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/23
 */
public class RedisWithReentrantLock {

    /* 记录当前线程持有的锁及其进入次数 */
    private ThreadLocal<Map<String,Integer>> lockers = new ThreadLocal<>();

    private Jedis jedis;

    public RedisWithReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }

    private boolean lock0(String key) {
        /**
         * 为了避免进程在 setnx 和 expire 之间服务器突然挂掉了， expire 得不到执行，造成死锁，所以有了将这二者合二为一的命令。
         */
        SetParams params = new SetParams();
        params.nx();
        params.ex(5);
        return jedis.set(key, "", params) != null;
    }

    private void unlock0(String key) {
        jedis.del(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }

    public boolean lock(String key) {
        Map refs = currentLockers();
        Integer refCnt = (Integer) refs.get(key);
        if (refCnt != null) { /* 锁重入 */
            refs.put(key, refCnt + 1);
            return true;
        }
        boolean ok = this.lock0(key);
        if (!ok) {
            return false;
        }
        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key) {
        Map refs = currentLockers();
        if(!refs.containsKey(key)) return false; /* 当前线程并非锁的持有者 */
        Integer refCnt = (Integer) refs.get(key);
        refCnt -= 1;
        if (refCnt > 0) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this.unlock0(key);
        }
        return true;
    }
}
