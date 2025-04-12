package EndlessCheng.GenreMenu.Grid.DFS;

public class ColorBorder {

    // https://leetcode.cn/problems/coloring-a-border/solutions/1143080/pythonjavajavascriptgo-dfs-ji-lu-zou-guo-l7e5/
    boolean[][] visited;
    int init;

    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        visited = new boolean[grid.length][grid[0].length]; // 聲明訪問數組
        init = grid[row][col]; // 記錄初始的顏色
        dfs(grid, row, col, color);
        return grid;
    }

    // 該題是島嶼問題的變形,核心點在於給連通分量的邊界涂色。
    // 如何定義連通分量邊界？
    // 顏色與grid[row][col]相同，與連通分量連接，這兩個是必須滿足的條件
    // 當滿足上述條件的點出現在最外一圈時，必為邊界。
    // 當滿足上述條件的點出現在內圈時，如果它四周有一個點與grid[row][col]顏色不同，為邊界。
    private void dfs(int[][] grid, int i, int j, int color) {
        // 判空
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || visited[i][j] || grid[i][j] != init) return;

        // 剩下的都是未被訪問過,而且值等於Init的點
        // grid[i][j]是邊界
        if (i == 0 || j == 0 || i == grid.length - 1 || j == grid[0].length - 1) grid[i][j] = color;
            // 只要grid[i][j]的四周有一個不是聯通分量的點,就代表它是邊界
        else if (isTrue(grid, i - 1, j) || isTrue(grid, i + 1, j) || isTrue(grid, i, j - 1) || isTrue(grid, i, j + 1))
            grid[i][j] = color;
        visited[i][j] = true; // 表示訪問過該點
        dfs(grid, i - 1, j, color);
        dfs(grid, i, j - 1, color);
        dfs(grid, i + 1, j, color);
        dfs(grid, i, j + 1, color);
    }

    private boolean isTrue(int[][] grid, int i, int j) {
        // grid[i][j]超出范圍
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) return false;
        // grid[i][j]未被訪問過,而且顏色與init不同
        if (grid[i][j] != init && !visited[i][j]) return true;
        // grid[i][j]被訪問過,或者顏色與init相同
        return false;
    }

}
