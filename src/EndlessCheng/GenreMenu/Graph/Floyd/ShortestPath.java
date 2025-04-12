package EndlessCheng.GenreMenu.Graph.Floyd;

import java.util.Arrays;

public class ShortestPath {

    // https://leetcode.cn/problems/design-graph-with-shortest-path-calculator/solutions/2229013/dijkstra-suan-fa-mo-ban-pythonjavacgo-by-unmv/
    class Graph {
        private static final int INF = Integer.MAX_VALUE / 3; // 防止更新最短路時加法溢出

        private final int[][] f; // f[i][j]表示從節點i到節點j路徑最小代價

        public Graph(int n, int[][] edges) {
            f = new int[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(f[i], INF);
                f[i][i] = 0;
            }
            for (int[] e : edges) {
                f[e[0]][e[1]] = e[2]; // 添加一條邊（題目保證沒有重邊和自環）
            }
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    if (f[i][k] == INF) { // 如果i到k的路徑長度為無窮大，則跳過本次循環
                        continue;
                    }

                    // 更新i到j的最短路徑，通過比較當前路徑與經過中間節點k的路徑長度
                    for (int j = 0; j < n; j++) {
                        f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j]);
                    }
                }
            }
        }

        public void addEdge(int[] e) {
            int x = e[0], y = e[1], w = e[2], n = f.length;
            if (w >= f[x][y]) { // 如果新添加的邊權大於原圖中的邊權，則無需更新圖
                return;
            }

            // 向圖中添加新邊後，需要重新計算所有可能受影響的最短路徑
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    f[i][j] = Math.min(f[i][j], f[i][x] + w + f[y][j]);
                }
            }
        }

        public int shortestPath(int start, int end) {
            int ans = f[start][end];
            return ans < INF ? ans : -1; // 若路徑長度為無窮大（即不存在路徑），返回-1
        }
    }


}
