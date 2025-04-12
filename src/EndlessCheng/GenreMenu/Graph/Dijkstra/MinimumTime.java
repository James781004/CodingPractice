package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class MinimumTime {

    // https://leetcode.cn/problems/minimum-time-to-visit-disappearing-nodes/solutions/2738888/dijkstra-suan-fa-pythonjavacgo-by-endles-7q6u/
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        List<int[]>[] g = new ArrayList[n]; // 稀疏圖用鄰接表
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            int wt = e[2];
            g[x].add(new int[]{y, wt});
            g[y].add(new int[]{x, wt});
        }

        int[] dis = new int[n];
        Arrays.fill(dis, -1);
        dis[0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        pq.offer(new int[]{0, 0});
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
                if (newDis < disappear[y] && (dis[y] < 0 || newDis < dis[y])) {
                    dis[y] = newDis; // 更新 x 的鄰居的最短路
                    pq.offer(new int[]{newDis, y});
                }
            }
        }
        return dis;
    }


}
