package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.ArrayList;

public class MinimumTime {

    // https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/solutions/2134200/er-fen-da-an-bfspythonjavacgo-by-endless-j10w/
    private final static int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int[][] grid, vis;

    public int minimumTime(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (grid[0][1] > 1 && grid[1][0] > 1) // 無法「等待」
            return -1;

        this.grid = grid;
        vis = new int[m][n];
        int left = Math.max(grid[m - 1][n - 1], m + n - 2) - 1;
        int right = (int) 1e5 + m + n; // 開區間
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(mid)) right = mid;
            else left = mid;
        }
        return right + (right + m + n) % 2;
    }

    private boolean check(int endTime) {
        int m = grid.length, n = grid[0].length;
        vis[m - 1][n - 1] = endTime;
        var q = new ArrayList<int[]>();
        q.add(new int[]{m - 1, n - 1});
        for (int t = endTime - 1; !q.isEmpty(); --t) {
            var tmp = q;
            q = new ArrayList<>();
            for (var p : tmp) {
                int i = p[0], j = p[1];
                for (var d : DIRS) { // 枚舉周圍四個格子
                    int x = i + d[0], y = j + d[1];
                    if (0 <= x && x < m && 0 <= y && y < n && vis[x][y] != endTime && grid[x][y] <= t) {
                        if (x == 0 && y == 0) return true;
                        vis[x][y] = endTime; // 用二分的值來標記，避免重復創建 vis 數組
                        q.add(new int[]{x, y});
                    }
                }
            }
        }
        return false;
    }


}
