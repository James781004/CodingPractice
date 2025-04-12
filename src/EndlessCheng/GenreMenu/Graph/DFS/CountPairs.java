package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountPairs {

    // https://leetcode.cn/problems/count-unreachable-pairs-of-nodes-in-an-undirected-graph/solutions/1625315/by-endlesscheng-7l50/
    public long countPairs(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
        }

        boolean[] vis = new boolean[n];
        long ans = 0;
        for (int i = 0, total = 0; i < n; i++) {
            if (!vis[i]) { // 未訪問的點：說明找到了一個新的連通塊
                int size = dfs(i, g, vis);

                // 連通塊中的每個點，與前面遍歷過的連通塊的每個點，都是無法互相到達的，
                // 根據乘法原理，這有size * total個
                ans += (long) size * total;
                total += size;
            }
        }
        return ans;
    }

    private int dfs(int x, List<Integer>[] g, boolean[] vis) {
        vis[x] = true; // 避免重復訪問同一個點
        int size = 1;
        for (int y : g[x]) {
            if (!vis[y]) {
                size += dfs(y, g, vis);
            }
        }
        return size;
    }


}
