# clickhouse
## lsm
“磁盘顺序写” + “多个树(状数据结构)” + “冷热（新老）数据分级” + “定期归并” + “非原地更新
## clickhouse与elasticsearch
现在流行将日志的存储转移到ck，因为ck的列式存储和良好的压缩，存储成本更低，ck的写入速度更快。
但在全文搜索方面，elasticsearch还是做的比较好的。