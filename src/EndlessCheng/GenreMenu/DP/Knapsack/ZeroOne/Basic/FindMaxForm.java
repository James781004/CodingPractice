package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Basic;

import java.util.Arrays;

public class FindMaxForm {

    // https://leetcode.cn/problems/ones-and-zeroes/solutions/3038333/yi-bu-bu-si-kao-cong-ji-yi-hua-sou-suo-d-lqio/
    public int findMaxForm(String[] strs, int m, int n) {
        int k = strs.length;
        int[] cnt0 = new int[k];
        for (int i = 0; i < k; i++) {
            cnt0[i] = (int) strs[i].chars().filter(ch -> ch == '0').count();
        }

        int[][][] memo = new int[strs.length][m + 1][n + 1];
        for (int[][] mat : memo) {
            for (int[] arr : mat) {
                Arrays.fill(arr, -1); // -1 表示沒有計算過
            }
        }
        return dfs(k - 1, m, n, strs, cnt0, memo);
    }

    private int dfs(int i, int j, int k, String[] strs, int[] cnt0, int[][][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j][k] != -1) { // 之前計算過
            return memo[i][j][k];
        }
        // 不選 strs[i]
        int res = dfs(i - 1, j, k, strs, cnt0, memo);
        int cnt1 = strs[i].length() - cnt0[i];
        if (j >= cnt0[i] && k >= cnt1) {
            // 選 strs[i]
            res = Math.max(res, dfs(i - 1, j - cnt0[i], k - cnt1, strs, cnt0, memo) + 1);
        }
        return memo[i][j][k] = res; // 記憶化
    }


    public int findMaxFormDP(String[] strs, int m, int n) {
        int[][][] f = new int[strs.length + 1][m + 1][n + 1];
        for (int i = 0; i < strs.length; i++) {
            int cnt0 = (int) strs[i].chars().filter(ch -> ch == '0').count();
            int cnt1 = strs[i].length() - cnt0;
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    if (j >= cnt0 && k >= cnt1) {
                        f[i + 1][j][k] = Math.max(f[i][j][k], f[i][j - cnt0][k - cnt1] + 1);
                    } else {
                        f[i + 1][j][k] = f[i][j][k];
                    }
                }
            }
        }
        return f[strs.length][m][n];
    }


    public int findMaxFormDP2(String[] strs, int m, int n) {
        int[][] f = new int[m + 1][n + 1];
        for (String s : strs) {
            int cnt0 = (int) s.chars().filter(ch -> ch == '0').count();
            int cnt1 = s.length() - cnt0;
            for (int j = m; j >= cnt0; j--) {
                for (int k = n; k >= cnt1; k--) {
                    f[j][k] = Math.max(f[j][k], f[j - cnt0][k - cnt1] + 1);
                }
            }
        }
        return f[m][n];
    }


}
