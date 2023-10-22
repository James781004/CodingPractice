package Grind.Ch15_DP;

public class Q10_MaximalSquare {
    // https://leetcode.cn/problems/maximal-square/solutions/44586/li-jie-san-zhe-qu-zui-xiao-1-by-lzhlyle/
    public int maximalSquare(char[][] matrix) {
        // base condition
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) return 0;

        int height = matrix.length;
        int width = matrix[0].length;
        int maxSide = 0;

        // dp[i][j] 表示到達 (i, j) 位置所能組成的最大正方形的邊長
        // 而 (i, j) 為該正方形的右下點
        // 等同於：dp(i + 1, j + 1) 是以 matrix(i, j) 為右下角的正方形的最大邊長
        // 相當於已經預處理新增第一行、第一列均為0
        int[][] dp = new int[height + 1][width + 1];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // 當 (i, j) 位置為1，此時要看 dp[i-1][j-1], dp[i][j-1]，和 dp[i-1][j] 這三個位置，
                // 找其中最小的值，並加上1，就是 dp[i][j] 的當前值
                // 因為整個正方形不能有0存在，所以只能取交集，最後再用 dp[i][j] 的值來更新結果 res 的值
                if (matrix[row][col] == '1') {
                    dp[row + 1][col + 1] = Math.min(Math.min(dp[row + 1][col], dp[row][col + 1]), dp[row][col]) + 1;
                    maxSide = Math.max(maxSide, dp[row + 1][col + 1]);
                }
            }
        }

        return maxSide * maxSide;
    }


    // 含優化過程的代碼
    public int maximalSquare2(char[][] matrix) {
        // base condition
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) return 0;

        // int height = matrix.length;
        int width = matrix[0].length;
        int maxSide = 0;

        // 相當於已經預處理新增第一行、第一列均為0
        //  int[][] dp = new int[height + 1][width + 1];
        int[] dp = new int[width + 1];
        int northwest = 0; // 西北角、左上角

        //  for (int row = 0; row < height; row++) {
        for (char[] chars : matrix) {
            northwest = 0; // 遍歷每行時，還原回輔助的原值0
            for (int col = 0; col < width; col++) {
                int nextNorthwest = dp[col + 1];
                if (chars[col] == '1') {
                    // dp[row + 1][col + 1] = Math.min(Math.min(dp[row + 1][col], dp[row][col + 1]), dp[row][col]) + 1;
                    dp[col + 1] = Math.min(Math.min(dp[col], dp[col + 1]), northwest) + 1;

                    // maxSide = Math.max(maxSide, dp[row + 1][col + 1]);
                    maxSide = Math.max(maxSide, dp[col + 1]);
                } else {
                    dp[col + 1] = 0;
                }
                northwest = nextNorthwest;
            }
        }
        return maxSide * maxSide;
    }
}
