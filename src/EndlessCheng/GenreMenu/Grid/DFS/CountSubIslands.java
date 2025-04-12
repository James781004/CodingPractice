package EndlessCheng.GenreMenu.Grid.DFS;

public class CountSubIslands {

    // https://leetcode.cn/problems/count-sub-islands/solutions/2105993/java-1905-tong-ji-zi-dao-yu-dfsbian-li-l-fnja/
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int n = grid2.length, m = grid2[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                    // 這個島嶼肯定不是子島，淹掉
                    dfs(grid2, i, j);
                }
            }
        }

        // 現在 grid2 中剩下的島嶼都是子島，計算島嶼數量
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid2[i][j] == 1) {
                    res++;
                    dfs(grid2, i, j);
                }
            }
        }
        return res;
    }

    // 從 (i, j) 開始，將與之相鄰的陸地都變成海水
    public void dfs(int[][] grid, int i, int j) {
        int n = grid.length, m = grid[0].length;
        // 超出索引邊界
        if (i < 0 || j < 0 || i >= n || j >= m) {
            return;
        }
        // 已經是海水了
        if (grid[i][j] == 0) {
            return;
        }

        // 將 (i, j) 變成海水
        grid[i][j] = 0;
        // 淹沒上下左右的陸地
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }


}
