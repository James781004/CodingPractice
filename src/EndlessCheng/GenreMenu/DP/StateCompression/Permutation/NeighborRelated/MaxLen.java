package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborRelated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxLen {

    // https://leetcode.cn/problems/longest-palindromic-path-in-graph/solutions/3722469/zhong-xin-kuo-zhan-fa-zhuang-ya-dp-by-en-ai9s/
    public int maxLen(int n, int[][] edges, String label) {
        char[] s = label.toCharArray();
        // 計算理論最大值
        int[] cnt = new int[26];
        for (char ch : s) {
            cnt[ch - 'a']++;
        }
        int odd = 0;
        for (int c : cnt) {
            odd += c % 2;
        }
        int theoreticalMax = n - Math.max(odd - 1, 0); // 奇數選一個放正中心，其余全棄

        if (edges.length == n * (n - 1) / 2) { // 完全圖，可以達到理論最大值
            return theoreticalMax;
        }

        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        int[][][] memo = new int[n][n][1 << n];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }

        int ans = 0;
        for (int x = 0; x < n; x++) {
            // 奇回文串，x 作為回文中心
            ans = Math.max(ans, dfs(x, x, 1 << x, g, s, memo) + 1);
            if (ans == theoreticalMax) {
                return ans;
            }
            // 偶回文串，x 和 x 的鄰居 y 作為回文中心
            for (int y : g[x]) {
                // 保證 x < y，減少狀態個數和計算量
                if (x < y && s[x] == s[y]) {
                    ans = Math.max(ans, dfs(x, y, 1 << x | 1 << y, g, s, memo) + 2);
                    if (ans == theoreticalMax) {
                        return ans;
                    }
                }
            }
        }
        return ans;
    }

    // 計算從 x 和 y 向兩側擴展，最多還能訪問多少個節點（不算 x 和 y）
    private int dfs(int x, int y, int vis, List<Integer>[] g, char[] label, int[][][] memo) {
        if (memo[x][y][vis] >= 0) { // 之前計算過
            return memo[x][y][vis];
        }
        int res = 0;
        for (int v : g[x]) {
            if ((vis >> v & 1) > 0) { // v 在路徑中
                continue;
            }
            for (int w : g[y]) {
                if ((vis >> w & 1) == 0 && w != v && label[w] == label[v]) {
                    // 保證 v < w，減少狀態個數和計算量
                    int r = dfs(Math.min(v, w), Math.max(v, w), vis | 1 << v | 1 << w, g, label, memo);
                    res = Math.max(res, r + 2);
                }
            }
        }
        return memo[x][y][vis] = res; // 記憶化
    }


}
