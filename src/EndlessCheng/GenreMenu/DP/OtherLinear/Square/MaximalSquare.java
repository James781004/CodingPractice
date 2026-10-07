package EndlessCheng.GenreMenu.DP.OtherLinear.Square;

public class MaximalSquare {

    // https://leetcode.cn/problems/maximal-square/solutions/3704858/he-85-ti-yi-yang-de-zuo-fa-pythonjavaccg-az54/
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] f = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    f[i + 1][j + 1] = Math.min(Math.min(f[i][j], f[i][j + 1]), f[i + 1][j]) + 1;
                    ans = Math.max(ans, f[i + 1][j + 1]);
                }
            }
        }
        return ans * ans;
    }


}
