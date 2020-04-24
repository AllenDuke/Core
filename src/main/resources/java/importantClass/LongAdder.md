# LongAdder
jdk 1.8时，Doug Lee为优化AtomicLong在高并发下，递增时大量时间用在失败重试上而设计。
## 大体结构
![LongAdder](../../images/LongAdder.PNG)
