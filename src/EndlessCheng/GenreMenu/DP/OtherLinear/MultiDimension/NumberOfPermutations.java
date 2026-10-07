package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class NumberOfPermutations {

    // https://leetcode.cn/problems/count-the-number-of-inversions/solutions/2819143/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-974t/
    public int numberOfPermutations(int n, int[][] requirements) {
        int[] req = new int[n];
        Arrays.fill(req, -1);
        req[0] = 0;
        int m = 0;
        for (int[] p : requirements) {
            req[p[0]] = p[1];
            m = Math.max(m, p[1]);
        }
        if (req[0] > 0) {
            return 0;
        }

        int[][] memo = new int[n][m + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, req[n - 1], req, memo);
    }

    // perm[0] 到 perm[i] 的逆序對為 j 的排列個數
    private int dfs(int i, int j, int[] req, int[][] memo) {
        if (i == 0) {
            return 1;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res = 0;
        int r = req[i - 1];
        if (r >= 0) {
            if (j >= r && j - i <= r) {
                res = dfs(i - 1, r, req, memo);
            }
        } else {
            for (int k = 0; k <= Math.min(i, j); k++) {
                res = (res + dfs(i - 1, j - k, req, memo)) % 1_000_000_007;
            }
        }
        return memo[i][j] = res; // 記憶化
    }

    public int numberOfPermutationsDP(int n, int[][] requirements) {
        final int MOD = 1_000_000_007;
        int[] req = new int[n];
        Arrays.fill(req, -1);
        req[0] = 0;
        int m = 0;
        for (int[] p : requirements) {
            req[p[0]] = p[1];
            m = Math.max(m, p[1]);
        }
        if (req[0] > 0) {
            return 0;
        }

        int[][] f = new int[n][m + 1];
        f[0][0] = 1;
        for (int i = 1; i < n; i++) {
            int mx = req[i] < 0 ? m : req[i];
            int r = req[i - 1];
            if (r >= 0) {
                for (int j = r; j <= Math.min(i + r, mx); j++) {
                    f[i][j] = f[i - 1][r];
                }
            } else {
                for (int j = 0; j <= mx; j++) {
                    for (int k = 0; k <= Math.min(i, j); k++) {
                        f[i][j] = (f[i][j] + f[i - 1][j - k]) % MOD;
                    }
                }
            }
        }
        return f[n - 1][req[n - 1]];
    }


    public int numberOfPermutationsDP2(int n, int[][] requirements) {
        final int MOD = 1_000_000_007;
        int[] req = new int[n];
        Arrays.fill(req, -1);
        req[0] = 0;
        int m = 0;
        for (int[] p : requirements) {
            req[p[0]] = p[1];
            m = Math.max(m, p[1]);
        }
        if (req[0] > 0) {
            return 0;
        }

        int[] f = new int[m + 1];
        f[0] = 1;
        for (int i = 1; i < n; i++) {
            int mx = req[i] < 0 ? m : req[i];
            int r = req[i - 1];
            if (r >= 0) {
                Arrays.fill(f, 0, r, 0);
                Arrays.fill(f, r + 1, Math.min(i + r, mx) + 1, f[r]);
                Arrays.fill(f, Math.min(i + r, mx) + 1, m + 1, 0);
            } else {
                for (int j = 1; j <= mx; j++) { // 計算前綴和
                    f[j] = (f[j] + f[j - 1]) % MOD;
                }
                for (int j = mx; j > i; j--) { // 計算子數組和
                    f[j] = (f[j] - f[j - i - 1] + MOD) % MOD;
                }
            }
        }
        return f[req[n - 1]];
    }


}
