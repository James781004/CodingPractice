package EndlessCheng.GenreMenu.DP.StateCompression.ContourLine;

import java.util.HashMap;
import java.util.Map;

public class UniquePathsIII {

    // https://leetcode.cn/problems/unique-paths-iii/solutions/2372252/liang-chong-fang-fa-hui-su-zhuang-tai-ya-26py/
    private Map<Integer, Integer> memo = new HashMap<>();

    public int uniquePathsIII(int[][] grid) {
        int n = grid[0].length, vis = 0, sx = -1, sy = -1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] < 0) // 把障礙方格算上
                    vis |= 1 << (i * n + j);
                else if (grid[i][j] == 1) { // 起點
                    sx = i;
                    sy = j;
                }
            }
        }
        return dfs(grid, sx, sy, vis);
    }

    private int dfs(int[][] grid, int x, int y, int vis) {
        int m = grid.length, n = grid[0].length;
        int p = x * n + y;
        if (x < 0 || x >= m || y < 0 || y >= n || (vis >> p & 1) > 0)
            return 0; // 不合法
        vis |= 1 << p; // 標記訪問過 (x,y)，因為題目要求「不能重復通過同一個方格」
        if (grid[x][y] == 2) // 到達終點
            return vis == (1 << m * n) - 1 ? 1 : 0; // 必須訪問所有的無障礙方格
        int key = (p << m * n) | vis; // 把參數壓縮成一個整數（左移 m*n 是因為 vis 至多有 m*n 個比特）
        if (memo.containsKey(key)) return memo.get(key); // 之前算過
        int ans = dfs(grid, x - 1, y, vis) + dfs(grid, x, y - 1, vis) +
                dfs(grid, x + 1, y, vis) + dfs(grid, x, y + 1, vis);
        memo.put(key, ans);
        return ans;
    }


}
