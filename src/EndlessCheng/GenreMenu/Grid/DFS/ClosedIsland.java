package EndlessCheng.GenreMenu.Grid.DFS;

public class ClosedIsland {

    // https://leetcode.cn/problems/number-of-closed-islands/solutions/2312616/liang-chong-si-lu-xian-wai-hou-nei-chu-j-b1e4/
    private boolean closed;

    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length, ans = 0;
        if (m < 3 || n < 3) return 0;
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 0) { // 從沒有訪問過的 0 出發
                    closed = true;
                    dfs(grid, i, j);
                    if (closed) ans++;
                }
            }
        }
        return ans;
    }

    private void dfs(int[][] grid, int x, int y) {
        if (x == 0 || x == grid.length - 1 || y == 0 || y == grid[x].length - 1) {
            if (grid[x][y] == 0) closed = false; // 到達邊界
            return;
        }
        if (grid[x][y] != 0) return;
        grid[x][y] = 1; // 標記 (x,y) 被訪問，避免重復訪問
        dfs(grid, x - 1, y);
        dfs(grid, x + 1, y);
        dfs(grid, x, y - 1);
        dfs(grid, x, y + 1);
    }


}
