package Grind.Ch05_Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Q02_01Matrix {
    // https://leetcode.cn/problems/01-matrix/solutions/203486/2chong-bfs-xiang-jie-dp-bi-xu-miao-dong-by-sweetie/
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        // 記錄答案的結果數組
        int[][] res = new int[m][n];
        // 初始化全部填充特殊值 -1，代表未計算，
        // 待會可以用來判斷坐標是否已經計算過，避免重復遍歷
        for (int[] row : res) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> q = new LinkedList<>();
        // 初始化隊列，把那些值為 0 的坐標放到隊列裡
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    q.offer(new int[]{i, j});
                    res[i][j] = 0;
                }
            }
        }
        // 執行 BFS 算法框架，從值為 0 的坐標開始向四周擴散
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            // 向四周擴散
            for (int i = 0; i < 4; i++) {
                int nextX = x + dirs[i][0];
                int nextY = y + dirs[i][1];
                // 確保相鄰的這個坐標沒有越界且之前未被計算過
                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n
                        && res[nextX][nextY] == -1) {
                    q.offer(new int[]{nextX, nextY});
                    // 從 mat[x][y] 走到 mat[nextX][nextY] 需要一步
                    res[nextX][nextY] = res[x][y] + 1;
                }
            }
        }

        return res;
    }


    public int[][] updateMatrixDP(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n]; // 表示該位置距離最近的 0 的距離
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) { // 題目裡指出二維數組的最大規格為 m*n = 10000，所以用 10000
                dp[i][j] = matrix[i][j] == 0 ? 0 : 10000;
            }
        }

        // 從左上角開始
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i - 1 >= 0) { // 從左方遞推
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                }
                if (j - 1 >= 0) { // 從上方遞推
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                }
            }
        }

        // 從右下角開始
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i + 1 < m) { // 從右方遞推
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j] + 1);
                }
                if (j + 1 < n) {  // 從下方遞推
                    dp[i][j] = Math.min(dp[i][j], dp[i][j + 1] + 1);
                }
            }
        }
        return dp;
    }

}
