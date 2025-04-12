package EndlessCheng.GenreMenu.Graph.BFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortestDistanceAfterQueries {

    // https://leetcode.cn/problems/shortest-distance-after-road-addition-queries-i/solutions/2869215/liang-chong-fang-fa-bfs-dppythonjavacgo-mgunf/
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        List<Integer>[] g = new ArrayList[n - 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            g[i].add(i + 1);
        }

        int[] ans = new int[queries.length];
        int[] vis = new int[n - 1];
        for (int i = 0; i < queries.length; i++) {
            g[queries[i][0]].add(queries[i][1]);
            ans[i] = bfs(i + 1, g, vis, n); // 每次加邊後，重新跑一遍 BFS，求出從 0 到 n−1 的最短路
        }
        return ans;
    }

    private int bfs(int i, List<Integer>[] g, int[] vis, int n) {
        List<Integer> q = new ArrayList<>();
        q.add(0);
        for (int step = 1; ; step++) {
            List<Integer> tmp = q;
            q = new ArrayList<>();
            for (int x : tmp) {
                for (int y : g[x]) {
                    if (y == n - 1) {
                        return step;
                    }
                    if (vis[y] != i) {
                        vis[y] = i; // 為避免反復創建 vis 數組，可以在 vis 中保存當前節點是第幾次詢問訪問的
                        q.add(y);
                    }
                }
            }
        }
    }


}
