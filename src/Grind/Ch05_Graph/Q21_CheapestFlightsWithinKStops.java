package Grind.Ch05_Graph;

import java.util.*;

public class Q21_CheapestFlightsWithinKStops {
    // https://leetcode.cn/problems/cheapest-flights-within-k-stops/solutions/437649/tu-bellman-fordsuan-fa-dong-tai-gui-hua-kzhan-zhon/
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, Map<Integer, Integer>> adjMap = new HashMap<>(); // src: (dst: cost)
        for (int[] flight : flights) { // 為缺省key提供初始值
            adjMap.computeIfAbsent(flight[0], key -> new HashMap()).put(flight[1], flight[2]); // 1個u，可能有多條邊，可能對應多個v
        }

        PriorityQueue<CityInfo> heap = new PriorityQueue((Comparator<CityInfo>) (city1, city2) -> city1.cost - city2.cost);
        heap.add(new CityInfo(0, src, K + 1)); // +1是關鍵，包括dst，共K+1跳。
        while (!heap.isEmpty()) {
            CityInfo curInfo = heap.remove();
            if (curInfo.hop < 0) continue;
            if (curInfo.city == dst) return curInfo.cost;

            Map<Integer, Integer> neighbors = adjMap.getOrDefault(curInfo.city, new HashMap()); // 結構 src: (dst: cost) ; 可能有的節點沒dst，只作為別的節點的dst
            for (Map.Entry<Integer, Integer> neighbor : neighbors.entrySet()) { // (dst: cost) => 根據u，更新v的成本
                heap.add(new CityInfo(curInfo.cost + neighbor.getValue(), neighbor.getKey(), curInfo.hop - 1));
            }
        }
        return -1;
    }

    private class CityInfo {
        int cost;
        int hop;
        int city;

        public CityInfo(int cost, int city, int hop) {
            this.cost = cost;
            this.city = city;
            this.hop = hop;
        }
    }


    // Bellman ford算法
    // https://leetcode.cn/problems/cheapest-flights-within-k-stops/solutions/956113/tong-ge-lai-shua-ti-la-yi-ti-si-jie-bfs-deqpt/
    int INF = 0x3f3f3f3f;

    public int findCheapestPrice2(int n, int[][] flights, int src, int dst, int k) {
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], INF);
        }

        for (int[] flight : flights) {
            g[flight[0]][flight[1]] = flight[2];
        }

        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;


        while (k-- >= 0) {
            // 因為題目要求最多k次，如果不 clone 不能保證第 i 次更新 dist 數組時用的是第 i-1 次的 dist 數組
            int[] clone = dist.clone();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int tmp = clone[i] + g[i][j];
                    if (tmp < dist[j]) {
                        dist[j] = tmp;
                    }
                }
            }
        }

        return dist[dst] >= INF ? -1 : dist[dst];
    }


    // Bellman ford 算法簡化版本
    public int findCheapestPrice3(int n, int[][] flights, int src, int dst, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        while (k-- >= 0) {
            int[] clone = dist.clone();
            for (int[] flight : flights) {
                int from = flight[0];
                int to = flight[1];
                int price = flight[2];

                // s->t = s->x + x->t
                dist[to] = Math.min(dist[to], clone[from] + price);
            }
        }

        return dist[dst] >= INF ? -1 : dist[dst];
    }
}
