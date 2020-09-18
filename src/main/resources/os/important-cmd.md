# 一些重要的命令
## top
查看cpu，内存等占用情况
## netstat -ano
查看端口占用情况
## kill <> <pid>
执行kill（默认kill -15）命令，系统会发送一个SIGTERM信号给对应的程序,大部分程序接收到SIGTERM信号后，会先释放自己的资源，然后再停止。
kill -9命令，系统给对应程序发送的信号是SIGKILL，即exit。exit信号不会被系统阻塞，所以kill -9能顺利杀掉进程