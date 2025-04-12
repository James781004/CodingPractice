package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.*;

public class FindAnswer {

    // https://leetcode.cn/problems/find-edges-in-shortest-paths/solutions/2749274/dijkstra-zui-duan-lu-dfsbfs-zhao-bian-py-yf48/
    public boolean[] findAnswer(int n, int[][] edges) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int[] e = edges[i];
            int x = e[0], y = e[1], w = e[2];
            g[x].add(new int[]{y, w, i});
            g[y].add(new int[]{x, w, i});
        }

        // 用 Dijkstra 算法計算出起點 0 到所有節點的最短路長度 dis
        long[] dis = new long[n];
        Arrays.fill(dis, Long.MAX_VALUE);
        dis[0] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] dxPair = pq.poll();
            long dx = dxPair[0];
            int x = (int) dxPair[1];
            if (dx > dis[x]) {
                continue;
            }
            for (int[] t : g[x]) {
                int y = t[0];
                int w = t[1];
                long newDis = dx + w;
                if (newDis < dis[y]) {
                    dis[y] = newDis;
                    pq.offer(new long[]{newDis, y});
                }
            }
        }

        boolean[] ans = new boolean[edges.length];
        // 圖不連通
        if (dis[n - 1] == Long.MAX_VALUE) {
            return ans;
        }

        // 從終點出發 BFS
        boolean[] vis = new boolean[n];
        vis[n - 1] = true;
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(n - 1);
        while (!q.isEmpty()) {
            int y = q.poll();
            for (int[] t : g[y]) {
                // 設當前在點 y，鄰居為 x，邊權為 w
                int x = t[0];
                int w = t[1];
                int i = t[2];

                if (dis[x] + w != dis[y]) {
                    continue;
                }

                // 如果滿足 dis[x]+w=dis[y] 則說明 x-y 這條邊在從 0 到 n−1 的最短路上
                ans[i] = true;
                if (!vis[x]) {
                    vis[x] = true;
                    q.offer(x);
                }
            }
        }
        return ans;
    }

}
