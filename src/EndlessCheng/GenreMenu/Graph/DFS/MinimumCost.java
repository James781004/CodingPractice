package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumCost {

    // https://leetcode.cn/problems/minimum-cost-walk-in-weighted-graph/solutions/2727290/xian-xing-zuo-fa-dfspythonjavacgo-by-end-i0gg/
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1], w = e[2];
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }

        int[] ids = new int[n]; // 記錄每個點所在連通塊的編號
        Arrays.fill(ids, -1);
        List<Integer> ccAnd = new ArrayList<>(); // 記錄每個連通塊的邊權的 AND
        for (int i = 0; i < n; i++) {
            if (ids[i] < 0) {
                ccAnd.add(dfs(i, ccAnd.size(), g, ids));
            }
        }

        int[] ans = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            int s = query[i][0], t = query[i][1];
            ans[i] = ids[s] != ids[t] ? -1 : ccAnd.get(ids[s]);
        }
        return ans;
    }

    private int dfs(int x, int curId, List<int[]>[] g, int[] ids) {
        ids[x] = curId; // 記錄每個點所在連通塊的編號
        int and = -1;
        for (int[] e : g[x]) {
            and &= e[1];
            if (ids[e[0]] < 0) { // 沒有訪問過
                and &= dfs(e[0], curId, g, ids);
            }
        }
        return and;
    }

}
