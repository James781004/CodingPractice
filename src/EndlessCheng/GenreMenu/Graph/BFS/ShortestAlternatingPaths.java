package EndlessCheng.GenreMenu.Graph.BFS;

import java.util.*;

public class ShortestAlternatingPaths {

    // https://leetcode.cn/problems/shortest-path-with-alternating-colors/solutions/2087881/python3javacgo-yi-ti-yi-jie-bfsqing-xi-t-ag0i/
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<Integer>[][] g = new List[2][n];
        for (var f : g) {
            Arrays.setAll(f, k -> new ArrayList<>());
        }

        // g[0] 存儲所有紅色邊
        for (var e : redEdges) {
            g[0][e[0]].add(e[1]);
        }

        // g[1] 存儲所有藍色邊
        for (var e : blueEdges) {
            g[1][e[0]].add(e[1]);
        }
        Deque<int[]> q = new ArrayDeque<>(); // 用來存儲當前搜索到的節點，以及當前邊的顏色
        q.offer(new int[]{0, 0}); // 首先將起點 0 和起點邊的顏色 0 或 1 入隊，表示從起點出發，且當前是紅色或藍色邊
        q.offer(new int[]{0, 1});
        boolean[][] vis = new boolean[n][2]; // 用來存儲已經搜索過的節點，以及當前邊的顏色
        int[] ans = new int[n]; // 用來存儲每個節點到起點的最短距離。初始時將 ans 數組中的所有元素初始化為 −1。
        Arrays.fill(ans, -1);
        int d = 0; // 用來表示當前搜索的層數，即當前搜索到的節點到起點的距離
        while (!q.isEmpty()) {
            for (int k = q.size(); k > 0; --k) {
                var p = q.poll();
                int i = p[0], c = p[1];
                if (ans[i] == -1) { // 第一次訪問到的此點，此次就是此點的最小路徑，直接ans[i]=d;
                    ans[i] = d;
                }
                vis[i][c] = true;
                c ^= 1; // 此點的下一條邊應該變換顏色
                for (int j : g[c][i]) { // 遍歷查找此點經正確顏色的出邊後可以到達的點
                    if (!vis[j][c]) {
                        q.offer(new int[]{j, c}); // 記錄到隊列中，下次輪到訪問它
                    }
                }
            }
            ++d; // 進入下一輪，路徑長度需要+1
        }
        return ans;
    }


}
