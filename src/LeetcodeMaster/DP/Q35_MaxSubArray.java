package LeetcodeMaster.DP;

public class Q35_MaxSubArray {
//    53. 最大子序和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0053.%E6%9C%80%E5%A4%A7%E5%AD%90%E5%BA%8F%E5%92%8C%EF%BC%88%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%EF%BC%89.md
//
//    給定一個整數數組 nums ，找到一個具有最大和的連續子數組（子數組最少包含一個元素），返回其最大和。
//
//    示例: 輸入: [-2,1,-3,4,-1,2,1,-5,4] 輸出: 6 解釋: 連續子數組 [4,-1,2,1] 的和最大，為 6。

    /**
     * 1.dp[i]代表當前下標對應的最大值
     * 2.遞推公式 dp[i] = max (dp[i-1]+nums[i],nums[i]) res = max(res,dp[i])
     * 3.初始化 都為 0
     * 4.遍歷方向，從前往後
     * 5.舉例推導結果。。。
     *
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int res = nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            res = res > dp[i] ? res : dp[i];
        }
        return res;
    }


    // 貪心解法
    public int maxSubArray2(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int sum = Integer.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            count += nums[i];
            sum = Math.max(sum, count); // 取區間累計的最大值（相當於不斷確定最大子序終止位置）
            if (count <= 0) {
                count = 0; // 相當於重置最大子序起始位置，因為遇到負數一定是拉低總和
            }
        }
        return sum;
    }
}
