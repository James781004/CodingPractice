package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maximumSum {

    // https://leetcode.cn/problems/maximum-subarray-sum-with-one-deletion/solutions/2321829/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-hzz6/
    private int[] arr;
    private int[][] memo;

    public int maximumSum(int[] arr) {
        this.arr = arr;
        int n = arr.length;
        int ans = Integer.MIN_VALUE;
        memo = new int[n][2];
        for (int[] row : memo)
            Arrays.fill(row, Integer.MIN_VALUE);
        for (int i = 0; i < n; i++)
            ans = Math.max(ans, Math.max(dfs(i, 0), dfs(i, 1)));
        return ans;
    }

    private int dfs(int i, int j) {
        if (i < 0) return Integer.MIN_VALUE / 2; // 除 2 防止負數相加溢出
        if (memo[i][j] != Integer.MIN_VALUE) return memo[i][j]; // 之前計算過
        if (j == 0) return memo[i][j] = Math.max(dfs(i - 1, 0), 0) + arr[i];
        return memo[i][j] = Math.max(dfs(i - 1, 1) + arr[i], dfs(i - 1, 0));
    }


    public int maximumSumDP(int[] arr) {
        int n = arr.length;
        int ans = Integer.MIN_VALUE;
        int[][] f = new int[n + 1][2];
        Arrays.fill(f[0], Integer.MIN_VALUE / 2); // 除 2 防止負數相加溢出
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
