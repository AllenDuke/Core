package com.github.AllenDuke.redisTest;


import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 杜科
 * @description 封装jedis操作的工具类，利用fastJson做中间序列化,默认每次命中缓存则重置过期时间为30分钟
 * @contact AllenDuke@163.com
 * @since 2019/11/6
 */
public class JedisUtil {

    //49.235.168.215      192.168.9.200
    public final static Jedis jedis = new Jedis("49.235.168.215", 6379);

    //用redis的哈希结构缓存对象
    public static boolean hsetObject(String key, Object object) {
        //反射设值
        Class c = object.getClass();
        for (Field declaredField : c.getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                String jsonString = JSON.toJSONString(declaredField.get(object));
                jedis.hset(key, declaredField.getName(), jsonString);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //设置为30分钟后过期
        jedis.expire(key, 60 * 30);
        return true;
    }


    public static Object hgetObject(String key, Object object) {
        //Map map = jedis.hgetAll(key);
        //延长缓存时间，重置为30分钟
        jedis.expire(key, 60 * 30);
        //反射设值
        Class c = object.getClass();
        for (Field declaredField : c.getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                String jsonString = jedis.hget(key, declaredField.getName());
                declaredField.set(object, JSON.parseObject(jsonString, declaredField.getType()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static Object hgetField(String key, String field, Object value) {
        //延长缓存时间，重置为30分钟
        jedis.expire(key, 60 * 30);
        String jsonValue = jedis.hget(key, field);
        value = JSON.parseObject(jsonValue, value.getClass());
        return value;
    }

    public static boolean hsetField(String key, String field, Object value) {
        String jsonValue = JSON.toJSONString(value);
        jedis.hset(key, field, jsonValue);
        //设置为30分钟后过期
        jedis.expire(key, 60 * 30);
        return true;
    }

    public static boolean ishFieldExist(String key, String field) {
        return jedis.hexists(key, field);
    }


    public static boolean isExist(String key) {
        return jedis.exists(key);
    }

    public static boolean setObject(String key, Object object) {
        System.out.println("触发设置键" + key);
        String jsonString = JSON.toJSONString(object);
        jedis.set(key, jsonString);
        //设置为30分钟后过期
        jedis.expire(key, 60 * 30);
        return true;
    }

    /***
     * @Description: 修正了一下，这里的seconds是无效参数
     * @Author: hermanCho
     * @Date: 2020-05-06
     * @Param key:
     * @Param object:
     * @Param seconds:
     * @return: boolean
     **/

    public static boolean setObject(String key, Object object, int seconds) {
        System.out.println("触发设置键with过期时间" + key);
        String jsonString = JSON.toJSONString(object);
        jedis.set(key, jsonString);
        jedis.expire(key, seconds);
        return true;
    }

    //todo 修改为泛型
    public static Object getObject(String key, Class clazz) {
        System.out.println("触发获取键" + key);
        //延长缓存时间，重置为30分钟
        jedis.expire(key, 60 * 30);
        String jsonString = jedis.get(key);
        return JSON.parseObject(jsonString, clazz);
    }

    public static boolean deleteObject(String key) {
        jedis.del(key);
        return true;
    }

    public static boolean hdeleteField(String key, String field) {
        jedis.hdel(key, field);
        return true;
    }

    public static boolean setExpire(String key, int second) {
        jedis.expire(key, second);
        return true;
    }


    public static boolean zAddMem(String key, double score, Object member) {

        String jsonString = JSON.toJSONString(member);
        jedis.zadd(key, score, jsonString);
        //设置为30分钟后过期
        jedis.expire(key, 60 * 30);
        return true;
    }

    public static boolean zRemMem(String key, Object member) {
        String jsonString = JSON.toJSONString(member);
        jedis.zrem(key, jsonString);
        return true;
    }


    public static List<String> zgetMemberAll(String key) {
        //延长缓存时间，重置为30分钟
        jedis.expire(key, 60 * 30);
        //降序
        Set<String> set = jedis.zrevrange(key, 0, -1);
        List<String> list = new ArrayList(set);
        return list;
    }

    public static List zgetMember(String key, int pageNum, int pageSize, Class c) {
        //延长缓存时间，重置为30分钟
        jedis.expire(key, 60 * 30);
        long start = (pageNum - 1) * pageSize;
        long end = pageNum * pageSize - 1;
        //降序
        Set<String> set = jedis.zrevrange(key, start, end);
        List list = new ArrayList();
        for (String s : set) {
            list.add(JSON.parseObject(s, c));
        }
        return list;
    }


    /***
     * @Description: 分割线，用于做数据统计
     * @Author: hermanCho
     * @Date: 2020-05-07
     * @Param key:
     * @Param value:
     * @return: boolean
     **/

    public static boolean pfAdd(String key, String... value) {
        jedis.pfadd(key, value);
        return true;
    }


    public static long pfCount(String... key) {
        return jedis.pfcount(key);
    }

    public static boolean pfMerge(String destkey, String... sourcekeys) {
        String result = jedis.pfmerge(destkey, sourcekeys);
        System.out.println("merge结果：" + result);
        return true;
    }


    /***
     * @Description: 分割线，用于生成自增主键
     *                  todo 目前只用于pyqLike，有别的需求再加方法
     * @Author: hermanCho
     * @Date: 2020-05-11
     * @Param null:
     * @return: null
     **/
    private static String redisCounterKey = "pyqLike";


    // 分离返Strng  : 格式为  int : int
    public static Integer[] split(String member) {
        String[] members = member.split(":");
        Integer[] ans = new Integer[2];
        ans[0] = Integer.valueOf(members[0]);
        ans[1] = Integer.valueOf(members[1]);
        return ans;
    }


}
