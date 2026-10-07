package EndlessCheng.GenreMenu.DP.GameTheory;

import java.util.Arrays;

public class PredictTheWinner {

    // https://leetcode.cn/problems/predict-the-winner/solutions/3998946/jiao-ni-yi-bu-bu-si-kao-dpji-yi-hua-sou-3v95h/
    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, Integer.MIN_VALUE); // MIN_VALUE 表示該狀態沒有計算過
        }
        return dfs(0, n - 1, nums, memo) >= 0;
    }

    // 緩存裝飾器，避免重復計算 dfs（一行代碼實現記憶化）
    private int dfs(int i, int j, int[] nums, int[][] memo) {
        if (memo[i][j] != Integer.MIN_VALUE) { // 之前計算過
            return memo[i][j];
        }
        if (i == j) {
            return memo[i][j] = nums[i]; // 記憶化
        }
        return memo[i][j] = Math.max(nums[i] - dfs(i + 1, j, nums, memo),
                nums[j] - dfs(i, j - 1, nums, memo));
    }


    public boolean predictTheWinnerDP(int[] nums) {
        int n = nums.length;
        int[][] f = new int[n][n];

        for (int i = n - 1; i >= 0; i--) {
            f[i][i] = nums[i];
            for (int j = i + 1; j < n; j++) {
                f[i][j] = Math.max(nums[i] - f[i + 1][j], nums[j] - f[i][j - 1]);
            }
        }

        return f[0][n - 1] >= 0;
    }


    public boolean predictTheWinnerDP2(int[] nums) {
        int n = nums.length;
        int[] f = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            f[i] = nums[i];
            for (int j = i + 1; j < n; j++) {
                f[j] = Math.max(nums[i] - f[j], nums[j] - f[j - 1]);
            }
        }

        return f[n - 1] >= 0;
    }

}
