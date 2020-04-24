# 过期键删除策略
1. 到期删除。
2. 惰性删除，过期放任不管，在访问的时候判断是否过期，如果过期了再执行删除操作。
3. 定期删除。

rdb不会持久化过期键，载入时遇到过期键会通过主动或者被动的方式删除。aof恢复时当遇到过期键时立即追加一条del命令。
# 内存淘汰策略
1. noeviction: 不删除策略。当达到最大内存限制时, 如果需要使用更多内存,则直接返回错误信息。（redis默认淘汰策略）
2. allkeys-lru: 在所有key中优先删除最近最少使用(less recently used ,LRU) 的 key。
3. allkeys-random: 在所有key中随机删除一部分 key。
4. volatile-lru: 在设置了超时时间（expire ）的key中优先删除最近最少使用(less recently used ,LRU) 的 key。
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