//给定字符串 s 和 t ，判断 s 是否为 t 的子序列。 
//
// 你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。 
//
// 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"ae
//c"不是）。 
//
// 示例 1: 
//s = "abc", t = "ahbgdc" 
//
// 返回 true. 
//
// 示例 2: 
//s = "axc", t = "ahbgdc" 
//
// 返回 false. 
//
// 后续挑战 : 
//
// 如果有大量输入的 S，称作S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码
//？ 
//
// 致谢: 
//
// 特别感谢 @pbrother 添加此问题并且创建所有测试用例。 
// Related Topics 贪心算法 二分查找 动态规划 
// 👍 293 👎 0


package leetcode.editor.cn;

public class P392IsSubsequence{
    public static void main(String[] args) {
        Solution solution = new P392IsSubsequence().new Solution();
        System.out.println(solution.isSubsequence("twn", "xtxwxn"));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isSubsequence(String s, String t) {
        if(s==null||s.length()==0) return true;
        if(t==null||s.length()>t.length()) return false;

//        双指针
//        int i=0;
//        int j=0;
//        while(i<s.length()){
//            while(j<t.length()&&t.charAt(j)!=s.charAt(i)) {
//                j++;
//            }
//            if(j>=t.length()) break;
//            j++;
//            i++;
//            if(i>=s.length()) return true;
//
//        }
//        return false;

//        int sLen = s.length(), tLen = t.length();
//        boolean[][] dp = new boolean[sLen + 1][tLen + 1];
//        //初始化
//        for (int j = 0; j < tLen; j++) {
//            dp[0][j] = true;
//        }
//        //dp
//        for (int i = 1; i <= sLen; i++) {
//            for (int j = 1; j <= tLen; j++) {
//                if (s.charAt(i - 1) == t.charAt(j - 1)) {
//                    dp[i][j] = dp[i - 1][j - 1];
//                } else {
//                    dp[i][j] = dp[i][j - 1];
//                }
//            }
//        }
//        return dp[sLen][tLen];

        return dp(s,s.length()-1,t,t.length()-1);

    }

    boolean dp(String s,int i,String t,int j){
        if(i==-1) return true;
        if(j==-1) return false;
        if(s.charAt(i)==t.charAt(j)) return dp(s,i-1,t,j-1);
        else return dp(s,i,t,j-1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}