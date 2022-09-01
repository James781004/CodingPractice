package LeetcodeMaster.DP;

public class Q34_UncrossedLines {
//    1035.不相交的線
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1035.%E4%B8%8D%E7%9B%B8%E4%BA%A4%E7%9A%84%E7%BA%BF.md
//
//    我們在兩條獨立的水平線上按給定的順序寫下 A 和 B 中的整數。
//
//    現在，我們可以繪制一些連接兩個數字 A[i] 和 B[j] 的直線，只要 A[i] == B[j]，且我們繪制的直線不與任何其他連線（非水平線）相交。
//
//    以這種方法繪制線條，並返回我們可以繪制的最大連線數。


    // 其實也就是說A和B的最長公共子序列是[1,4]，長度為2。
    // 這個公共子序列指的是相對順序不變（即數字4在字符串A中數字1的後面，那麽數字4也應該在字符串B數字1的後面）
    // 這麽分析完之後，大家可以發現：本題說是求繪制的最大連線數，其實就是求兩個字符串的最長公共子序列的長度
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[A.length][B.length];
    }
}
