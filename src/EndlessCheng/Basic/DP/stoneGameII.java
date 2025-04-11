package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class stoneGameII {

    // https://leetcode.cn/problems/stone-game-ii/solutions/2125753/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-jjax/
    private int[][] cache;
    private int[] s;

    public int stoneGameII(int[] piles) {
        s = piles;
        int n = s.length;
        for (int i = n - 2; i >= 0; --i)
            s[i] += s[i + 1]; // 後綴和

        cache = new int[n - 1][(n + 1) / 4 + 1];
        for (int i = 0; i < n - 1; i++)
            Arrays.fill(cache[i], -1); // -1 表示沒有訪問過
        return dfs(0, 1); // 定義 dfs(i,M) 表示當前玩家從 piles[i] 開始拿石子，當前玩家在本回合以及之後的回合中，總共可以得到的最大石子數
    }

    private int dfs(int i, int m) {
        if (i + m * 2 >= s.length) return s[i]; // 全拿
        if (cache[i][m] != -1) return cache[i][m];
        int mn = Integer.MAX_VALUE;
        for (int x = 1; x <= m * 2; ++x)
            mn = Math.min(mn, dfs(i + x, Math.max(m, x)));
        return cache[i][m] = s[i] - mn;
    }

    public int stoneGameIIDP(int[] piles) {
        int s = 0, n = piles.length;
        int[][] f = new int[n][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            s += piles[i];
            for (int m = 1; m <= i / 2 + 1; m++) {
                if (i + m * 2 >= n) f[i][m] = s; // 全拿
                else {
                    int mn = Integer.MAX_VALUE;
                    for (int x = 1; x <= m * 2; ++x)
                        mn = Math.min(mn, f[i + x][Math.max(m, x)]);
                    f[i][m] = s - mn;
                }
            }
        }
        return f[0][1];
    }


}
