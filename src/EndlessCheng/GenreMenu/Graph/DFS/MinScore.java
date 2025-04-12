package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.List;

public class MinScore {

    // https://leetcode.cn/problems/minimum-score-of-a-path-between-two-cities/solutions/2005104/du-ti-ti-by-endlesscheng-qp1m/
    public int minScore(int n, int[][] roads) {
        int m = roads.length;
        List<int[]>[] edges = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int[] edge : roads) {
            int from = edge[0], to = edge[1], dist = edge[2];
            edges[from].add(new int[]{to, dist});
            edges[to].add(new int[]{from, dist});
        }
        boolean[] isVisited = new boolean[n + 1];
        return dfs(edges, isVisited, 1);
    }

    public int dfs(List<int[]>[] edges, boolean[] isVisited, int source) {
        isVisited[source] = true;
        int minD = Integer.MAX_VALUE;
        List<int[]> tos = edges[source];
        for (int[] edge : tos) {
            int to = edge[0], dist = edge[1];
            minD = Math.min(minD, dist);
            if (!isVisited[to]) {
                minD = Math.min(minD, dfs(edges, isVisited, to));
            }
        }
        return minD;
    }

}
