package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class MinArraySum {

    // https://leetcode.cn/problems/minimum-array-sum/solutions/2998867/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-0pc5/
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int n = nums.length;
        int[][][] memo = new int[n][op1 + 1][op2 + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示沒有計算過
            }
        }
        return dfs(n - 1, op1, op2, k, nums, memo);
    }

    private int dfs(int i, int op1, int op2, int k, int[] nums, int[][][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][op1][op2] != -1) { // 之前計算過
            return memo[i][op1][op2];
        }
        int x = nums[i];
        int res = dfs(i - 1, op1, op2, k, nums, memo) + x;
        if (op1 > 0) {
            res = Math.min(res, dfs(i - 1, op1 - 1, op2, k, nums, memo) + (x + 1) / 2);
        }
        if (op2 > 0 && x >= k) {
            res = Math.min(res, dfs(i - 1, op1, op2 - 1, k, nums, memo) + x - k);
            if (op1 > 0) {
                int y = (x + 1) / 2 >= k ? (x + 1) / 2 - k : (x - k + 1) / 2;
                res = Math.min(res, dfs(i - 1, op1 - 1, op2 - 1, k, nums, memo) + y);
            }
        }
        return memo[i][op1][op2] = res; // 記憶化
    }


    public int minArraySumDP(int[] nums, int k, int op1, int op2) {
        int n = nums.length;
        int[][][] f = new int[n + 1][op1 + 1][op2 + 1];
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int p = 0; p <= op1; p++) {
                for (int q = 0; q <= op2; q++) {
                    int res = f[i][p][q] + x;
                    if (p > 0) {
                        res = Math.min(res, f[i][p - 1][q] + (x + 1) / 2);
                    }
                    if (q > 0 && x >= k) {
                        res = Math.min(res, f[i][p][q - 1] + x - k);
                        if (p > 0) {
                            int y = (x + 1) / 2 >= k ? (x + 1) / 2 - k : (x - k + 1) / 2;
                            res = Math.min(res, f[i][p - 1][q - 1] + y);
                        }
                    }
                    f[i + 1][p][q] = res;
                }
            }
        }
        return f[n][op1][op2];
    }


    public int minArraySumDP2(int[] nums, int k, int op1, int op2) {
        int[][] f = new int[op1 + 1][op2 + 1];
        for (int x : nums) {
            for (int p = op1; p >= 0; p--) {
                for (int q = op2; q >= 0; q--) {
                    int res = f[p][q] + x;
                    if (p > 0) {
                        res = Math.min(res, f[p - 1][q] + (x + 1) / 2);
                    }
                    if (q > 0 && x >= k) {
                        res = Math.min(res, f[p][q - 1] + x - k);
                        if (p > 0) {
                            int y = (x + 1) / 2 >= k ? (x + 1) / 2 - k : (x - k + 1) / 2;
                            res = Math.min(res, f[p - 1][q - 1] + y);
                        }
                    }
                    f[p][q] = res;
                }
            }
        }
        return f[op1][op2];
    }


}
