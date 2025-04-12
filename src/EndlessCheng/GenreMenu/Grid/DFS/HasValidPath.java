package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.Set;

public class HasValidPath {

    // https://leetcode.cn/problems/check-if-there-is-a-valid-path-in-a-grid/solutions/3005012/dfs-pythonjavacti-jie-by-yfsilver-pgf2/
    public int m, n;
    public int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public Set<Integer> set1 = Set.of(2, 3, 4);
    public Set<Integer> set2 = Set.of(2, 5, 6);
    public Set<Integer> set3 = Set.of(1, 4, 6);
    public Set<Integer> set4 = Set.of(1, 3, 5);

    public boolean hasValidPath(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        boolean[][] vis = new boolean[m][n];

        dfs(grid, 0, 0, vis);
        return vis[m - 1][n - 1];
    }

    public void dfs(int[][] grid, int i, int j, boolean[][] vis) {
        vis[i][j] = true;
        for (int[] d : dirs) {
            int x = i + d[0];
            int y = j + d[1];
            if (0 <= x && x < m && 0 <= y && y < n && !vis[x][y] && connect(grid, i, j, x, y)) {
                dfs(grid, x, y, vis);
            }
        }
    }

    public boolean connect(int[][] grid, int i, int j, int x, int y) {
        // 如果(x,y)在(i,j)的下邊
        if (x == i + 1 && set1.contains(grid[i][j]) && set2.contains(grid[x][y])) {
            return true;
        }

        // 如果(x,y)在(i,j)的上邊
        if (x == i - 1 && set1.contains(grid[x][y]) && set2.contains(grid[i][j])) {
            return true;
        }

        // 如果(x,y)在(i,j)的右邊
        if (y == j + 1 && set3.contains(grid[i][j]) && set4.contains(grid[x][y])) {
            return true;
        }

        // 如果(x,y)在(i,j)的左邊
        if (y == j - 1 && set3.contains(grid[x][y]) && set4.contains(grid[i][j])) {
            return true;
        }
        return false;
    }


}
