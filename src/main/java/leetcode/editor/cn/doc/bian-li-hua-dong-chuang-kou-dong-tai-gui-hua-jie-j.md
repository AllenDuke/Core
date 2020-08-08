```
class Solution {
    public int maxProfit(int[] prices) {

//暴力法，
    // int maxProfit = 0;
    // for(int i = 0;i<prices.length-1;i++){
    //     for(int j = i+1;j<prices.length;j++){
    //         if(prices[j] - prices[i] > maxProfit){
    //             maxProfit = prices[j] - prices[i];
    //         }
    //     }
    // }
    // return maxProfit;

//一次遍历法，找最小值
    //     int maxProfit = 0,minPrice = Integer.MAX_VALUE;
    //     for(int i = 0;i<prices.length;i++){
    //         if(minPrice > prices[i]){//如果小于最小值就替换
    //             minPrice = prices[i];
    //         }else if(maxProfit < prices[i] - minPrice){//不然就计算利润大小
    //             maxProfit = prices[i] - minPrice;
    //         }

    //     }
    //  return maxProfit;  
 
//一次遍历法，找最小值简短写法，找最小点，找最大利润。    
    //     int minPrice = Integer.MAX_VALUE, profit = 0;
    //     for(int price : prices) {
    //     minPrice = Math.min(minPrice, price);
    //     profit = Math.max(profit, price - minPrice);
    // }
    // return profit;

//动态规划：0是不持股的最大利润，1是持股的最大利润
    // int n = prices.length;
    // if(n < 2){
    //     return 0;
    // }
    // int[][] dp = new int[n][2];
    // dp[0][0] = 0;
    // dp[0][1] = -prices[0];
    // for(int i = 1;i<n;i++){
    //     dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1] + prices[i]);//上一天不持股的利润和持股加上今天的股价取大值
    //     dp[i][1] = Math.max(dp[i-1][1],-prices[i]);
    // }
    // return dp[n-1][0];

//滑动窗口
    int begin = 0,maxProfit = 0,minPrice = Integer.MAX_VALUE;
    for(int end = 0;end<prices.length;end++){
        if(prices[end] < prices[begin]){
            begin = end;
        }
        maxProfit = Math.max(maxProfit,prices[end] - prices[begin]);
    }
    return maxProfit;
    }
}
```
