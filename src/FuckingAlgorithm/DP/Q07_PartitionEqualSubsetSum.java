package FuckingAlgorithm.DP;

public class Q07_PartitionEqualSubsetSum {
//    https://leetcode.cn/problems/partition-equal-subset-sum/
//    416. 分割等和子集
//    給你一個 只包含正整數 的 非空 數組 nums 。請你判斷是否可以將這個數組分割成兩個子集，使得兩個子集的元素和相等。

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;

        // 和為奇數時，不可能劃分成兩個和相等的集合
        if (sum % 2 != 0) return false;

        int n = nums.length;
        sum = sum / 2;

        // dp[i][j] = x 表示，對於前 i 個物品（i 從 1 開始計數），
        // 當前背包的容量為 j 時，若 x 為 true，則說明可以恰好將背包裝滿，
        // 若 x 為 false，則說明不能恰好將背包裝滿。
        // 如果 dp[4][9] = true，其含義為：對於容量為 9 的背包，若只是用前 4 個物品，可以有一種方法把背包恰好裝滿
        boolean[][] dp = new boolean[n + 1][sum + 1];

        // base case
        // 背包沒有空間的時候，就相當於裝滿了，而當沒有物品可選擇的時候，肯定沒辦法裝滿背包。
        for (int i = 0; i <= n; i++) dp[i][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j - nums[i - 1] < 0) {
                    // 背包容量不足，不能裝入第 i 個物品
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 裝入或不裝入背包
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n][sum];
    }


    // 空間優化
    public boolean canPartition2(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        if (nums[0] <= target) {
            dp[nums[0]] = true;
        }

        for (int i = 1; i < len; i++) {
            for (int j = target; nums[i] <= j; j--) {
                if (dp[target]) {
                    return true;
                }
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }
        return dp[target];
    }


    // 記憶化搜索
    public boolean canPartitionMemo(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (sum % 2 != 0) return false;
        memo = new Boolean[sum / 2 + 1][nums.length];
        return process(nums, sum / 2, 0);
    }

    Boolean[][] memo;

    public boolean process(int[] nums, int bag, int index) {
        if (index < 0 || index >= nums.length) {
            return false;
        }

        if (bag == 0) {
            return true;
        } else if (bag < 0) {
            return false;
        }

        if (memo[bag][index] != null) {
            return memo[bag][index];
        }

        memo[bag][index] = process(nums, bag - nums[index], index + 1)
                || process(nums, bag, index + 1);
        return memo[bag][index];
    }
}
