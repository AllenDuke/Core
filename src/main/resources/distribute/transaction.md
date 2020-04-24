# 分布式事务
![distribute-transaction-2pc](../images/distribute-transaction-2pc.PNG)
![distribute-transaction-tcc](../images/distribute-transaction-tcc.PNG)

增加中间状态，预阶段是直接commit中间状态，最后确认或取消，达到最终状态或者初始状态。与上面的不同，减少了锁的粒度，提高了并发量。
其实关键就在于，最后各服务的状态是正确的，而上面没有中间状态，就只能不commit或是rollback，一直持有锁直至所有服务完成预提交。