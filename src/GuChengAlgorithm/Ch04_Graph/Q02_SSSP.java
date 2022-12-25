package GuChengAlgorithm.Ch04_Graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Q02_SSSP {
    // https://docs.google.com/presentation/d/1ctm0akZqJIPmEXNK6_eJnWi9M6596nzMPtNtSZmbR-Q/edit#slide=id.g872ed57b9a_0_97
    // Dijkstra算法
    public int findCheapestPriceDijkstra(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] f : flights) prices.computeIfAbsent(f[0], v -> new HashMap<>()).put(f[1], f[2]);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{0, src, k + 1}); // {cost, city, step}
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int price = cur[0], city = cur[1], stops = cur[2];
            if (city == dst) return price;
            if (stops > 0) {
                Map<Integer, Integer> neighborPrice = prices.getOrDefault(city, new HashMap<>());
                for (int nei : neighborPrice.keySet()) {
                    pq.add(new int[]{price + neighborPrice.get(nei), nei, stops - 1});
                }
            }
        }
        return -1;
    }

    // Bellman-Ford 算法
    public int findCheapestPriceBellmanFord(int n, int[][] flights, int src, int dst, int k) {
        int MAX = 100000000;
        int[] cost = new int[n];
        Arrays.fill(cost, MAX);
        cost[src] = 0;
        int res = MAX;
        for (int i = 0; i < k; i++) {
            int[] cur = Arrays.copyOf(cost, n);
            for (int[] f : flights) {
                cur[f[1]] = Math.min(f[1], cost[f[0]] + f[2]);
            }
            res = Math.min(res, cur[dst]);
            cost = cur;
        }
        return res == MAX ? -1 : res;
    }


    // https://docs.google.com/presentation/d/1ctm0akZqJIPmEXNK6_eJnWi9M6596nzMPtNtSZmbR-Q/edit#slide=id.g872ed57b9a_0_234
    // Floyd–Warshall算法
    public int networkDelay(int[][] times, int N, int K) {
        double[][] graph = new double[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
        }
        for (int i = 1; i <= N; i++) {
            graph[i][i] = 0;
        }
        for (int[] edge : times) graph[edge[0]][edge[1]] = edge[2];
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (graph[i][j] > graph[i][k] + graph[k][j]) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                    }
                }
            }
        }
        double max = Double.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            if (graph[K][i] == Double.POSITIVE_INFINITY) return -1;
            max = Math.max(max, graph[K][i]);
        }
        return (int) max;
    }
}
