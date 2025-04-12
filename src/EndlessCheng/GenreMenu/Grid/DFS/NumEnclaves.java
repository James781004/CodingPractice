package EndlessCheng.GenreMenu.Grid.DFS;

public class NumEnclaves {

    // https://leetcode.cn/problems/number-of-enclaves/solutions/3010601/java-dfsquan-ju-bian-liang-by-eagle_like-bf2u/
    public static final int[] dx = {1, -1, 0, 0};
    public static final int[] dy = {0, 0, 1, -1};
    public static boolean close;

    public int numEnclaves(int[][] grid) {
        int ans = 0;
        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                close = true;    //close要隨時的更新
                if (grid[i][j] == 1) {
                    num = dfs(grid, i, j);
                }
                if (close) {    //判斷是否時有邊緣陸地
                    ans += num;
                }
                num = 0;
            }
        }
        return ans;
    }

    public static int dfs(int[][] grid, int x, int y) {
        int n = grid.length, m = grid[0].length;
        if (x == 0 || x == n - 1 || y == 0 || y == m - 1) {  //位於邊界上並且等於1那麽就將close關閉
            if (x >= 0 && x <= n - 1 && y >= 0 && y <= m - 1 && grid[x][y] == 1) {
                close = false;
            }
        }
        if (x < 0 || y < 0 || x >= n || y >= m || grid[x][y] == 0) {
            return 0;
        }
        int num = 1;
        grid[x][y] = 0;  //將為1的陸地改為海洋，防止再一次訪問
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i], newY = y + dy[i];
            num += dfs(grid, newX, newY);
        }
        return num;
    }

}
