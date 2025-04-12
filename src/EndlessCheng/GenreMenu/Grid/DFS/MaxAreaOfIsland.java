package EndlessCheng.GenreMenu.Grid.DFS;

public class MaxAreaOfIsland {

    // https://leetcode.cn/problems/max-area-of-island/solutions/150127/biao-zhun-javadong-tai-gui-hua-jie-fa-100-by-mark-/
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    res = Math.max(res, dfs(i, j, grid));
                }
            }
        }
        return res;
    }

    // 每次調用的時候默認num為1，進入後判斷如果不是島嶼，則直接返回0，就可以避免預防錯誤的情況。
    // 每次找到島嶼，則直接把找到的島嶼改成0，這是傳說中的沉島思想，就是遇到島嶼就把他和周圍的全部沉默。
    private int dfs(int i, int j, int[][] grid) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        int num = 1;
        num += dfs(i + 1, j, grid);
        num += dfs(i - 1, j, grid);
        num += dfs(i, j + 1, grid);
        num += dfs(i, j - 1, grid);
        return num;

    }


}
