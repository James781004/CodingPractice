package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

import java.util.LinkedList;
import java.util.Queue;

public class SwimInWater {

    // https://leetcode.cn/problems/swim-in-rising-water/solutions/2986487/er-fen-da-an-zhao-zui-xiao-zhi-bfs-pytho-wzct/
    public int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int l = grid[0][0];
        int r = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                r = Math.max(r, grid[i][j]);
            }
        }
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (check(grid, mid)) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    // 检查在最大值为k的情况下，能否从左上角游到右下角
    public boolean check(int[][] grid, int x) {
        int n = grid.length;
        boolean[][] vis = new boolean[n][n];
        vis[0][0] = true;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = pos[0] + dirs[i][0];
                int ny = pos[1] + dirs[i][1];
                if (0 <= nx && nx < n && 0 <= ny && ny < n && !vis[nx][ny] && grid[nx][ny] <= x) {
                    vis[nx][ny] = true;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
        return vis[n - 1][n - 1];
    }


}
