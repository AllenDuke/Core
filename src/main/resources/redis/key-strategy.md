# 过期键淘汰时机
1. 到期删除。设置定时器
2. 惰性删除，过期放任不管，在访问的时候判断是否过期，如果过期了再执行删除操作。
3. 定期删除。简单而言，对指定个数个库的每一个库随机删除小于等于指定个数个过期key，遍历每个数据库（默认为16），针对当前库，
删除指定数量的过期键，对内存中每个键随机抽取进行检查。 对于定期删除，在程序中有一个全局变量current_db来记录下一个将要遍历的库。
假设有16个库，我们这一次定期删除遍历了10个，那此时的current_db就是11，下一次定期删除就从第11个库开始遍历。类比netty

Redis采用的过期策略：惰性删除+定期删除(默认100ms)。如果定期删除没删除key。然后你也没即时去请求key，也就是说惰性删除也没生效。这样，
redis的内存会越来越高。那么就应该采用内存淘汰机制(作用于新键插入时)。

# 内存淘汰策略 找出要淘汰的key
1. noeviction: 不删除策略。当达到最大内存限制时, 如果需要使用更多内存,则直接返回错误信息。（redis默认淘汰策略）
2. allkeys-lru: 在所有key中优先删除最近最少使用(less recently used ,LRU) 的 key。（推荐使用）
3. allkeys-random: 在所有key中随机删除一部分 key。
4. volatile-lru: 在设置了超时时间（expire ）的key中优先删除最近最少使用(less recently used ,LRU) 的 key。（这种情况一般是
把redis既当缓存，又做持久化存储的时候才用。不推荐）
5. volatile-random: 在设置了超时时间（expire）的key中随机删除一部分 key。
6. volatile-ttl: 在设置了超时时间（expire ）的key中优先删除剩余时间(time to live,TTL) 短的key。

## 如何保证redis中的key是热点数据
场景：

数据库中有1000w的数据，而redis中只有50w数据，如何保证redis中50w数据都是热点数据？

方案：

 限定 Redis 占用的内存，Redis 会根据自身数据淘汰策略，留下热数据到内存。所以，计算一下 50W 数据大约占用的内存，
然后设置一下 Redis 内存限制即可，并将淘汰策略为volatile-lru或者allkeys-lru。  

设置Redis最大占用内存：

打开redis配置文件，设置maxmemory参数，maxmemory是bytes字节类型
```
# In short... if you have slaves attached it is suggested that you set a lower
# limit for maxmemory so that there is some free RAM on the system for slave
# output buffers (but this is not needed if the policy is 'noeviction').
#
# maxmemory <bytes>
maxmemory 268435456
```
设置过期策略：
```java
maxmemory-policy volatile-lru
```

## Redis的LRU
Redis LRU 过期算法 Redis的过期算法是基于正经LRU的变种，之所以不使用正经LRU算法，是因为它需要消耗大量内存，对Redis现有数据结构有
较大的改造。这种变种算法是在现有的数据结构基础上使用随机采样方法来淘汰key。他是这样操作的：给每个key增加一个额外的字段，
这个字段占24bit，也就是最后一次被访问的时间戳。然后随机采样出5个key（通过maxmemory_samples来调整，采样数量越大越接近于正经的
LRU算法，但是也带来了淘汰速率的问题）淘汰掉最旧的key，直到Redis占用内存小于maxmemory为止。

在3.0以后增加了LRU淘汰池，进一步提高了与LRU算法的近似效果。