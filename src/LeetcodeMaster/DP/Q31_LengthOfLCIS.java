package LeetcodeMaster.DP;

import java.util.Arrays;

public class Q31_LengthOfLCIS {
//    674. 最長連續遞增序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0674.%E6%9C%80%E9%95%BF%E8%BF%9E%E7%BB%AD%E9%80%92%E5%A2%9E%E5%BA%8F%E5%88%97.md
//
//    給定一個未經排序的整數數組，找到最長且 連續遞增的子序列，並返回該序列的長度。
//
//    連續遞增的子序列 可以由兩個下標 l 和 r（l < r）確定，如果對於每個 l <= i < r，都有 nums[i] < nums[i + 1] ，那麼子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]] 就是連續遞增子序列。
//
//    示例 1： 輸入：nums = [1,3,5,4,7] 輸出：3 解釋：最長連續遞增序列是 [1,3,5], 長度為3。 盡管 [1,3,5,7] 也是升序的子序列, 但它不是連續的，因為 5 和 7 在原數組里被 4 隔開。
//
//    示例 2： 輸入：nums = [2,2,2,2,2] 輸出：1 解釋：最長連續遞增序列是 [2], 長度為1。
//
//    提示：
//
//            0 <= nums.length <= 10^4
//            -10^9 <= nums[i] <= 10^9


    /**
     * 1.dp[i] 代表當前下標最大連續值
     * 2.遞推公式 if（nums[i+1]>nums[i]） dp[i+1] = dp[i]+1
     * 3.初始化 都為1
     * 4.遍歷方向，從其那往後
     * 5.結果推導 。。。。
     *
     * @param nums
     * @return
     */
    public static int findLengthOfLCIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int res = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] > nums[i]) {
                dp[i + 1] = dp[i] + 1;
            }
            res = res > dp[i + 1] ? res : dp[i + 1];
        }
        return res;
    }


    // 貪心法
    public static int findLengthOfLCIS1(int[] nums) {
        if (nums.length == 0) return 0;
        int res = 1; // 連續子序列最少也是1
        int count = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] > nums[i]) { // 連續記錄
                count++;
            } else { // 不連續，count從頭開始
                count = 1;
            }
            if (count > res) res = count;
        }
        return res;
    }
}
