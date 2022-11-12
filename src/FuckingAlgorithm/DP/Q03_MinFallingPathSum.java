package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q03_MinFallingPathSum {
//    https://leetcode.cn/problems/minimum-falling-path-sum/
//    931. 下降路徑最小和
//    給你一個 n x n 的 方形 整數數組 matrix ，請你找出並返回通過 matrix 的下降路徑 的 最小和 。
//
//    下降路徑 可以從第一行中的任何元素開始，並從每一行中選擇一個元素。
//    在下一行選擇的元素和當前行所選元素最多相隔一列（即位於正下方或者沿對角線向左或者向右的第一個元素）。
//    具體來說，位置 (row, col) 的下一個元素應當是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。

    // 備忘錄
    int[][] memo;

    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int res = Integer.MAX_VALUE;

        // 備忘錄裡的值初始化為 66666
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], 66666);
        }

        // 終點可能在最後一行的任意一列
        for (int i = 0; i < n; i++) {
            res = Math.min(res, dp(matrix, n - 1, i));
        }

        return res;
    }

    int dp(int[][] matrix, int i, int j) {
        // 1、索引合法性檢查
        if (i < 0 || j < 0 ||
                i >= matrix.length ||
                j >= matrix[0].length) {

            return 99999;
        }

        // 2、base case
        if (i == 0) {
            return matrix[0][j];
        }

        // 3、查找備忘錄，防止重復計算
        if (memo[i][j] != 66666) {
            return memo[i][j];
        }
        
        // 進行狀態轉移
        memo[i][j] = matrix[i][j] + min(
                dp(matrix, i - 1, j),
                dp(matrix, i - 1, j - 1),
                dp(matrix, i - 1, j + 1)
        );

        return memo[i][j];
    }


    int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }


    public int minFallingPathSumDP(int[][] matrix) {
        int row = matrix.length, col = matrix[0].length;
        int[][] dp = new int[row][col];
        for (int i = 0; i < col; i++) dp[0][i] = matrix[0][i];
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (j == 0) dp[i][j] = matrix[i][j] + Math.min(dp[i - 1][j], dp[i - 1][j + 1]);
                else if (j == col - 1)
                    dp[i][j] = matrix[i][j] + Math.min(dp[i - 1][j], dp[i - 1][j - 1]);
                else
                    dp[i][j] = matrix[i][j] + Math.min(dp[i - 1][j], Math.min(dp[i - 1][j + 1], dp[i - 1][j - 1]));
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < col; i++) {
            res = Math.min(res, dp[row - 1][i]);
        }

        return res;
    }
}
