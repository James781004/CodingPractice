package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacificAtlantic {

    // https://leetcode.cn/problems/pacific-atlantic-water-flow/solutions/1688937/by-carlsun-2-h5ww/
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        int m = matrix.length;
        int n = matrix[0].length;

        // 記錄從太平洋邊出發，可以遍歷的節點
        int[][] pacific = new int[m][n];

        // 記錄從大西洋出發，可以遍歷的節點
        int[][] atlantic = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 從邊界開始處理
                if (i == 0 || j == 0) {
                    dfs(matrix, pacific, i, j, matrix[i][j]);
                }
                if (i == m - 1 || j == n - 1) {
                    dfs(matrix, atlantic, i, j, matrix[i][j]);
                }
            }
        }

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果這個節點，從太平洋和大西洋出發都遍歷過，就是結果
                if (pacific[i][j] == 1 && atlantic[i][j] == 1) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }

        return res;
    }

    // 從低向高遍歷
    private void dfs(int[][] matrix, int[][] aux, int i, int j, int pre) {
        if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length || aux[i][j] == 1 || matrix[i][j] < pre) {
            return;
        }

        // 遍歷過的點設為1
        aux[i][j] = 1;

        dfs(matrix, aux, i + 1, j, matrix[i][j]);
        dfs(matrix, aux, i - 1, j, matrix[i][j]);
        dfs(matrix, aux, i, j + 1, matrix[i][j]);
        dfs(matrix, aux, i, j - 1, matrix[i][j]);
    }

}
