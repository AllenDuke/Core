先写数据库，再让缓存失效，期间如果有读请求，将会读到旧数据，等写完数据库缓存失效，再次读，将会把新数据放到缓存。问题：写线程挂掉。

如果先删除缓存，再写数据库，期间如果有读请求，那么将会把旧数据读出来放到缓存，缓存中将一直存放旧数据。

1.第一种方案：采用延时双删策略

在写库前后都进行redis.del(key)操作，并且设定合理的超时时间。
伪代码如下：
```java
public void write(String key,Object data){ 
redis.delKey(key);
db.updateData(data); 
Thread.sleep(500); 
redis.delKey(key); 
}
```
具体的步骤就是：
1. 先删除缓存；
2. 再写数据库；
3. 休眠500毫秒；
4. 再次删除缓存。
那么，这个500毫秒怎么确定的，具体该休眠多久呢？
需要评估自己的项目的读数据业务逻辑的耗时。这么做的目的，就是确保读请求结束，写请求可以删除读请求造成的缓存脏数据。
当然这种策略还要考虑redis和数据库主从同步的耗时。最后的的写数据的休眠时间：则在读数据业务逻辑的耗时基础上，加几百ms即可。
比如：休眠1秒。

设置缓存过期时间
从理论上来说，给缓存设置过期时间，是保证最终一致性的解决方案。所有的写操作以数据库为准，只要到达缓存过期时间，
则后面的读请求自然会从数据库中读取新值然后回填缓存。

该方案的弊端
结合双删策略+缓存超时设置，这样最差的情况就是在超时时间内数据存在不一致，而且又增加了写请求的耗时。

2、第二种方案：异步更新缓存(基于订阅binlog的同步机制)

技术整体思路：
MySQL binlog增量订阅消费+消息队列+增量数据更新到redis
读Redis：热数据基本都在Redis
写MySQL:增删改都是操作MySQL
更新Redis数据：MySQ的数据操作binlog，来更新到Redis

Redis更新
1）数据操作主要分为两大块：
一个是全量(将全部数据一次写入到redis)
一个是增量（实时更新）
这里说的是增量,指的是mysql的update、insert、delate变更数据。

2）读取binlog后分析 ，利用消息队列,推送更新各台的redis缓存数据。
这样一旦MySQL中产生了新的写入、更新、删除等操作，就可以把binlog相关的消息推送至Redis，Redis再根据binlog中的记录，对Redis进行更新。
其实这种机制，很类似MySQL的主从备份机制，因为MySQL的主备也是通过binlog来实现的数据一致性。

这里可以结合使用canal(阿里的一款开源框架)，通过该框架可以对MySQL的binlog进行订阅，而canal正是模仿了mysql的slave数据库的备份请求，
使得Redis的数据更新达到了相同的效果。

当然，这里的消息推送工具你也可以采用别的第三方：kafka、rabbitMQ等来实现推送更新Redis。

以上就是Redis和MySQL数据一致性详解。