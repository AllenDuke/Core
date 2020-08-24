package com.github.AllenDuke;

import java.util.*;

/**
 * @author 杜科
 * @description 加权随机算法
 * @contact AllenDuke@163.com
 * @date 2020/8/24
 */
public class WeightRandom {

    public volatile static Map<String, Integer> serverMap = new TreeMap<>();

    static {
        serverMap.put("192.168.1.1", 1);
        serverMap.put("192.168.1.2", 2);
        serverMap.put("192.168.1.3", 3);
        serverMap.put("192.168.1.4", 4);
    }

    public static String getServer() {
        ArrayList<String> serverList = new ArrayList<>();
        Set<String> serverSet = serverMap.keySet();
        Iterator<String> iterator = serverSet.iterator();

        Integer weightSum = 0;
        while(iterator.hasNext()){
            String server = iterator.next();
            Integer weight = serverMap.get(server);
            weightSum += weight;
            for (int i = 0; i < weight; i++) { /* 区间长度，将按比例 */
                serverList.add(server);
            }
        }

        Random random = new Random();
        String server = serverList.get(random.nextInt(weightSum)); /* 随机落在某个区间，概率不一 */
        return server;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String server = getServer();
            System.out.println(server);
        }
    }
}
