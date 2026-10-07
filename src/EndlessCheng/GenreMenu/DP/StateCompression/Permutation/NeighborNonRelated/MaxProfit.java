package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

import java.util.Arrays;

public class MaxProfit {

    // https://leetcode.cn/problems/maximum-profit-from-valid-topological-order-in-dag/solutions/3663054/pai-lie-xing-zhuang-ya-dpcong-ji-yi-hua-z67rp/
    public int maxProfit(int n, int[][] edges, int[] score) {
        if (edges.length == 0) {
            Arrays.sort(score);
            int ans = 0;
            for (int i = 0; i < n; i++) {
                ans += score[i] * (i + 1);
            }
            return ans;
        }

        // 記錄每個節點的先修課（直接前驅）
        int[] pre = new int[n];
        for (int[] e : edges) {
            pre[e[1]] |= 1 << e[0];
        }

        int[] memo = new int[1 << n];
        return dfs(0, pre, score, memo);
    }

    // 在已學課程集合為 s 的情況下，學完剩余未學課程可以獲得的最大利潤
    private int dfs(int s, int[] pre, int[] score, int[] memo) {
        if (memo[s] > 0) { // 之前計算過
            return memo[s];
        }
        int res = 0;
        int i = Integer.bitCount(s); // 已學課程數
        // 枚舉還沒學過的課程 j，且 j 的所有先修課都學完了
        for (int j = 0; j < pre.length; j++) {
            if ((s >> j & 1) == 0 && (s | pre[j]) == s) {
                res = Math.max(res, dfs(s | 1 << j, pre, score, memo) + score[j] * (i + 1));
            }
        }
        return memo[s] = res; // 記憶化
    }


    public int maxProfitDP(int n, int[][] edges, int[] score) {
        if (edges.length == 0) {
            Arrays.sort(score);
            int ans = 0;
            for (int i = 0; i < n; i++) {
                ans += score[i] * (i + 1);
            }
            return ans;
        }

        // 記錄每個節點的先修課（直接前驅）
        int[] pre = new int[n];
        for (int[] e : edges) {
            pre[e[1]] |= 1 << e[0];
        }

        int u = 1 << n;
        int[] f = new int[u];

        for (int s = u - 2; s >= 0; s--) {
            int i = Integer.bitCount(s); // 已學課程數
            // 枚舉還沒學過的課程 j，且 j 的所有先修課都學完了
            for (int j = 0; j < n; j++) {
                if ((s >> j & 1) == 0 && (s | pre[j]) == s) {
                    f[s] = Math.max(f[s], f[s | 1 << j] + score[j] * (i + 1));
                }
            }
        }
        return f[0];
    }


    // 刷表法（正序）
    public int maxProfitDP2(int n, int[][] edges, int[] score) {
        if (edges.length == 0) {
            Arrays.sort(score);
            int ans = 0;
            for (int i = 0; i < n; i++) {
                ans += score[i] * (i + 1);
            }
            return ans;
        }

        // 記錄每個節點的先修課（直接前驅）
        int[] pre = new int[n];
        for (int[] e : edges) {
            pre[e[1]] |= 1 << e[0];
        }

        int u = 1 << n;
        int[] f = new int[u]; //  f[S] 表示已學課程集合為 S 時，從已學課程中可以獲得的最大利潤
        Arrays.fill(f, -1);
        f[0] = 0;

        for (int s = 0; s < u - 1; s++) {
            if (f[s] < 0) { // 不合法狀態，比如已經學完後面的課程，但前面的課程還沒學
                continue;
            }
            int i = Integer.bitCount(s); // 已學課程數
            // 枚舉還沒學過的課程 j，且 j 的所有先修課都學完了
            for (int j = 0; j < n; j++) {
                if ((s >> j & 1) == 0 && (s | pre[j]) == s) {
                    int newS = s | 1 << j; // pre[j]⊆S 用 f[S]+score[j]⋅(∣S∣+1) 更新 f[S∪{j}] 的最大值
                    f[newS] = Math.max(f[newS], f[s] + score[j] * (i + 1));
                }
            }
        }
        return f[u - 1];
    }


}
