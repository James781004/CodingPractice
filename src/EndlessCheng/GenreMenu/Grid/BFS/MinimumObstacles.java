package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MinimumObstacles {

    // https://leetcode.cn/problems/minimum-obstacle-removal-to-reach-corner/solutions/1524710/0-1-bfs-by-endlesscheng-4pjt/
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int minimumObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // 從 (i,j) 移動到與其相鄰的格子 (x,y)，視作一條從 (i,j) 到 (x,y) 的有向邊，邊權為 grid[x][y]
        int[][] dis = new int[m][n];
        for (int[] row : dis) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dis[0][0] = 0;
        Deque<int[]> q = new ArrayDeque<>(); // 0-1 BFS 本質是對 Dijkstra 算法的優化。因為邊權只有 0 和 1，可以把最小堆換成雙端隊列
        q.addFirst(new int[]{0, 0});
        while (!q.isEmpty()) {
            int[] p = q.pollFirst();
            int i = p[0];
            int j = p[1];
            for (int[] d : DIRS) {
                int x = i + d[0];
                int y = j + d[1];
                if (0 <= x && x < m && 0 <= y && y < n) {
                    int g = grid[x][y];
                    if (dis[i][j] + g < dis[x][y]) { // 更新最小代價
                        dis[x][y] = dis[i][j] + g;
                        if (g == 0) { // 遇到 0 邊權就加入隊首，這樣可以保證隊首總是最小的
                            q.addFirst(new int[]{x, y});
                        } else { // 遇到 1 邊權就加入隊尾
                            q.addLast(new int[]{x, y});
                        }
                    }
                }
            }
        }
        return dis[m - 1][n - 1];
    }


}
