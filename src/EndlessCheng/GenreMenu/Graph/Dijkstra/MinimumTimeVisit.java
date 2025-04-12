package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimumTimeVisit {

    // https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/solutions/2134200/er-fen-da-an-bfspythonjavacgo-by-endless-j10w/
    private final static int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int minimumTime(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (grid[0][1] > 1 && grid[1][0] > 1) // 無法「等待」
            return -1;

        var dis = new int[m][n];
        for (int i = 0; i < m; ++i)
            Arrays.fill(dis[i], Integer.MAX_VALUE);
        dis[0][0] = 0;
        var pq = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{0, 0, 0});
        for (; ; ) { // 可以等待，就一定可以到達終點
            var p = pq.poll();
            int d = p[0], i = p[1], j = p[2];
            if (d > dis[i][j]) continue;
            if (i == m - 1 && j == n - 1) // 找到終點，此時 d 一定是最短路
                return d;
            for (var q : DIRS) { // 枚舉周圍四個格子
                int x = i + q[0], y = j + q[1];
                if (0 <= x && x < m && 0 <= y && y < n) {
                    int nd = Math.max(d + 1, grid[x][y]);
                    nd += (nd - x - y) % 2; // nd 必須和 x+y 同奇偶
                    if (nd < dis[x][y]) {
                        dis[x][y] = nd; // 更新最短路
                        pq.add(new int[]{nd, x, y});
                    }
                }
            }
        }
    }

}
