package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxMoves {

    // https://leetcode.cn/problems/maximum-number-of-moves-in-a-grid/solutions/2269244/cong-ji-yi-hua-sou-suo-dao-di-tui-by-end-pgq3/
    private int ans;

    public int maxMoves(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            dfs(i, 0, grid); // 從第一列的任一單元格出發
        }
        return ans;
    }

    private void dfs(int i, int j, int[][] grid) {
        ans = Math.max(ans, j);
        if (ans == grid[0].length - 1) { // ans 已達到最大值
            return;
        }
        // 向右上/右/右下走一步
        for (int k = Math.max(i - 1, 0); k < Math.min(i + 2, grid.length); k++) {
            if (grid[k][j + 1] > grid[i][j]) {
                dfs(k, j + 1, grid);
            }
        }
        grid[i][j] = 0;
    }


    public int maxMovesBFS(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] vis = new int[m];
        Arrays.fill(vis, -1);
        List<Integer> q = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            q.add(i);
        }
        for (int j = 0; j < n - 1; j++) {
            List<Integer> nxt = new ArrayList<>();
            for (int i : q) {
                for (int k = Math.max(i - 1, 0); k < Math.min(i + 2, m); k++) {
                    if (vis[k] != j && grid[k][j + 1] > grid[i][j]) {
                        vis[k] = j; // 第 k 行目前最右訪問到了 j
                        nxt.add(k);
                    }
                }
            }
            if (nxt.isEmpty()) { // 無法再往右走了
                return j;
            }
            q = nxt;
        }
        return n - 1;
    }


}
