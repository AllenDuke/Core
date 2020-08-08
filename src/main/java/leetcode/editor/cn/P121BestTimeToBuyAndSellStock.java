//给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。 
//
// 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。 
//
// 注意：你不能在买入股票前卖出股票。 
//
// 
//
// 示例 1: 
//
// 输入: [7,1,5,3,6,4]
//输出: 5
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
// 
//
// 示例 2: 
//
// 输入: [7,6,4,3,1]
//输出: 0
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
// 
// Related Topics 数组 动态规划 
// 👍 1113 👎 0


package leetcode.editor.cn;

public class P121BestTimeToBuyAndSellStock{
    public static void main(String[] args) {
        Solution solution = new P121BestTimeToBuyAndSellStock().new Solution();
        System.out.println(solution.maxProfit(new int[]{5, 1,3, 2, 7}));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxProfit(int[] prices) {

        /**
         * 暴力法，穷举所有可能
         */
        int buy=0;
        int sell=0;
        int max=0;
        for(int i=0;i<prices.length;i++){//买入
            for(int j=i;j<prices.length;j++){//卖出
                max=Math.max(max,prices[j]-prices[i]);
            }
        }
//        return max;

        /**
         * 定义状态：对于每一天 i 有2种状态，不持有，持有
         * dp[i][0], dp[i][1], 分别对应2种状态下的总资产
         *
         * 初始状态：
         * dp[0][0]= 0
         * dp[0][1]= -price[0]
         *
         * 状态转移方程：
         * dp[i][0] = max { dp[i-1][0], dp[i-1][1]+prices[i] }
         * dp[i][1] = max { dp[i-1][1], -prices[i] }
         */

         int n = prices.length;
         if(n < 2){
             return 0;
         }
         int[][] dp = new int[n][2];
         dp[0][0] = 0;
         dp[0][1] = -prices[0];
         for(int i = 1;i<n;i++){
             dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1] + prices[i]);//上一天不持股的利润和持股加上今天的股价取大值
             dp[i][1] = Math.max(dp[i-1][1],-prices[i]);//上一天已经持有或者一直到今天才持有
         }
         return dp[n-1][0];

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}