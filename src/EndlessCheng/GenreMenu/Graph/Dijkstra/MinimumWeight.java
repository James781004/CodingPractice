package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.*;

public class MinimumWeight {

    // https://leetcode.cn/problems/minimum-weighted-subgraph-with-the-required-paths/solutions/1332967/by-endlesscheng-2mxm/
    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        List<Pair>[] g = new ArrayList[n], rg = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        Arrays.setAll(rg, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            g[x].add(new Pair(y, wt));
            rg[y].add(new Pair(x, wt));
        }

        // src1 src2 到 x 的最短路
        var d1 = dijkstra(g, src1);
        var d2 = dijkstra(g, src2);

        //  x 到 dest 的最短路，這可以通過在反圖（即所有邊反向後的圖）上求從 dest 出發的最短路得出
        var d3 = dijkstra(rg, dest);

        var ans = Long.MAX_VALUE / 3;
        for (var x = 0; x < n; x++) { // 枚舉三岔口的交點 x
            ans = Math.min(ans, d1[x] + d2[x] + d3[x]); // 累加這三條最短路的和，即為三岔口在 x 處的子圖的邊權和
        }
        return ans < Long.MAX_VALUE / 3 ? ans : -1;
    }

    long[] dijkstra(List<Pair>[] g, int start) {
        var dis = new long[g.length];
        Arrays.fill(dis, Long.MAX_VALUE / 3);
        dis[start] = 0;
        var pq = new PriorityQueue<>(Comparator.comparingLong(Pair::getValue));
        pq.offer(new Pair(start, 0L));
        while (!pq.isEmpty()) {
            var p = pq.poll();
            var x = p.getKey();
            var wt = p.getValue();
            if (wt > dis[x]) continue;
            for (var e : g[x]) {
                var y = e.getKey();
                var newD = wt + e.getValue();
                if (newD < dis[y]) {
                    dis[y] = newD;
                    pq.offer(new Pair(y, newD));
                }
            }
        }
        return dis;
    }


    class Pair {
        int key;
        long value;

        public Pair(int i, long d) {
            key = i;
            value = d;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
}
