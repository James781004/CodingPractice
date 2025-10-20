package EndlessCheng.GenreMenu.DP.Grid.Basic;

public class MinFallingPathSumII {

    // https://leetcode.cn/problems/minimum-falling-path-sum-ii/solutions/3660610/1289-xia-jiang-lu-jing-zui-xiao-he-iidpx-w97w/

    /**
     * 計算矩陣中下降路徑的最小和（不能垂直下降）
     * 使用動態規劃方法解決：
     * 1. dp[i][j]表示到達(i,j)位置的最小路徑和
     * 2. 初始化：第一行直接使用網格值
     * 3. 狀態轉移：考慮從上一行非同一列的所有可能路徑
     * 4. 最後返回最後一行中的最小值
     *
     * @param grid 二維網格數組，不能為null且必須為方陣
     * @return 最小下降路徑和
     * @throws NullPointerException     如果輸入矩陣為null
     * @throws IllegalArgumentException 如果矩陣為空或不是方陣
     */
    public static int minFallingPathSum(int[][] grid) {
        // 獲取矩陣尺寸
        int length = grid.length;

        // 初始化DP數組
        int[][] dp = new int[length][length];
        // 覆制第一行作為初始值
        System.arraycopy(grid[0], 0, dp[0], 0, length);

        // 從第二行開始動態規劃
        for (int i = 1; i < length; i++) {
            // 遍歷每一列
            for (int j = 0; j < length; j++) {
                // 記錄上一行最小路徑和
                int num = Integer.MAX_VALUE;
                // 遍歷上一行所有非同一列的路徑
                for (int k = 0; k < length; k++) {
                    // 不是同一列，就比較最小路徑和
                    if (k != j) {
                        num = Math.min(num, dp[i - 1][k]);
                    }
                }
                // 當前格子值 = 上一行最小路徑和 + 當前格子值
                dp[i][j] = num + grid[i][j];
            }
        }

        // 找出最後一行中的最小值
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            result = Math.min(result, dp[length - 1][i]);
        }
        return result;
    }


    public static int minFallingPathSum2(int[][] grid) {
        // 獲取矩陣尺寸
        int length = grid.length;

        // 上一行的最小值
        int preMinValue = 0;
        // 上一行最小值的列索引
        int preMinIndex = 0;
        // 上一行的次小值
        int preSecondMinValue = 0;

        // 遍歷每一行
        for (int[] row : grid) {
            // 初始化當前行的狀態
            // 當前行的最小值
            int curMinValue = Integer.MAX_VALUE;
            // 當前行最小值的列索引
            int curSecondMinValue = Integer.MAX_VALUE;
            // 當前行的次小值
            int curMinIndex = 0;

            // 遍歷每一列
            for (int j = 0; j < length; j++) {

                // 計算當前格子的路徑和：如果當前列與上一行最小值的列相同，則使用次小值，否則使用最小值
                int sum = (j == preMinIndex ? preSecondMinValue : preMinValue) + row[j];

                // 更新當前行的最小值和次小值
                if (curMinValue > sum) {
                    curSecondMinValue = curMinValue;
                    curMinValue = sum;
                    curMinIndex = j;
                } else if (curSecondMinValue > sum) {
                    curSecondMinValue = sum;

                }
            }

            // 更新上一行狀態為當前行狀態
            preMinValue = curMinValue;
            preSecondMinValue = curSecondMinValue;
            preMinIndex = curMinIndex;
        }

        // 返回最後計算的最小值
        return preMinValue;
    }
}
