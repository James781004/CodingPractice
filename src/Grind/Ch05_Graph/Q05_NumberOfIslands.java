package Grind.Ch05_Graph;

import java.util.LinkedList;
import java.util.Queue;

public class Q05_NumberOfIslands {
    // https://leetcode.cn/problems/number-of-islands/solutions/16884/number-of-islands-shen-du-you-xian-bian-li-dfs-or-/
    // Flood Fill
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j) {
        // 當前座標越界或者當前座標是水域
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') return;
        grid[i][j] = '0'; // 遍歷過座標直接淹沒

        // 接下來找上下左右
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
    }


    // BFS 也可以 Flood Fill
    private void bfs(char[][] grid, int i, int j) {
        Queue<int[]> list = new LinkedList<>();
        list.add(new int[]{i, j});
        while (!list.isEmpty()) {
            int[] cur = list.remove();
            i = cur[0];
            j = cur[1];

            // 當前座標越界或者當前座標是水域
            if (0 <= i && i < grid.length && 0 <= j && j < grid[0].length && grid[i][j] == '1') {
                grid[i][j] = '0';  // 遍歷過座標直接淹沒

                // 接下來找上下左右
                list.add(new int[]{i + 1, j});
                list.add(new int[]{i - 1, j});
                list.add(new int[]{i, j + 1});
                list.add(new int[]{i, j - 1});
            }
        }
    }
}
