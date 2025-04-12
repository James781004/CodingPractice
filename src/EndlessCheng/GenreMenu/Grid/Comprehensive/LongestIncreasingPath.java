package EndlessCheng.GenreMenu.Grid.Comprehensive;

public class LongestIncreasingPath {

    // https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/solutions/944399/tong-ge-lai-shua-ti-la-yi-ti-si-jie-bfs-agawl/
    // 上下左右四個方向
    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int longestIncreasingPath(int[][] matrix) {
        // 從每一個點出發，往下深搜，看它最遠能到哪
        int m = matrix.length;
        int n = matrix[0].length;

        // 記憶化
        int[][] memo = new int[m][n];

        // 每個點都要作為起始點遍歷一下
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 已經遍歷過的就不用遍歷了
                if (memo[i][j] == 0) {
                    ans = Math.max(ans, dfs(matrix, m, n, i, j, memo));
                }
                // 這裡為什麼不用再比較一次 ans 和 memo[i][j]呢？
                // 因為遍歷前面節點的時候已經把後面的節點遍歷了
                // 說明後面的節點肯定比前面的節點的最長路徑短
                // 所以，不用多判斷一次了
            }
        }

        return ans;
    }

    private int dfs(int[][] matrix, int m, int n, int i, int j, int[][] memo) {
        // 已經遍歷過，直接返回
        if (memo[i][j] != 0) {
            return memo[i][j];
        }

        // 否則，看四個方向是否有滿足條件的節點去擴散
        // 每個節點的初始路徑為1
        int ans = 1;
        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextJ >= 0 && nextI < m && nextJ < n && matrix[nextI][nextJ] > matrix[i][j]) {
                ans = Math.max(ans, dfs(matrix, m, n, nextI, nextJ, memo) + 1);
            }
        }

        // 記錄到緩存中
        memo[i][j] = ans;
        return ans;
    }


}
