package EndlessCheng.GenreMenu.Graph.Tarjan;

public class MinDays {

    // https://leetcode.cn/problems/minimum-number-of-days-to-disconnect-island/solutions/1123998/java-tarjanqiu-ge-dian-yu-qiao-kao-lu-xi-dxe1/
    int index;
    // size是處理連通分量等於1時，陸地數目小於3時候的情況
    int size = 0;
    int[][] directions = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};

    public int minDays(int[][] grid) {
        // n為連通分量個數，當n大於1是可以直接結束
        int n = 0, row = grid.length, column = grid[0].length;
        boolean hasBridge = false;
        int[][] dfn = new int[row][column];
        int[][] low = new int[row][column];
        boolean[][] visited = new boolean[row][column];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j] && 1 == grid[i][j]) {
                    n++;
                    if (n > 1) {
                        break;
                    }
                    if (dfs(i, j, -1, -1, grid, dfn, low, visited)) {
                        hasBridge = true;
                    }
                }
            }
        }
        // 連通分量大於1，不需要操作
        if (n > 1) {
            return 0;
        }
        // 連通分量等於1時，如果陸地數小於3，操作次數就等於陸地數（連通分量等於0，size肯定也是0）
        if (size < 3) {
            return size;
        }
        // 連通分量等於1時，如果存在橋，只需要一次操作
        if (hasBridge) {
            return 1;
        }
        // 連通分量等於1時，並且不存在橋，就需要2次操作
        return 2;
    }

    boolean dfs(int x, int y, int px, int py,
                int[][] grid, int[][] dfn, int[][] low, boolean[][] visited) {
        visited[x][y] = true;
        size++;
        boolean hasBridge = false;
        dfn[x][y] = low[x][y] = ++index;
        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1];
            if (!(nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length)
                    || 0 == grid[nx][ny] || (nx == px && ny == py)) {
                continue;
            }
            if (!visited[nx][ny]) {
                if (dfs(nx, ny, x, y, grid, dfn, low, visited)) {
                    hasBridge = true;
                }
                low[x][y] = Math.min(low[x][y], low[nx][ny]);
                if (!hasBridge && low[nx][ny] > dfn[x][y]) {
                    hasBridge = true;
                }
            } else {
                low[x][y] = Math.min(low[x][y], dfn[nx][ny]);
            }
        }
        return hasBridge;
    }

}
