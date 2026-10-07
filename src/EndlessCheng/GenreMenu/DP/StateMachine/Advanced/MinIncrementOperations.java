package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

import java.util.Arrays;

public class MinIncrementOperations {

    // https://leetcode.cn/problems/minimum-increment-operations-to-make-array-beautiful/solutions/2503157/qiao-miao-she-ji-zhuang-tai-xuan-huo-bu-8547u/
    public long minIncrementOperations(int[] nums, int k) {
        int n = nums.length;
        long[][] memo = new long[n][3];
        for (long[] m : memo) {
            Arrays.fill(m, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, 0, memo, nums, k);
    }

    // 用 i 表示當前位置，j=0,1,2 表示下標 i 的右邊有（連續）零個/一個/兩個沒有增大的數
    // 定義 dfs(i,j) 表示在該狀態下對於前 i 個數的最小遞增運算數
    private long dfs(int i, int j, long[][] memo, int[] nums, int k) {
        if (i < 0) { // 遞歸邊界
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        long res = dfs(i - 1, 0, memo, nums, k) + Math.max(k - nums[i], 0); // nums[i] 增大到 k
        if (j < 2) res = Math.min(res, dfs(i - 1, j + 1, memo, nums, k)); // nums[i] 不增大
        return memo[i][j] = res; // 記憶化
    }

    public long minIncrementOperationsDP(int[] nums, int k) {
        int n = nums.length;
        // dp[i][j]表示到下標i，前面有j個位置沒有到k(包括當前位置)
        long[][] dp = new long[n + 1][3];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < 3; ++j) {
                dp[i + 1][j] = dp[i][2] + Math.max(0, k - nums[i]);     // 不得不選
                if (j > 0) {
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j - 1]);// 可以不選
                }
            }
        }
        return Arrays.stream(dp[n]).min().getAsLong();
    }


    public long minIncrementOperationsDP2(int[] nums, int k) {
        long f0 = 0, f1 = 0, f2 = 0;
        for (int x : nums) {
            long inc = f0 + Math.max(k - x, 0);
            f0 = Math.min(inc, f1);
            f1 = Math.min(inc, f2);
            f2 = inc;
        }
        return f0;
    }


}
