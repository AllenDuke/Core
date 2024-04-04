# 当我们在谈论分布式的时候，我们到底在谈论些什么
## 什么是分布式
分布式可以这样理解，就是一种操作，需要经过一系列计算机才能完成。而这些计算机是通过网络进行通信的，而网络则是具有一定延迟的，甚至是不可信的。

而为什么需要多台计算机呢？自然是单台计算的资源并不能完成计算。纵深发展的单体大型机当然也能解决问题，但是不足够“弹性”，例如，一些秒杀活动等
需要动态扩张/减少资源的，大型机难以满足“刚刚好”，少了不足，多了浪费。

所以，换个思路横向拓展，通过多个小型机和网络来完成计算，小型机则可以极大地接近“刚刚好”，减少浪费。当然这也带来了额外的性能和出错风险。
比如网络通信的速度越不如本地通信的速度，同时小型机组成的网络中，任一节点出错都可能导致计算失败，而随着节点数量的增加，这种出错的风险越大。

因此，正是在这样的背景下，我们需要一系列的算法和工程实现来构建一个可靠、可用的分布式系统，使得这些节点正确地完成计算。

## 分布式理论