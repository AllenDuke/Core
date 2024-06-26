# 分布式理论
## CAP
C：一致性，A：可用性，P：分区容错性。

在网络分区发生时，两个分布式节点之间无法进行通信，我们对一个节点进行的修改操
作将无法同步到另外一个节点，所以数据的「一致性」将无法满足，因为两个分布式节点的
数据不再保持一致。除非我们牺牲「可用性」，也就是暂停分布式节点服务，在网络分区发
生时，不再提供修改数据的功能，直到网络状况完全恢复正常再继续对外提供服务。

一句话概括 CAP 原理就是——网络分区发生时，一致性和可用性两难全。

如果，采用AC，即在发生网络分区时，系统可能会将无法通信的节点从服务中剔除，从而牺牲了分区容忍性。（故障检测，用户请求重定向）
### redis AP
Redis 的主从数据是异步同步的，所以分布式的 Redis 系统并不满足「一致性」要求。
当客户端在 Redis 的主节点修改了数据后，立即返回，即使在主从网络断开的情况下，主节
点依旧可以正常对外提供修改服务，所以 Redis 满足「可用性」。
Redis 保证「最终一致性」，从节点会努力追赶主节点，最终从节点的状态会和主节点
的状态将保持一致。如果网络断开了，主从节点的数据将会出现大量不一致，一旦网络恢
复，从节点会采用多种策略努力追赶上落后的数据，继续尽力保持和主节点一致。
### zookeeper CP
强调一致性，在集群选举时不可用。
https://zookeeper.apache.org/doc/r3.1.2/zookeeperProgrammers.html#ch_zkGuarantees
读：会话中顺序一致性（client写入后知道version）、最终一致性（提供sync与leader同步）。
### etcd CP
https://zyy.rs/post/etcd-linearizable-read-implementation/
一致性读由leader完成，读前检查自己是否仍然是leader，防止已出现网络分区。
## Base
Ba：基本可用，s：软状态（中间态），e：最终一致性。
## raft
raft是一个共识算法（consensus algorithm），所谓共识，就是多个节点对某个事情达成一致的看法。
redis哨兵模式在选举主从切换执行者的时候有用到。

raft协议中，一个节点任一时刻处于以下三个状态之一：
1. leader
2. follower
3. candidate

所有节点启动时都是follower状态；在一段时间内如果没有收到来自leader的心跳（leader故障），从follower切换到candidate，发起选举；
如果收到majority的造成票（含自己的一票）则切换到leader状态；如果发现其他节点比自己更新，则主动切换到follower。
### 选举过程
每个candidate随机等待后都向其他candidate发出自己想成为leader的请求，每个candidate对于这样的请求，
如果自己此前没有同意过（还在等待中，也没来得及选自己），那么就同意此次请求，告知其他candidate自己选了谁。
如果一轮没能选出，如此进行下一轮。
## zab
Zookeeper Atomic Broadcast（Zookeeper 原子广播协议）。
参考：https://www.cnblogs.com/stateis0/p/9062133.html

其实原理与raft一致，只是在某些细节不一样，例如心跳方面，raft是：leader给follower发送心跳，zab是：follower去感知leader的存在。
### 选举过程
每个follower都有一个投票箱，初始都会投自己，每收到别人的选票就会计算（事务id,机器id）新的投票结果，并广告结果，如果某follower得到
一半以上得选票，那么它就成为了新的领导。

## 一致性hash算法
在Integer.MAX_VALUE范围内，形成一个环。每次删除和新增只会影响那一部分。增加虚拟节点。
参考：https://www.jianshu.com/p/e968c081f563