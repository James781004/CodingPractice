package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class UniquePathsIII {

    // https://leetcode.cn/problems/unique-paths-iii/solutions/2372252/liang-chong-fang-fa-hui-su-zhuang-tai-ya-26py/
    public int uniquePathsIII(int[][] grid) {
        int cnt0 = 0, sx = -1, sy = -1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) cnt0++;
                else if (grid[i][j] == 1) { // 起點
                    sx = i;
                    sy = j;
                }
            }
        }
        return dfs(grid, sx, sy, cnt0 + 1); // +1 把起點也算上
    }

    private int dfs(int[][] grid, int x, int y, int left) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || grid[x][y] < 0)
            return 0; // 不合法
        if (grid[x][y] == 2) // 到達終點
            return left == 0 ? 1 : 0; // 必須訪問所有的無障礙方格
        grid[x][y] = -1; // 標記成訪問過，因為題目要求「不能重復通過同一個方格」
        int ans = dfs(grid, x - 1, y, left - 1) + dfs(grid, x, y - 1, left - 1) +
                dfs(grid, x + 1, y, left - 1) + dfs(grid, x, y + 1, left - 1);
        grid[x][y] = 0; // 恢復現場
        return ans;
    }


}
