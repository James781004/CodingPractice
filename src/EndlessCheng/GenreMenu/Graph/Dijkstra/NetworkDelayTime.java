package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class NetworkDelayTime {

    // https://leetcode.cn/problems/network-delay-time/solutions/2668220/liang-chong-dijkstra-xie-fa-fu-ti-dan-py-ooe8/
    public int networkDelayTime(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2; // 防止加法溢出
        int[][] g = new int[n][n]; // 鄰接矩陣
        for (int[] row : g) {
            Arrays.fill(row, INF);
        }
        for (int[] t : times) {
            g[t[0] - 1][t[1] - 1] = t[2];
        }

        int maxDis = 0;
        int[] dis = new int[n];
        Arrays.fill(dis, INF);
        dis[k - 1] = 0;
        boolean[] done = new boolean[n];
        while (true) {
            int x = -1;
            for (int i = 0; i < n; i++) {
                if (!done[i] && (x < 0 || dis[i] < dis[x])) {
                    x = i;
                }
            }
            if (x < 0) {
                return maxDis; // 最後一次算出的最短路就是最大的
            }
            if (dis[x] == INF) { // 有節點無法到達
                return -1;
            }
            maxDis = dis[x]; // 求出的最短路會越來越大
            done[x] = true; // 最短路長度已確定（無法變得更小）
            for (int y = 0; y < n; y++) {
                // 更新 x 的鄰居的最短路
                dis[y] = Math.min(dis[y], dis[x] + g[x][y]);
            }
        }
    }


    public int networkDelayTimePQ(int[][] times, int n, int k) {
        List<int[]>[] g = new ArrayList[n]; // 鄰接表
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] t : times) {
            g[t[0] - 1].add(new int[]{t[1] - 1, t[2]});
        }

        int maxDis = 0;
        int left = n; // 未確定最短路的節點個數
        int[] dis = new int[n];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[k - 1] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        pq.offer(new int[]{0, k - 1});
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            int dx = p[0];
            int x = p[1];
            if (dx > dis[x]) { // x 之前出堆過
                continue;
            }
            maxDis = dx; // 求出的最短路會越來越大
            left--;
            for (int[] e : g[x]) {
                int y = e[0];
                int newDis = dx + e[1];
                if (newDis < dis[y]) {
                    dis[y] = newDis; // 更新 x 的鄰居的最短路
                    pq.offer(new int[]{newDis, y});
                }
            }
        }
        return left == 0 ? maxDis : -1;
    }


}
