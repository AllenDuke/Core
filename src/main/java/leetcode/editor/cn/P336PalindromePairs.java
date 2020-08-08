//给定一组 互不相同 的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。 
//
// 
//
// 示例 1： 
//
// 输入：["abcd","dcba","lls","s","sssll"]
//输出：[[0,1],[1,0],[3,2],[2,4]] 
//解释：可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
// 
//
// 示例 2： 
//
// 输入：["bat","tab","cat"]
//输出：[[0,1],[1,0]] 
//解释：可拼接成的回文串为 ["battab","tabbat"] 
// Related Topics 字典树 哈希表 字符串 
// 👍 135 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

public class P336PalindromePairs{
    public static void main(String[] args) {
        Solution solution = new P336PalindromePairs().new Solution();
        for (List<Integer> list : solution.palindromePairs(new String[]{"ba","abc"})) {
            System.out.println(list.get(0) + " " + list.get(1));
        }
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
        public List<List<Integer>> palindromePairs(String[] words) {
            List<List<Integer>> ans=new ArrayList();
            for(int i=0;i<words.length;i++){
                for(int j=0;j<words.length;j++){
                    if(i==j) continue;
                    if(match(words[i],words[j])){
                        List<Integer> list=new ArrayList();
                        list.add(i);
                        list.add(j);
                        ans.add(list);
                    }
                }
            }
            return ans;
        }

        public boolean match(String s1,String s2){
            int l=0;
            int r=s2.length()-1;
            while(l<s1.length()&&r>=0&&s1.charAt(l)==s2.charAt(r)){
                l++;
                r--;
            }
            if(l<s1.length()){
                if(r>=0) return false;
                r=s1.length()-1;
                while(l<s1.length()&&r>=0&&s1.charAt(l)==s1.charAt(r)){
                    l++;
                    r--;
                }
            }else{
                if(l<s1.length()) return false;
                l=0;
                while(l<s2.length()&&r>=0&&s2.charAt(l)==s2.charAt(r)){
                    l++;
                    r--;
                }
            }
            if(l<r) return false;
            return true;
        }
}
//leetcode submit region end(Prohibit modification and deletion)

}