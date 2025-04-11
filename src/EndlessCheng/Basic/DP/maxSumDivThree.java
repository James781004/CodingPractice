package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maxSumDivThree {

    // https://leetcode.cn/problems/greatest-sum-divisible-by-three/solutions/2313700/liang-chong-suan-fa-tan-xin-dong-tai-gui-tsll/
    public int maxSumDivThree(int[] nums) {
        int n = nums.length;
        int[][] memo = new int[n][3];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        return dfs(memo, nums, n - 1, 0);
    }

    private int dfs(int[][] memo, int[] nums, int i, int j) {
        if (i < 0) return j == 0 ? 0 : Integer.MIN_VALUE; // 需要保證所選數字之和是 3 的倍數
        if (memo[i][j] != -1) return memo[i][j]; // 之前計算過

        // 如果不選 x，問題變成從 nums[0] 到 nums[i−1] 中選數
        // 如果選 x，問題變成從 nums[0] 到 nums[i−1] 中選數，所選數字之和 s 滿足 (s+x)mod3=j
        return memo[i][j] = Math.max(dfs(memo, nums, i - 1, j),
                dfs(memo, nums, i - 1, (j + nums[i]) % 3) + nums[i]);
    }


    public int maxSumDivThreeDP(int[] nums) {
        int n = nums.length;
        var f = new int[n + 1][3];
        f[0][1] = f[0][2] = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < 3; j++)
                f[i + 1][j] = Math.max(f[i][j], f[i][(j + nums[i]) % 3] + nums[i]);
        return f[n][0];
    }


}
