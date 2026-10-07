package EndlessCheng.GenreMenu.DP.Graph;

import java.util.*;

public class CountRestrictedPaths {

    // https://leetcode.cn/problems/number-of-restricted-paths-from-first-to-last-node/solutions/1808565/by-canceraaron-5v6m/
    public int countRestrictedPaths(int n, int[][] edges) {
        int N = n + 1;
        List<int[]>[] graph = new ArrayList[N];
        boolean[] visited = new boolean[N];
        int[] dist = new int[N];
        for (int i = 1; i < N; i++) {
            graph[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]].add(new int[]{edges[i][1], edges[i][2]});
            graph[edges[i][1]].add(new int[]{edges[i][0], edges[i][2]});
        }
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        dist[n] = 0;
        queue.add(new int[]{n, 0});
        while (!queue.isEmpty()) {
            int[] t = queue.poll();
            visited[t[0]] = true;
            for (int[] g : graph[t[0]]
            ) {
                if (visited[g[0]])
                    continue;
                if (dist[g[0]] > dist[t[0]] + g[1]) {
                    dist[g[0]] = dist[t[0]] + g[1];
                    queue.add(new int[]{g[0], dist[g[0]]});
                }
            }
        }
        int[] dp = new int[N];
        dp[n] = 1;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = i + 1;
            arr[i][1] = dist[i + 1];
        }
        Arrays.sort(arr, Comparator.comparingInt(o -> o[1]));
        for (int i = 0; i < n; i++) {
            int node = arr[i][0];
            for (int[] g : graph[node]
            ) {
                if (dist[g[0]] < dist[node]) {
                    dp[node] += dp[g[0]];
                    dp[node] %= 1000000007;
                }
            }
            if (node == 1)
                continue;
        }
        return dp[1];
    }


}
