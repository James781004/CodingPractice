package EndlessCheng.GenreMenu.DP.GameTheory;

import java.util.ArrayDeque;
import java.util.Arrays;

public class StoneGameII {

    // https://leetcode.cn/problems/stone-game-ii/solutions/2125753/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-jjax/
    public int stoneGameII(int[] piles) {
        int[] s = piles;
        int n = s.length;
        for (int i = n - 2; i >= 0; i--) {
            s[i] += s[i + 1]; // 後綴和
        }

        int[][] memo = new int[n - 1][(n + 1) / 4 + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(0, 1, s, memo);
    }

    // dfs(i,M) 表示當前玩家從 piles[i] 開始拿石子，
    // 當前玩家在本回合以及之後的回合中，總共可以得到的最大石子數。
    private int dfs(int i, int m, int[] s, int[][] memo) {
        if (i + m * 2 >= s.length) {
            return s[i]; // 全拿
        }
        if (memo[i][m] != -1) { // 之前計算過
            return memo[i][m];
        }
        int mn = Integer.MAX_VALUE;
        for (int x = 1; x <= m * 2; x++) {
            mn = Math.min(mn, dfs(i + x, Math.max(m, x), s, memo));
        }
        return memo[i][m] = s[i] - mn; // 記憶化
    }


    public int stoneGameIIDP(int[] piles) {
        int n = piles.length;
        int[][] f = new int[n][n + 1];
        int s = 0;
        for (int i = n - 1; i >= 0; i--) {
            s += piles[i];
            for (int m = 1; m <= i / 2 + 1; m++) {
                if (i + m * 2 >= n) {
                    f[i][m] = s; // 全拿
                } else {
                    int mn = Integer.MAX_VALUE;
                    for (int x = 1; x <= m * 2; x++) {
                        mn = Math.min(mn, f[i + x][Math.max(m, x)]);
                    }
                    f[i][m] = s - mn;
                }
            }
        }
        return f[0][1];
    }


    public int stoneGameIIDP2(int[] piles) {
        int n = piles.length;
        int[][] f = new int[n][n + 1];
        ArrayDeque<Integer>[] colQ = new ArrayDeque[n + 1];
        Arrays.setAll(colQ, v -> new ArrayDeque<>()); // 每列的滑動窗口最小值（窗口向上滑動）

        for (int i = n - 1, s = 0; i >= 0; i--) {
            s += piles[i];
            ArrayDeque<Integer> diagQ = new ArrayDeque<>(); // 斜向的滑動窗口最小值（窗口向右下滑動）
            for (int m = 1; m <= i / 2 + 1; m++) {
                if (i + m * 2 >= n) { // 全拿
                    f[i][m] = s;
                    continue;
                }

                // f[i+1][m] 進入列窗口
                ArrayDeque<Integer> q = colQ[m];
                while (!q.isEmpty() && f[q.peekLast()][m] >= f[i + 1][m]) {
                    q.pollLast();
                }
                q.offerLast(i + 1);

                // 隊首離開列窗口
                if (q.peekFirst() > i + m) {
                    q.pollFirst();
                }

                // f[i+m*2-1][m*2-1] 和 f[i+m*2][m*2] 進入斜向窗口
                for (int x = m * 2 - 1; x <= m * 2; x++) {
                    while (!diagQ.isEmpty() && f[i + diagQ.peekLast()][diagQ.peekLast()] >= f[i + x][x]) {
                        diagQ.pollLast();
                    }
                    diagQ.offerLast(x);
                }

                // 隊首離開斜向窗口
                if (diagQ.peekFirst() <= m) {
                    diagQ.pollFirst();
                }

                f[i][m] = s - Math.min(f[q.peekFirst()][m], f[i + diagQ.peekFirst()][diagQ.peekFirst()]);
            }
        }

        return f[0][1];
    }

}
