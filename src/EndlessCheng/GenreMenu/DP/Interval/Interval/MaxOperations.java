package EndlessCheng.GenreMenu.DP.Interval.Interval;

import java.util.Arrays;

public class MaxOperations {

    // https://leetcode.cn/problems/maximum-number-of-operations-with-the-same-score-ii/solutions/2643756/qu-jian-dp-de-tao-lu-pythonjavacgo-by-en-nynz/
    private int[] nums;
    private int[][] memo;
    private boolean done;

    public int maxOperations(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        int res1 = helper(2, n - 1, nums[0] + nums[1]); // 刪除前兩個數
        int res2 = helper(0, n - 3, nums[n - 2] + nums[n - 1]); // 刪除後兩個數
        int res3 = helper(1, n - 2, nums[0] + nums[n - 1]); // 刪除第一個和最後一個數
        return Math.max(Math.max(res1, res2), res3) + 1; // 加上第一次操作
    }

    private int helper(int i, int j, int target) {
        if (done) { // 說明之前已經算出了 res = n / 2
            return 0;
        }
        return dfs(i, j, target);
    }

    private int dfs(int i, int j, int target) {
        if (done) {
            return 0;
        }
        if (i >= j) {
            done = true;
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res = 0;
        if (nums[i] + nums[i + 1] == target) { // 刪除前兩個數
            res = Math.max(res, dfs(i + 2, j, target) + 1);
        }
        if (nums[j - 1] + nums[j] == target) { // 刪除後兩個數
            res = Math.max(res, dfs(i, j - 2, target) + 1);
        }
        if (nums[i] + nums[j] == target) { // 刪除第一個和最後一個數
            res = Math.max(res, dfs(i + 1, j - 1, target) + 1);
        }
        return memo[i][j] = res; // 記憶化
    }


    public int maxOperationDPs(int[] nums) {
        int n = nums.length;
        int res1 = helper(nums, 2, n - 1, nums[0] + nums[1]); // 刪除前兩個數
        int res2 = helper(nums, 0, n - 3, nums[n - 2] + nums[n - 1]); // 刪除後兩個數
        int res3 = helper(nums, 1, n - 2, nums[0] + nums[n - 1]); // 刪除第一個和最後一個數
        return Math.max(res1, Math.max(res2, res3)) + 1; // 加上第一次操作
    }

    private int helper(int[] nums, int start, int end, int target) {
        int n = nums.length;
        int[][] f = new int[n + 1][n + 1];
        for (int i = end - 1; i >= start; i--) {
            for (int j = i + 1; j <= end; j++) {
                if (nums[i] + nums[i + 1] == target) { // 刪除前兩個數
                    f[i][j + 1] = Math.max(f[i][j + 1], f[i + 2][j + 1] + 1);
                }
                if (nums[j - 1] + nums[j] == target) { // 刪除後兩個數
                    f[i][j + 1] = Math.max(f[i][j + 1], f[i][j - 1] + 1);
                }
                if (nums[i] + nums[j] == target) { // 刪除第一個和最後一個數
                    f[i][j + 1] = Math.max(f[i][j + 1], f[i + 1][j] + 1);
                }
            }
        }
        return f[start][end + 1];
    }


}
