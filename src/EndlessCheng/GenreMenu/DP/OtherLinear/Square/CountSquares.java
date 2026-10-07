package EndlessCheng.GenreMenu.DP.OtherLinear.Square;

public class CountSquares {

    // https://leetcode.cn/problems/count-square-submatrices-with-all-ones/solutions/3751608/tu-jie-dong-tai-gui-hua-jian-ji-xie-fa-p-1kiy/
    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] f = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > 0) {
                    f[i + 1][j + 1] = Math.min(Math.min(f[i][j], f[i][j + 1]), f[i + 1][j]) + 1;
                    ans += f[i + 1][j + 1];
                }
            }
        }
        return ans;
    }


    public int countSquares2(int[][] matrix) {
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] > 0 && i > 0 && j > 0) {
                    row[j] += Math.min(Math.min(matrix[i - 1][j], matrix[i - 1][j - 1]), row[j - 1]);
                }
                ans += row[j];
            }
        }
        return ans;
    }

   
}
