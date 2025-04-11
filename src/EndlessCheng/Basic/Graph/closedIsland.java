package EndlessCheng.Basic.Graph;

public class closedIsland {

    // https://leetcode.cn/problems/number-of-closed-islands/solutions/2312616/liang-chong-si-lu-xian-wai-hou-nei-chu-j-b1e4/
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (m < 3 || n < 3) return 0; // 如果行數或列數不足 3，此時沒有封閉島嶼，可以直接返回 0
        for (int i = 0; i < m; i++) {
            // 如果是第一行和最後一行，訪問所有格子
            // 如果不是，只訪問第一列和最後一列的格子
            int step = i == 0 || i == m - 1 ? 1 : n - 1;
            for (int j = 0; j < n; j += step)
                dfs(grid, i, j); // 從邊界出發，先標記所有非封閉島嶼
        }

        int ans = 0;
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 0) { // 從沒有訪問過的 0 出發
                    ans++; // 網格圖內部的 0 一定是封閉島嶼
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    private void dfs(int[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || grid[x][y] != 0)
            return;
        grid[x][y] = 1; // 標記 (x,y) 被訪問，避免重復訪問
        dfs(grid, x - 1, y);
        dfs(grid, x + 1, y);
        dfs(grid, x, y - 1);
        dfs(grid, x, y + 1);
    }


}
