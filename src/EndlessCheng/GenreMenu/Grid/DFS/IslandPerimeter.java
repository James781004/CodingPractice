package EndlessCheng.GenreMenu.Grid.DFS;

public class IslandPerimeter {

    // https://leetcode.cn/problems/island-perimeter/solutions/151724/tu-jie-jian-ji-er-qiao-miao-de-dfs-fang-fa-java-by/
    public int islandPerimeter(int[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    // 題目限制只有一個島嶼，計算一個即可
                    return dfs(grid, r, c);
                }
            }
        }
        return 0;
    }

    int dfs(int[][] grid, int r, int c) {
        // 從一個島嶼方格走向網格邊界，周長加 1
        if (!(0 <= r && r < grid.length && 0 <= c && c < grid[0].length)) {
            return 1;
        }

        // 從一個島嶼方格走向水域方格，周長加 1
        if (grid[r][c] == 0) {
            return 1;
        }

        // 已遍歷過（值為2）的島嶼在這裡會直接返回，不會重復遍歷
        if (grid[r][c] != 1) {
            return 0;
        }

        // 將方格標記為"已遍歷"
        grid[r][c] = 2;

        return dfs(grid, r - 1, c)
                + dfs(grid, r + 1, c)
                + dfs(grid, r, c - 1)
                + dfs(grid, r, c + 1);
    }


}
