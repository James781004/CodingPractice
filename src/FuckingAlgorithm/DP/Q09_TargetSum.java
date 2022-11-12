package FuckingAlgorithm.DP;

import java.util.HashMap;

public class Q09_TargetSum {
//    https://leetcode.cn/problems/target-sum/
//    494. 目標和
//    給你一個整數數組 nums 和一個整數 target 。
//
//    向數組中的每個整數前添加 '+' 或 '-' ，然後串聯起所有整數，可以構造一個 表達式 ：
//
//    例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然後串聯起來得到表達式 "+2-1" 。
//    返回可以通過上述方法構造的、運算結果等於 target 的不同 表達式 的數目。

    // 備忘錄
    HashMap<String, Integer> memo = new HashMap<>();

    public int findTargetSumWays(int[] nums, int target) {
        if (nums.length == 0) return 0;
        return helper(nums, 0, target);
    }

    private int helper(int[] nums, int i, int target) {
        if (i == nums.length) {
            if (target == 0) return 1;
            return 0;
        }

        // 把它倆轉成字符串才能作為哈希表的鍵
        String key = i + "," + target;

        // 避免重復計算
        if (memo.containsKey(key)) return memo.get(key);

        // 還是窮舉
        int result = helper(nums, i + 1, target - nums[i]) + helper(nums, i + 1, target + nums[i]);
        // 記入備忘錄
        memo.put(key, result);
        return result;
    }


    //    https://leetcode.cn/problems/target-sum/solution/dong-tai-gui-hua-si-kao-quan-guo-cheng-by-keepal/
    public int findTargetSumWaysDP(int[] nums, int s) {
        int sum = 0;
        for (int n : nums) sum += n;

        // 絕對值范圍超過了sum的絕對值范圍則無法得到
        if (Math.abs(s) > Math.abs(sum)) return 0;

        int len = nums.length;
        // - 0 +
        int t = sum * 2 + 1;
        int[][] dp = new int[len][t];

        // 初始化
        if (nums[0] == 0) {
            dp[0][sum] = 2;
        } else {
            dp[0][sum + nums[0]] = 1;
            dp[0][sum - nums[0]] = 1;
        }

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < t; j++) {
                // 邊界
                int l = (j - nums[i]) >= 0 ? j - nums[i] : 0;
                int r = (j + nums[i]) < t ? j + nums[i] : 0;
                dp[i][j] = dp[i - 1][l] + dp[i - 1][r];
            }
        }
        return dp[len - 1][sum + s];
    }


    // https://leetcode.cn/problems/target-sum/solution/dong-tai-gui-hua-fa-onkong-jian-fu-za-du-k1lx/
    public int findTargetSumWaysDP1(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if ((sum + target) < Math.abs(target) || (sum + target) % 2 == 1) {
            return 0;
        }

        int S = (sum + target) / 2;
        int[] dp = new int[S + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int j = S; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[S];

    }

}
