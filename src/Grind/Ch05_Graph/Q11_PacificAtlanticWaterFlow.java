package Grind.Ch05_Graph;

import java.util.*;

public class Q11_PacificAtlanticWaterFlow {
    // https://leetcode.cn/problems/pacific-atlantic-water-flow/solutions/46196/ni-liu-dfs-yu-bfs-by-fibonacciwh/
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        int m = matrix.length;
        int n = matrix[0].length;

        int[][] pacific = new int[m][n];
        int[][] atlantic = new int[m][n];

        // 從海洋邊界開始
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
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
                if (pacific[i][j] == 1 && atlantic[i][j] == 1) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }

        return res;
    }

    private void dfs(int[][] matrix, int[][] aux, int i, int j, int pre) {
        //判斷邊界
        if (i < 0 || j < 0 || i > matrix.length - 1 || j > matrix[0].length - 1
                //已經流到過了
                || aux[i][j] == 1
                //不能流動
                || matrix[i][j] < pre) {
            return;
        }

        aux[i][j] = 1;

        dfs(matrix, aux, i - 1, j, matrix[i][j]);
        dfs(matrix, aux, i + 1, j, matrix[i][j]);
        dfs(matrix, aux, i, j - 1, matrix[i][j]);
        dfs(matrix, aux, i, j + 1, matrix[i][j]);
    }

    public List<List<Integer>> pacificAtlanticBFS(int[][] matrix) {

        if (matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        int m = matrix.length;
        int n = matrix[0].length;

        Queue<int[]> pacificQueue = new LinkedList<>();
        Queue<int[]> atlanticQueue = new LinkedList<>();

        int[][] pacificAux = new int[m][n];
        int[][] atlanticAux = new int[m][n];

        //從海洋邊界開始
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    pacificQueue.add(new int[]{i, j});
                }
                if (i == m - 1 || j == n - 1) {
                    atlanticQueue.add(new int[]{i, j});
                }
            }
        }

        bfs(matrix, pacificAux, pacificQueue);
        bfs(matrix, atlanticAux, atlanticQueue);

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacificAux[i][j] == 1 && atlanticAux[i][j] == 1) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }

        return res;
    }

    private void bfs(int[][] matrix, int[][] aux, Queue<int[]> queue) {
        while (!queue.isEmpty()) {
            int[] array = queue.remove();
            int i = array[0];
            int j = array[1];
            // 流到的區域就置為1
            aux[i][j] = 1;
            if (i - 1 >= 0 && matrix[i][j] <= matrix[i - 1][j] && aux[i - 1][j] != 1) {
                queue.add(new int[]{i - 1, j});
            }
            if (i + 1 < matrix.length && matrix[i][j] <= matrix[i + 1][j] && aux[i + 1][j] != 1) {
                queue.add(new int[]{i + 1, j});
            }
            if (j - 1 >= 0 && matrix[i][j] <= matrix[i][j - 1] && aux[i][j - 1] != 1) {
                queue.add(new int[]{i, j - 1});
            }
            if (j + 1 < matrix[0].length && matrix[i][j] <= matrix[i][j + 1] && aux[i][j + 1] != 1) {
                queue.add(new int[]{i, j + 1});
            }
        }
    }
}
