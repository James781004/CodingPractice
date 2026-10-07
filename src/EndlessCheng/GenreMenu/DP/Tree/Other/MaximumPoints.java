package EndlessCheng.GenreMenu.DP.Tree.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaximumPoints {

    // https://leetcode.cn/problems/maximum-points-after-collecting-coins-from-all-nodes/solutions/2503152/shu-xing-dp-ji-yi-hua-sou-suo-by-endless-phzx/
    public int maximumPoints(int[][] edges, int[] coins, int k) {
        int n = coins.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        int[][] memo = new int[n][14];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(0, 0, -1, memo, g, coins, k);
    }

    private int dfs(int i, int j, int fa, int[][] memo, List<Integer>[] g, int[] coins, int k) {
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res1 = (coins[i] >> j) - k;
        int res2 = coins[i] >> (j + 1);
        for (int ch : g[i]) {
            if (ch == fa) continue;
            res1 += dfs(ch, j, i, memo, g, coins, k); // 不右移
            if (j < 13) { // j+1 >= 14 相當於 res2 += 0，無需遞歸
                res2 += dfs(ch, j + 1, i, memo, g, coins, k); // 右移
            }
        }
        return memo[i][j] = Math.max(res1, res2); // 記憶化
    }


}
