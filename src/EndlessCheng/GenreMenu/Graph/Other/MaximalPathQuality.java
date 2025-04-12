package EndlessCheng.GenreMenu.Graph.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class MaximalPathQuality {

    // https://leetcode.cn/problems/maximum-path-quality-of-a-graph/solutions/1088414/bao-sou-jian-zhi-by-endlesscheng-iv6z/
    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        int n = values.length;
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            int t = e[2];
            g[x].add(new int[]{y, t});
            g[y].add(new int[]{x, t});
        }

        // Dijkstra 算法預處理起點 0 到每個節點的最短路
        int[] dis = new int[n];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{0, 0});
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            int dx = p[0];
            int x = p[1];
            if (dx > dis[x]) { // x 之前出堆過
                continue;
            }
            for (int[] e : g[x]) {
                int y = e[0];
                int newDis = dx + e[1];
                if (newDis < dis[y]) {
                    dis[y] = newDis; // 更新 x 的鄰居的最短路
                    pq.offer(new int[]{newDis, y});
                }
            }
        }

        boolean[] vis = new boolean[n];
        vis[0] = true;
        return dfs(0, 0, values[0], vis, g, values, maxTime, dis);
    }

    private int dfs(int x, int sumTime, int sumValue, boolean[] vis, List<int[]>[] g, int[] values, int maxTime, int[] dis) {
        int res = x == 0 ? sumValue : 0;
        for (int[] e : g[x]) {
            int y = e[0];
            int t = e[1];
            // 相比方法一，這里多了 dis[y]
            // 判斷下一個節點在走最短路的前提下，能否在 maxTime 時間內回到起點 0
            // 若不能則不遞歸。
            if (sumTime + t + dis[y] > maxTime) {
                continue;
            }
            if (vis[y]) {
                res = Math.max(res, dfs(y, sumTime + t, sumValue, vis, g, values, maxTime, dis));
            } else {
                vis[y] = true;
                // 每個節點的價值至多算入價值總和中一次
                res = Math.max(res, dfs(y, sumTime + t, sumValue + values[y], vis, g, values, maxTime, dis));
                vis[y] = false; // 恢覆現場
            }
        }
        return res;
    }

}
