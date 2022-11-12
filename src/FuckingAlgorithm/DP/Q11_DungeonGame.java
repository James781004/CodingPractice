package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q11_DungeonGame {
//    https://leetcode.cn/problems/dungeon-game/
//    174. 地下城游戲
//    一些惡魔抓住了公主（P）並將她關在了地下城的右下角。地下城是由 M x N 個房間組成的二維網格。
//    我們英勇的騎士（K）最初被安置在左上角的房間裡，他必須穿過地下城並通過對抗惡魔來拯救公主。
//    騎士的初始健康點數為一個正整數。如果他的健康點數在某一時刻降至 0 或以下，他會立即死亡。
//    有些房間由惡魔守衛，因此騎士在進入這些房間時會失去健康點數（若房間裡的值為負整數，則表示騎士將損失健康點數）；
//    其他房間要麼是空的（房間裡的值為 0），要麼包含增加騎士健康點數的魔法球（若房間裡的值為正整數，則表示騎士將增加健康點數）。
//    為了盡快到達公主，騎士決定每次只向右或向下移動一步。
//    編寫一個函數來計算確保騎士能夠拯救到公主所需的最低初始健康點數。

    // 備忘錄，消除重疊子問題
    int[][] memo;

    public int dungeonMemo(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 備忘錄中都初始化為 -1
        memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return process(grid, 0, 0);
    }

    /* 定義：從 (i, j) 到達右下角，需要的初始血量至少是多少 */
    private int process(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        // base case
        // 終點如果是加血格，進入前只要有一滴血就夠了
        // 如果是扣血格，那進入前就至少要保持扣除血量+1
        if (i == m - 1 && j == n - 1) {
            return grid[i][j] >= 0 ? 1 : -grid[i][j] + 1;
        }

        // 越界狀況
        if (i == m || j == n) {
            return Integer.MAX_VALUE;
        }

        // 避免重復計算
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        // 狀態轉移邏輯: 根據後面格子的情況，推算到達當前格子時需要保持的最低血量
        int res = Math.min(
                process(grid, i, j + 1),
                process(grid, i + 1, j)
        ) - grid[i][j];

        // 騎士的生命值至少為 1
        memo[i][j] = res <= 0 ? 1 : res;

        return memo[i][j];
    }


    public int dungeonDP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];

        // base case
        // 終點如果是加血格，進入前只要有一滴血就夠了
        // 如果是扣血格，那進入前就至少要保持扣除血量+1
        dp[m - 1][n - 1] = dungeon[m - 1][n - 1] < 0 ? -dungeon[m - 1][n - 1] + 1 : 1;

        for (int i = m; i >= 0; i--) {
            for (int j = n; j >= 0; j--) {

                // 越界狀況
                if (i == m || j == n) {
                    dp[i][j] = Integer.MAX_VALUE;
                    continue;
                }

                // 終點是base case直接跳過
                if (i == m - 1 && j == n - 1) {
                    continue;
                }

                // 狀態轉移邏輯: 根據後面格子的情況，推算到達當前格子時需要保持的最低血量
                int res = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = res <= 0 ? 1 : res;
            }
        }

        return dp[0][0];
    }

    // 狀態壓縮
    public int dungeonDP2(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n + 1];

        for (int i = m; i >= 0; i--) {
            for (int j = n; j >= 0; j--) {
                if (i == m || j == n) {
                    dp[j] = Integer.MAX_VALUE;
                    continue;
                }
                if (i == m - 1 && j == n - 1) {
                    dp[j] = dungeon[i][j] < 0 ? -dungeon[i][j] + 1 : 1;
                    continue;
                }
                int res = Math.min(dp[j], dp[j + 1]) - dungeon[i][j];
                dp[j] = res <= 0 ? 1 : res;

            }
        }
        return dp[0];
    }
}
