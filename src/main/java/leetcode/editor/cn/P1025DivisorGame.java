//爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。 
//
// 最初，黑板上有一个数字 N 。在每个玩家的回合，玩家需要执行以下操作： 
//
// 
// 选出任一 x，满足 0 < x < N 且 N % x == 0 。 
// 用 N - x 替换黑板上的数字 N 。 
// 
//
// 如果玩家无法执行这些操作，就会输掉游戏。 
//
// 只有在爱丽丝在游戏中取得胜利时才返回 True，否则返回 False。假设两个玩家都以最佳状态参与游戏。 
//
// 
//
// 
// 
//
// 示例 1： 
//
// 输入：2
//输出：true
//解释：爱丽丝选择 1，鲍勃无法进行操作。
// 
//
// 示例 2： 
//
// 输入：3
//输出：false
//解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= N <= 1000 
// 
// Related Topics 数学 动态规划 
// 👍 217 👎 0


package leetcode.editor.cn;

public class P1025DivisorGame{
    public static void main(String[] args) {
        Solution solution = new P1025DivisorGame().new Solution();
        System.out.println(solution.divisorGame(5));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean divisorGame(int N) {

        /**
         * 定义状态：
         * dp[i][0] 面对数字i 先手的结果
         * dp[i][1] 面对数字i 后手的结果
         */
        boolean[][] dp=new boolean[N+1][2];
        /**
         * 初始状态：
         */
        dp[0][0]=false;
        dp[0][1]=true;
        dp[1][0]=false;
        dp[1][1]=true;
        /**
         * 状态转移方程：
         */
        for(int i=2;i<=N;i++){
            for(int j=1;j<i;j++){
                if((i%j)==0){
                    dp[i][0]=dp[i][0]|dp[i-j][1];//当前先手 选择了j, 变为 i-j 后手
                    dp[i][1]=dp[i][1]|dp[i-j][0];//当前后手 先手选择了j, 变为 i-j 先手
                    if(dp[i][0]) break;//当前先手已经赢了，不用再往后找了
                }
            }
        }
        return dp[N][0];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}