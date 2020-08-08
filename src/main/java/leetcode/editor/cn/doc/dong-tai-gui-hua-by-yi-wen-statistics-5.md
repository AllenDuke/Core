### 解题思路
注意空集，想了半天，可能其实际意义是股市有开市和休市之分，空集可能说明休市吧，好坑。
从第2项开始遍历，逐项对比，遇到价位更低的股票就将股票更换，遇到价位更高的股票就记录差价，最终输出最高差价即可。

### 代码

```python3
class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        if prices == []:
            return 0
        result = [0 for i in range(len(prices))]
        buy = prices[0]
        for i,j in enumerate(prices[1:]):
            if buy >= j:
                buy = j
            else:
                result[i] = j - buy
        return max(result)
```