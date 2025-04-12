package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayList;
import java.util.List;

public class OrangesRotting {

    // https://leetcode.cn/problems/rotting-oranges/solutions/2773461/duo-yuan-bfsfu-ti-dan-pythonjavacgojsrus-yfmh/
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 四方向

    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int fresh = 0;
        List<int[]> q = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    fresh++; // 統計新鮮橘子個數
                } else if (grid[i][j] == 2) {
                    q.add(new int[]{i, j}); // 一開始就腐爛的橘子
                }
            }
        }

        int ans = 0;
        while (fresh > 0 && !q.isEmpty()) {
            ans++; // 經過一分鐘
            List<int[]> tmp = q;
            q = new ArrayList<>();
            for (int[] pos : tmp) { // 已經腐爛的橘子
                for (int[] d : DIRECTIONS) { // 四方向
                    int i = pos[0] + d[0];
                    int j = pos[1] + d[1];
                    if (0 <= i && i < m && 0 <= j && j < n && grid[i][j] == 1) { // 新鮮橘子
                        fresh--;
                        grid[i][j] = 2; // 變成腐爛橘子
                        q.add(new int[]{i, j});
                    }
                }
            }
        }

        return fresh > 0 ? -1 : ans;
    }


}
