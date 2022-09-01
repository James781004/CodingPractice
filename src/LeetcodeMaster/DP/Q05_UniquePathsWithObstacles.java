package LeetcodeMaster.DP;

public class Q05_UniquePathsWithObstacles {
//    63. 不同路徑 II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0063.%E4%B8%8D%E5%90%8C%E8%B7%AF%E5%BE%84II.md
//
//    一個機器人位於一個 m x n 網格的左上角 （起始點在下圖中標記為“Start” ）。
//
//    機器人每次只能向下或者向右移動一步。機器人試圖達到網格的右下角（在下圖中標記為“Finish”）。
//
//    現在考慮網格中有障礙物。那麼從左上角到右下角將會有多少條不同的路徑？
//
//
//
//    網格中的障礙物和空位置分別用 1 和 0 來表示。
//
//    示例 1：
//
//
//
//    輸入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
//    輸出：2 解釋：
//            3x3 網格的正中間有一個障礙物。
//    從左上角到右下角一共有 2 條不同的路徑：
//    向右 -> 向右 -> 向下 -> 向下
//    向下 -> 向下 -> 向右 -> 向右
//    示例 2：
//
//
//
//    輸入：obstacleGrid = [[0,1],[0,0]]
//    輸出：1
//    提示：
//
//    m == obstacleGrid.length
//    n == obstacleGrid[i].length
//1 <= m, n <= 100
//    obstacleGrid[i][j] 為 0 或 1

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length, m = obstacleGrid[0].length;
        int[][] dp = new int[n][m];

        // 初始化
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[0][i] == 1) continue;  // 一旦遇到障礙，後續都到不了
            dp[0][i] = 1;
        }

        // 初始化
        for (int i = 0; i < n; i++) {
            if (obstacleGrid[i][0] == 1) continue;  // 一旦遇到障礙，後續都到不了
            dp[i][0] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (obstacleGrid[i][j] == 1) continue;  // 一旦遇到障礙，後續都到不了
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[n - 1][m - 1];
    }

}
