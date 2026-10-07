package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class CountCoprime {

    // https://leetcode.cn/problems/count-ways-to-choose-coprime-integers-from-rows/solutions/3815551/liang-chong-fang-fa-dong-tai-gui-hua-bei-4djl/
    private static final int MOD = 1_000_000_007;

    public int countCoprime(int[][] mat) {
        int m = mat.length;
        int mx = 0;
        for (int[] row : mat) {
            for (int x : row) {
                mx = Math.max(mx, x);
            }
        }

        int[][] memo = new int[m][mx + 1];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        }
        return dfs(m - 1, 0, mat, memo);
    }

    // dfs(i,g) 表示在已選元素的 GCD 為 g 的情況下，從 [0,i] 每行選一個數，最終 GCD 等於 1 的方案數
    private int dfs(int i, int g, int[][] mat, int[][] memo) {
        if (i < 0) { // 找到 GCD 等於 1 的方案
            return g == 1 ? 1 : 0;
        }
        if (memo[i][g] != -1) { // 之前計算過
            return memo[i][g];
        }
        long res = 0;

        // 枚舉從 mat[i] 這一行選 x，
        for (int x : mat[i]) {
            // 問題變成在已選元素的 GCD 為 gcd(g,x) 的情況下，
            // 從 [0,i−1] 每行選一個數，最終 GCD 等於 1 的方案數，即 dfs(i−1,gcd(g,x))
            res += dfs(i - 1, gcd(g, x), mat, memo);
        }
        return memo[i][g] = (int) (res % MOD); // 記憶化
    }


    public int countCoprimeDP(int[][] mat) {
        int m = mat.length;
        int mx = 0;
        for (int[] row : mat) {
            for (int x : row) {
                mx = Math.max(mx, x);
            }
        }

        int[][] f = new int[m + 1][mx + 1];
        f[0][1] = 1;
        for (int i = 0; i < m; i++) {
            for (int g = 0; g <= mx; g++) {
                long res = 0;
                for (int x : mat[i]) {
                    res += f[i][gcd(g, x)];
                }
                f[i + 1][g] = (int) (res % MOD);
            }
        }
        return f[m][0];
    }


    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


}
