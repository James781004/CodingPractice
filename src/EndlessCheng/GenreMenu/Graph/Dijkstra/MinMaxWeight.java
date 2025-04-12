package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class MinMaxWeight {

    // https://leetcode.cn/problems/minimize-the-maximum-edge-weight-of-graph/solutions/3045060/liang-chong-fang-fa-er-fen-da-an-dijkstr-eb7d/
    public int minMaxWeight(int n, int[][] edges, int threshold) {
        if (edges.length < n - 1) {
            return -1;
        }

        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) { // 每條邊反著存就可以把問題轉換為從0到所有點都有路徑
            int x = e[0], y = e[1], w = e[2];
            g[y].add(new int[]{x, w});
        }

        // dis[i]的意思是，從0到i的路徑上的最大邊權
        int[] dis = new int[n];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0}); // (路徑最大邊權, 節點編號)
        int ans = 0;
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            int d = p[0], x = p[1];

            // 如果d > dis[x],那這條邊已經超過當前的最小最大邊權
            // 對於後面的路徑更新來說已經沒有意義了
            if (d > dis[x]) {
                continue;
            }
            ans = d;
            n--;

            // 使用當前路徑的最大邊權來更新接下來可能的路徑上的最大邊權值
            // 對於所有連接 x 的邊都可能是一條可以更新的路徑
            for (int[] e : g[x]) {
                int y = e[0];

                // 計算這條路徑(-d -> x -w -> y)上的最大邊權
                // 因為要求的是最小的最大值，所以從 x -> y 這一路上最大的(max(d, w))邊權和dist[y]去對比，
                // 這樣才能更新到達y的路徑上的最小最大邊權。
                int newD = Math.max(d, e[1]);
                if (newD < dis[y]) {
                    dis[y] = newD;
                    pq.offer(new int[]{newD, y});
                }
            }
        }

        return n > 0 ? -1 : ans;
    }


}
