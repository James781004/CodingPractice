package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.ArrayDeque;
import java.util.Queue;

public class ContainsCycle {

    // https://leetcode.cn/problems/detect-cycles-in-2d-grid/solutions/2421933/1559-er-wei-wang-ge-tu-zhong-tan-ce-huan-6ka2/
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int m, n;
    char[][] grid;
    boolean[][] visited;

    public boolean containsCycle(char[][] grid) {
        this.m = grid.length;
        this.n = grid[0].length;
        this.grid = grid;
        this.visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j]) {
                    continue;
                }
                if (dfs(i, j, -1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(int currRow, int currCol, int prevRow, int prevCol) {
        visited[currRow][currCol] = true; // 標記當前格子為已訪問
        for (int[] dir : dirs) {  // 遍歷四個方向
            int nextRow = currRow + dir[0], nextCol = currCol + dir[1];

            // 檢查下一個格子是否在網格內，且不是上一個格子，且值與當前格子相同
            if (nextRow >= 0 && nextRow < m && nextCol >= 0 && nextCol < n && (nextRow != prevRow || nextCol != prevCol) && grid[nextRow][nextCol] == grid[currRow][currCol]) {
                if (visited[nextRow][nextCol]) {
                    return true; // 如果存在一個具有相同值的相鄰單元格已訪問，則找到一個相同值形成的環
                } else {
                    if (dfs(nextRow, nextCol, currRow, currCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean containsCycleBFS(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j]) {
                    continue;
                }
                visited[i][j] = true;
                Queue<int[]> queue = new ArrayDeque<>();
                queue.offer(new int[]{i, j, -1, -1});
                while (!queue.isEmpty()) {
                    int[] cell = queue.poll();
                    int currRow = cell[0], currCol = cell[1], prevRow = cell[2], prevCol = cell[3];
                    for (int[] dir : dirs) {
                        int nextRow = currRow + dir[0], nextCol = currCol + dir[1];
                        if (nextRow >= 0 && nextRow < m && nextCol >= 0 && nextCol < n && (nextRow != prevRow || nextCol != prevCol) && grid[nextRow][nextCol] == grid[currRow][currCol]) {
                            if (visited[nextRow][nextCol]) {
                                return true;
                            } else {
                                visited[nextRow][nextCol] = true;
                                queue.offer(new int[]{nextRow, nextCol, currRow, currCol});
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


}
