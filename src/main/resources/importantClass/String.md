# String
## final
1. 类final不可继承。
2. char[] final不可改变指向，且对外不提供char[] 的修改，所以对表现为字符串不可变。
3. 安全。可安全地共享，也适合当Map的key。也是这个原因，不适合存储密码等数据。
## hash种子设为31
31是一个不大不小的质数，而且可以被优化成位运算，提高运算速度。
## 不适用频繁修改
# StringBuilder 用于拼接字符串
## 线程不安全
# StringBuffer 用于拼接字符串
## 线程安全
在StringBuilder的基础上，在方法上加上了synchronized。实现表明，在单线程的环境下，大量地频繁地拼接字符串，
StringBuffer的性能只比StringBuilder低15%左右。