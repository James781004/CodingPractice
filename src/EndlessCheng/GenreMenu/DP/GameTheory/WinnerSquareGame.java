package EndlessCheng.GenreMenu.DP.GameTheory;

import java.util.Arrays;

public class WinnerSquareGame {

    // https://leetcode.cn/problems/stone-game-iv/solutions/4004959/san-chong-xie-fa-ji-yi-hua-sou-suo-cha-b-h7b7/
    public boolean winnerSquareGame(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1); // -1 表示該狀態沒有計算過
        return dfs(n, memo);
    }

    // dfs(i) 表示在有 i 個石子的情況下，先手能否必勝
    private boolean dfs(int i, int[] memo) {
        if (i == 0) {
            return false;
        }

        if (memo[i] != -1) { // 之前計算過
            return memo[i] == 1;
        }

        for (int x = 1; x * x <= i; x++) {
            if (!dfs(i - x * x, memo)) {
                memo[i] = 1; // 記憶化
                return true;
            }
        }
        memo[i] = 0; // 記憶化
        return false;
    }

    public boolean winnerSquareGameDP(int n) {
        boolean[] f = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int x = 1; x * x <= i; x++) {
                if (!f[i - x * x]) {
                    f[i] = true;
                    break;
                }
            }
        }
        return f[n];
    }

    public boolean winnerSquareGameDP2(int n) {
        boolean[] f = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            if (f[i]) {
                continue;
            }
            for (int x = 1; x * x <= n - i; x++) {
                f[i + x * x] = true;
            }
        }
        return f[n];
    }

}
