package EndlessCheng.GenreMenu.Grid.Comprehensive;

public class IsPossibleToCutPath {

    // https://leetcode.cn/problems/disconnect-path-in-a-binary-matrix-by-at-most-one-flip/solutions/2093243/zhuan-huan-cheng-qiu-lun-kuo-shi-fou-xia-io8x/
    private int[][] g;
    private int m, n;

    public boolean isPossibleToCutPath(int[][] grid) {
        g = grid;
        m = g.length;
        n = g[0].length;
        return !dfs(0, 0) || !dfs(0, 0);
    }

    private boolean dfs(int x, int y) { // 返回能否到達終點
        if (x == m - 1 && y == n - 1) return true;
        g[x][y] = 0; // 直接修改
        return x < m - 1 && g[x + 1][y] > 0 && dfs(x + 1, y) ||
                y < n - 1 && g[x][y + 1] > 0 && dfs(x, y + 1);
    }


}
