# 分布式理论
## CAP
C：一致性，A：可用性，P：分区容错性。
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
### 选举过程
每个follower都有一个投票箱，初始都会投自己，每收到别人的选票就会计算（事务id,机器id）新的投票结果，并广告结果，如果某follower得到
一半以上得选票，那么它就成为了新的领导。