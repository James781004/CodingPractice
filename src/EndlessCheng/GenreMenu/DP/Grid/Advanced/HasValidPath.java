package EndlessCheng.GenreMenu.DP.Grid.Advanced;

public class HasValidPath {

    // https://leetcode.cn/problems/check-if-there-is-a-valid-parentheses-string-path/solutions/1477302/tian-jia-zhuang-tai-hou-dfscpythonjavago-f287/
    private int m, n;
    private char[][] grid;
    private boolean[][][] vis;

    public boolean hasValidPath(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        if ((m + n) % 2 == 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }

        this.grid = grid;
        vis = new boolean[m][n][(m + n + 1) / 2];
        return dfs(0, 0, 0); // 起點
    }

    private boolean dfs(int x, int y, int c) {
        if (c > m - x + n - y - 1) { // 剪枝：即使後面都是 ')' 也不能將 c 減為 0
            return false;
        }
        if (x == m - 1 && y == n - 1) { // 終點
            return c == 1; // 終點一定是 ')'
        }
        if (vis[x][y][c]) {
            return false;
        }
        vis[x][y][c] = true;
        c += grid[x][y] == '(' ? 1 : -1;
        return c >= 0 && (x < m - 1 && dfs(x + 1, y, c) || y < n - 1 && dfs(x, y + 1, c)); // 往下或者往右
    }


}
