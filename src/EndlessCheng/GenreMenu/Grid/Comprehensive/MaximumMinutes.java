package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.ArrayList;
import java.util.List;

public class MaximumMinutes {

    // https://leetcode.cn/problems/escape-the-spreading-fire/solutions/1460794/er-fen-bfspythonjavacgo-by-endlesscheng-ypp1/
    private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int maximumMinutes(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 開區間二分
        int left = -1, right = m * n + 1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(grid, mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left < m * n ? left : 1_000_000_000;
    }

    // 返回能否在初始位置停留 t 分鐘，並安全到達安全屋
    private boolean check(int[][] grid, int t) {
        int m = grid.length, n = grid[0].length;
        boolean[][] onFire = new boolean[m][n];
        List<int[]> f = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    onFire[i][j] = true; // 標記著火的位置
                    f.add(new int[]{i, j});
                }
            }
        }
        while (t-- > 0 && !f.isEmpty()) { // 如果火無法擴散就提前退出
            f = spreadFire(grid, onFire, f); // 火擴散
        }
        if (onFire[0][0]) {
            return false; // 起點著火，寄
        }

        // 人的 BFS
        boolean[][] vis = new boolean[m][n];
        vis[0][0] = true;
        List<int[]> q = List.of(new int[]{0, 0});
        while (!q.isEmpty()) {
            List<int[]> tmp = q;
            q = new ArrayList<>();
            for (int[] p : tmp) {
                if (onFire[p[0]][p[1]]) { // 人走到這個位置後，火也擴散到了這個位置
                    continue;
                }
                for (int[] d : DIRS) { // 枚舉上下左右四個方向
                    int x = p[0] + d[0], y = p[1] + d[1];
                    if (0 <= x && x < m && 0 <= y && y < n && !onFire[x][y] && !vis[x][y] && grid[x][y] == 0) {
                        if (x == m - 1 && y == n - 1) {
                            return true; // 我們安全了…暫時。
                        }
                        vis[x][y] = true; // 避免反復訪問同一個位置
                        q.add(new int[]{x, y});
                    }
                }
            }
            f = spreadFire(grid, onFire, f); // 火擴散
        }
        return false; // 人被火燒到，或者沒有可以到達安全屋的路
    }

    // 火的 BFS
    private List<int[]> spreadFire(int[][] grid, boolean[][] fire, List<int[]> f) {
        int m = grid.length, n = grid[0].length;
        List<int[]> tmp = f;
        f = new ArrayList<>();
        for (int[] p : tmp) {
            for (int[] d : DIRS) { // 枚舉上下左右四個方向
                int x = p[0] + d[0], y = p[1] + d[1];
                if (0 <= x && x < m && 0 <= y && y < n && !fire[x][y] && grid[x][y] == 0) {
                    fire[x][y] = true; // 標記著火的位置
                    f.add(new int[]{x, y});
                }
            }
        }
        return f;
    }


}
