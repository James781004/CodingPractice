package EndlessCheng.GenreMenu.DP.Grid.Advanced;

import java.util.Arrays;

public class MaxCollectedFruits {

    // https://leetcode.cn/problems/find-the-maximum-number-of-fruits-collected/solutions/2998667/nao-jin-ji-zhuan-wan-wang-ge-tu-dppython-gjcm/
    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length;
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        int ans = 0;
        // 從 (0, 0) 出發的小朋友
        for (int i = 0; i < n; i++) {
            ans += fruits[i][i];
        }

        // 從 (0, n - 1) 出發的小朋友（倒著走）
        // 從下往上走，方便 1:1 翻譯成遞推
        ans += dfs(n - 2, n - 1, fruits, memo);

        // 從 (n - 1, 0) 出發的小朋友（按照主對角線翻轉，然後倒著走）
        // 把下三角形中的數據填到上三角形中
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                fruits[j][i] = fruits[i][j];
            }
        }
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return ans + dfs(n - 2, n - 1, fruits, memo);
    }

    private int dfs(int i, int j, int[][] fruits, int[][] memo) {
        int n = fruits.length;
        if (j < n - 1 - i || j >= n) {
            return 0;
        }
        if (i == 0) {
            return fruits[i][j];
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res1 = dfs(i - 1, j - 1, fruits, memo);
        int res2 = dfs(i - 1, j, fruits, memo);
        int res3 = dfs(i - 1, j + 1, fruits, memo);
        int res = Math.max(Math.max(res1, res2), res3) + fruits[i][j];
        return memo[i][j] = res; // 記憶化
    }


    public int maxCollectedFruitsDP(int[][] fruits) {
        int n = fruits.length;
        int ans = 0;
        // 從 (0, 0) 出發的小朋友
        for (int i = 0; i < n; i++) {
            ans += fruits[i][i];
        }

        // 從 (0, n - 1) 出發的小朋友
        ans += dp(fruits);

        // 從 (n - 1, 0) 出發的小朋友（按照主對角線翻轉）
        // 把下三角形中的數據填到上三角形中
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                fruits[j][i] = fruits[i][j];
            }
        }
        return ans + dp(fruits);
    }

    int dp(int[][] fruits) {
        int n = fruits.length;
        int[][] f = new int[n - 1][n + 1];
        f[0][n - 1] = fruits[0][n - 1];
        for (int i = 1; i < n - 1; i++) {
            for (int j = Math.max(n - 1 - i, i + 1); j < n; j++) {
                f[i][j] = Math.max(Math.max(f[i - 1][j - 1], f[i - 1][j]), f[i - 1][j + 1]) + fruits[i][j];
            }
        }
        return f[n - 2][n - 1];
    }


}
