# epoll
应当结合本地文件系统来思考。

epoll_wait的时候，会复制mmap的双向链表rdlist到进程用户空间，然后清空rdlist。在LT中，可以主动地把fd插入到双向链表中，例如没读完，
或者没有写完。而在ET中，只能等待新数据到来时（读写缓冲区发生变化），由网卡中断程序来将fd插入到rdlist中。

从本质上讲：与LT相比，ET模型是通过减少系统调用来达到提高并行效率的。