package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

import java.util.Arrays;

public class MaximumSum {

    // https://leetcode.cn/problems/maximum-subarray-sum-with-one-deletion/solutions/2321829/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-hzz6/
    public int maximumSum(int[] arr) {
        int n = arr.length;
        int[][] memo = new int[n][2];
        for (int[] row : memo) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }

        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, Math.max(dfs(i, 0, arr, memo), dfs(i, 1, arr, memo)));
        }
        return ans;
    }

    private int dfs(int i, int j, int[] arr, int[][] memo) {
        if (i < 0) {
            return Integer.MIN_VALUE / 2; // 除 2 防止負數相加溢出
        }
        if (memo[i][j] != Integer.MIN_VALUE) {
            return memo[i][j]; // 之前計算過
        }
        if (j == 0) {
            return memo[i][j] = Math.max(dfs(i - 1, 0, arr, memo), 0) + arr[i];
        }
        return memo[i][j] = Math.max(dfs(i - 1, 1, arr, memo) + arr[i], dfs(i - 1, 0, arr, memo));
    }


    public int maximumSumDP(int[] arr) {
        int n = arr.length;
        int[][] f = new int[n + 1][2]; // 子數組的右端點下標是 i，不能/必須刪除數字的情況下，子數組元素和的最大值
        Arrays.fill(f[0], Integer.MIN_VALUE / 2); // 除 2 防止負數相加溢出
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = Math.max(f[i][0], 0) + arr[i];
            f[i + 1][1] = Math.max(f[i][1] + arr[i], f[i][0]);
            ans = Math.max(ans, Math.max(f[i + 1][0], f[i + 1][1]));
        }
        return ans;
    }


    public int maximumSumDP2(int[] arr) {
        int ans = Integer.MIN_VALUE / 2;
        int f0 = ans;
        int f1 = ans;
        for (int x : arr) {
            f1 = Math.max(f1 + x, f0);
            f0 = Math.max(f0, 0) + x;
            ans = Math.max(ans, Math.max(f0, f1));
        }
        return ans;
    }


}
