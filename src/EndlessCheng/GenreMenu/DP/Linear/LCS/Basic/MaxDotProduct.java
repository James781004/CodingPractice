package EndlessCheng.GenreMenu.DP.Linear.LCS.Basic;

public class MaxDotProduct {

    // https://leetcode.cn/problems/max-dot-product-of-two-subsequences/solutions/3054763/ling-shen-dpti-dan-da-qia-ji-yi-hua-sou-1r3yz/
    public int maxDotProduct(int[] s, int[] t) {
        int n = s.length;
        int m = t.length;
        int[][] memo = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                memo[i][j] = Integer.MIN_VALUE;
            }
        }
        return dfs(s, t, n - 1, m - 1, memo);
    }

    private int dfs(int[] s, int[] t, int i, int j, int[][] memo) {
        if (i < 0 || j < 0) {
            return Integer.MIN_VALUE / 2; // 避免整數溢出
        }
        if (memo[i][j] != Integer.MIN_VALUE) {
            return memo[i][j];
        }
        int v = s[i] * t[j];

        int res = Math.max(v, Math.max(dfs(s, t, i - 1, j - 1, memo) + v,
                Math.max(dfs(s, t, i - 1, j, memo), dfs(s, t, i, j - 1, memo))));
        memo[i][j] = res;
        return res;
    }


    public int maxDotProductDP(int[] s, int[] t) {
        int n = s.length;
        int m = t.length;
        int[][] f = new int[n + 1][m + 1];

        // 初始化為 Integer.MIN_VALUE / 2（避免整數溢出）
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                f[i][j] = Integer.MIN_VALUE / 2;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int v = s[i] * t[j];
                // Java需用嵌套 Math.max 處理4個選項
                f[i + 1][j + 1] = Math.max(
                        Math.max(f[i][j] + v, v),
                        Math.max(f[i][j + 1], f[i + 1][j])
                );
            }
        }
        return f[n][m];
    }


    public int maxDotProductDP2(int[] s, int[] t) {
        int n = s.length;
        int m = t.length;
        int[] f = new int[m + 1];

        // 初始化：f[0..m] = Integer.MIN_VALUE / 2
        for (int j = 0; j <= m; j++) {
            f[j] = Integer.MIN_VALUE / 2;
        }

        for (int i = 0; i < n; i++) {
            int pre = f[0]; // 保存上一行的 f[i][0]（即 f[i-1][0]）
            for (int j = 0; j < m; j++) {
                int v = s[i] * t[j];
                int tmp = f[j + 1]; // 保存上一行的 f[i][j+1]（即 f[i-1][j+1]）

                // 狀態轉移：f[i][j+1] = max(上一行[j] + v, v, 當前行[j], 上一行[j+1])
                f[j + 1] = Math.max(Math.max(pre + v, v), Math.max(f[j], f[j + 1]));

                pre = tmp; // 為下一輪 j+1 准備：pre = 上一行的 f[i-1][j+1]
            }
        }
        return f[m];
    }

}
