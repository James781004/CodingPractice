package EndlessCheng.GenreMenu.Graph.BFS;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindShortestCycle {

    // https://leetcode.cn/problems/shortest-cycle-in-a-graph/solutions/2203585/yi-tu-miao-dong-mei-ju-qi-dian-pao-bfspy-ntck/
    private List<Integer>[] g;
    private int[] dis; // dis[i] 表示從 start 到 i 的最短路長度

    public int findShortestCycle(int n, int[][] edges) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
        }
        dis = new int[n];

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) // 枚舉每個起點跑 BFS
            ans = Math.min(ans, bfs(i));
        return ans < Integer.MAX_VALUE ? ans : -1;
    }

    private int bfs(int start) {
        int ans = Integer.MAX_VALUE;
        Arrays.fill(dis, -1);
        dis[start] = 0;
        var q = new ArrayDeque<int[]>();
        q.add(new int[]{start, -1});
        while (!q.isEmpty()) {
            var p = q.poll();
            int x = p[0], fa = p[1];
            for (int y : g[x])
                if (dis[y] < 0) { // 第一次遇到
                    dis[y] = dis[x] + 1;
                    q.add(new int[]{y, x});
                } else if (y != fa) // 第二次遇到
                    ans = Math.min(ans, dis[x] + dis[y] + 1);
        }
        return ans;
    }


}
