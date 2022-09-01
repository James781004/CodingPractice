package LeetcodeMaster.DP;

import java.util.Arrays;

public class Q30_LengthOfLIS {
//    300.最長遞增子序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0300.%E6%9C%80%E9%95%BF%E4%B8%8A%E5%8D%87%E5%AD%90%E5%BA%8F%E5%88%97.md
//
//    給你一個整數數組 nums ，找到其中最長嚴格遞增子序列的長度。
//
//    子序列是由數組派生而來的序列，刪除（或不刪除）數組中的元素而不改變其余元素的順序。例如，[3,6,2,7] 是數組 [0,3,1,6,2,2,7] 的子序列。
//
//    示例 1： 輸入：nums = [10,9,2,5,3,7,101,18] 輸出：4 解釋：最長遞增子序列是 [2,3,7,101]，因此長度為 4 。
//
//    示例 2： 輸入：nums = [0,1,0,3,2,3] 輸出：4
//
//    示例 3： 輸入：nums = [7,7,7,7,7,7,7] 輸出：1
//
//    提示：
//
//            1 <= nums.length <= 2500
//            -10^4 <= nums[i] <= 104


    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int result = 0;
        Arrays.fill(dp, 1);
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] > result) result = dp[i]; // 取長的子序列
        }
        return result;
    }
}
