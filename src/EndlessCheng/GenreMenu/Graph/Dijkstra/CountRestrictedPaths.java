package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class CountRestrictedPaths {

    // https://leetcode.cn/problems/number-of-restricted-paths-from-first-to-last-node/solutions/3068902/1786-cong-di-yi-ge-jie-dian-chu-fa-dao-z-bj7w/
    static final int MODULO = 1000000007;

    public int countRestrictedPaths(int n, int[][] edges) {
        List<int[]>[] adjacentArr = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            adjacentArr[i] = new ArrayList<int[]>();
        }
        for (int[] edge : edges) {
            adjacentArr[edge[0]].add(new int[]{edge[1], edge[2]});
            adjacentArr[edge[1]].add(new int[]{edge[0], edge[2]});
        }

        // 使用 Dijkstra 算法計算從結點 n 出發到每個結點的距離，即可得到每個結點的最後結點距離
        int[] distances = dijkstra(adjacentArr, n);
        int[][] distancesIndices = new int[n + 1][2];
        for (int i = 0; i <= n; i++) {
            distancesIndices[i][0] = distances[i];
            distancesIndices[i][1] = i;
        }
        Arrays.sort(distancesIndices, (a, b) -> a[0] - b[0]);
        int[] dp = new int[n + 1]; // dp[i] 表示從結點 i 出發到結點 n 的受限路徑數
        dp[n] = 1;
        for (int i = 1; i < n; i++) {
            int curr = distancesIndices[i][1];
            List<int[]> adjacent = adjacentArr[curr];
            for (int[] pair : adjacent) {
                int next = pair[0];
                if (distances[curr] > distances[next]) {
                    dp[curr] = (dp[curr] + dp[next]) % MODULO;
                }
            }
        }
        return dp[1];
    }

    public int[] dijkstra(List<int[]>[] adjacentArr, int n) {
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[n] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{n, 0});
        while (!pq.isEmpty()) {
            int[] pair = pq.poll();
            int curr = pair[0], distance = pair[1];
            if (distances[curr] < distance) {
                continue;
            }
            List<int[]> adjacent = adjacentArr[curr];
            for (int[] nextPair : adjacent) {
                int next = nextPair[0], weight = nextPair[1];
                if (distances[next] > distance + weight) {
                    distances[next] = distance + weight;
                    pq.offer(new int[]{next, distances[next]});
                }
            }
        }
        return distances;
    }

}
