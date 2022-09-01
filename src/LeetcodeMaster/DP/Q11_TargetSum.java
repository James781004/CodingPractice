package LeetcodeMaster.DP;

public class Q11_TargetSum {
//    494. 目標和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0494.%E7%9B%AE%E6%A0%87%E5%92%8C.md
//
//    難度：中等
//
//    給定一個非負整數數組，a1, a2, ..., an, 和一個目標數，S。現在你有兩個符號 + 和 -。對於數組中的任意一個整數，你都可以從 + 或 -中選擇一個符號添加在前面。
//
//    返回可以使最終數組和為目標數 S 的所有添加符號的方法數。
//
//    示例：
//
//    輸入：nums: [1, 1, 1, 1, 1], S: 3
//    輸出：5
//
//    解釋：
//            -1+1+1+1+1 = 3
//            +1-1+1+1+1 = 3
//            +1+1-1+1+1 = 3
//            +1+1+1-1+1 = 3
//            +1+1+1+1-1 = 3
//
//    一共有5種方法讓最終目標和為3。
//
//    提示：
//
//    數組非空，且長度不會超過 20 。
//    初始的數組的和不會超過 1000 。
//    保證返回的最終結果能被 32 位整數存下。


    public int findTargetSumWays(int[] nums, int target) {
        // 把數組分成兩個組合left, right
        // left + right = sum, left - right = target.
        int sum = 0;
        for (int i = 0; i < nums.length; i++) sum += nums[i];
        if ((target + sum) % 2 != 0) return 0;
        int size = (target + sum) / 2;
        if (size < 0) size = -size;

        // 將問題轉化為裝滿容量為left的背包有多少種方法
        // dp[i]表示裝滿容量為i的背包有多少種方法
        int[] dp = new int[size + 1];
        dp[0] = 1; // 裝滿容量為0的背包有1種方法（什麽也不裝）
        for (int i = 0; i < nums.length; i++) {
            for (int j = size; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]]; // 在求裝滿背包有幾種方法的情況下，遞推公式一般為： dp[j] += dp[j - nums[i]];
            }
        }
        return dp[size];
    }

}
