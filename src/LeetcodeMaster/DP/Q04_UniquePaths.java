package LeetcodeMaster.DP;

public class Q04_UniquePaths {
//    62.不同路徑
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0062.%E4%B8%8D%E5%90%8C%E8%B7%AF%E5%BE%84.md
//
//    一個機器人位於一個 m x n 網格的左上角 （起始點在下圖中標記為 “Start” ）。
//
//    機器人每次只能向下或者向右移動一步。機器人試圖達到網格的右下角（在下圖中標記為 “Finish” ）。
//
//    問總共有多少條不同的路徑？
//
//    示例 1：
//
//
//
//    輸入：m = 3, n = 7
//    輸出：28
//    示例 2：
//
//    輸入：m = 2, n = 3
//    輸出：3
//    解釋： 從左上角開始，總共有 3 條路徑可以到達右下角。
//
//    向右 -> 向右 -> 向下
//    向右 -> 向下 -> 向右
//    向下 -> 向右 -> 向右
//    示例 3：
//
//    輸入：m = 7, n = 3
//    輸出：28
//    示例 4：
//
//    輸入：m = 3, n = 3
//    輸出：6
//    提示：
//
//            1 <= m, n <= 100
//    題目數據保證答案小於等於 2 * 10^9

    /**
     * 1. 確定dp數組下標含義 dp[i][j] 到每一個坐標可能的路徑種類
     * 2. 遞推公式 dp[i][j] = dp[i-1][j] dp[i][j-1]
     * 3. 初始化 dp[i][0]=1 dp[0][i]=1 初始化橫豎就可
     * 4. 遍歷順序 一行一行遍歷
     * 5. 推導結果 。。。。。。。。
     *
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // 初始化
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        // 初始化
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

}
