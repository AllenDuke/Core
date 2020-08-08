//给定一个整数数组 nums，求出数组从索引 i 到 j (i ≤ j) 范围内元素的总和，包含 i, j 两点。 
//
// 示例： 
//
// 给定 nums = [-2, 0, 3, -5, 2, -1]，求和函数为 sumRange()
//
//sumRange(0, 2) -> 1
//sumRange(2, 5) -> -1
//sumRange(0, 5) -> -3 
//
// 说明: 
//
// 
// 你可以假设数组不可变。 
// 会多次调用 sumRange 方法。 
// 
// Related Topics 动态规划 
// 👍 178 👎 0


package leetcode.editor.cn;

public class P303RangeSumQueryImmutable{
    public static void main(String[] args) {

        
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class NumArray {

        int[] sum;

    public NumArray(int[] nums) {
        if(nums==null||nums.length==0) return;
        sum=new int[nums.length];
        sum[0]=nums[0];
        for(int i=1;i<sum.length;i++){
            sum[i]+=sum[i-1]+nums[i];
        }
    }
    
    public int sumRange(int i, int j) {
        if(this.sum==null) return 0;
        if(i==0) return sum[j];
        return sum[j]-sum[i-1];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
//leetcode submit region end(Prohibit modification and deletion)

}