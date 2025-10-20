package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class GetMaximumGold {

    // https://leetcode.cn/problems/path-with-maximum-gold/solutions/399776/hui-su-suan-fa-tu-wen-xiang-jie-by-sdwwld/
    public int getMaximumGold(int[][] grid) {
        // 邊界條件判斷
        if (grid == null || grid.length == 0)
            return 0;
        // 保存最大路徑值
        int res = 0;
        // 兩個for循環，遍歷每一個位置，讓他們當做起點
        // 查找最大路徑值
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 函數dfs是以坐標(i,j)為起點，查找最大路徑值
                res = Math.max(res, dfs(grid, i, j));
            }
        }
        // 返回最大路徑值
        return res;
    }

    public int dfs(int[][] grid, int i, int j) {
        // 邊界條件的判斷，x,y都不能越界，同時當前坐標的位置如果是0，表示有障礙物
        // 或者遍歷過了
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0)
            return 0;
        // 先把當前坐標的值保存下來，最後再還原
        int temp = grid[i][j];
        // 當前坐標已經訪問過了，要把他標記為0，防止再次訪問
        grid[i][j] = 0;
        // 然後沿著當前坐標的上下左右4個方向查找
        int up = dfs(grid, i, j - 1); // 往上找
        int down = dfs(grid, i, j + 1); // 往下找
        int left = dfs(grid, i - 1, j); // 往左找
        int right = dfs(grid, i + 1, j); // 往右找
        // 這裡只要4個方向的最大值即可
        int max = Math.max(left, Math.max(right, Math.max(up, down)));
        // 然後再把當前位置的值還原
        grid[i][j] = temp;
        // 返回最大路徑值
        return grid[i][j] + max;
    }


}
