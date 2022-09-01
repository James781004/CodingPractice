package LeetcodeMaster.DP;

public class Q18_NumSquares {
//    279.完全平方數
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0279.%E5%AE%8C%E5%85%A8%E5%B9%B3%E6%96%B9%E6%95%B0.md
//
//    給定正整數 n，找到若幹個完全平方數（比如 1, 4, 9, 16, ...）使得它們的和等於 n。你需要讓組成和的完全平方數的個數最少。
//
//    給你一個整數 n ，返回和為 n 的完全平方數的 最少數量 。
//
//    完全平方數 是一個整數，其值等於另一個整數的平方；換句話說，其值等於一個整數自乘的積。例如，1、4、9 和 16 都是完全平方數，而 3 和 11 不是。
//
//    示例 1： 輸入：n = 12 輸出：3 解釋：12 = 4 + 4 + 4
//
//    示例 2： 輸入：n = 13 輸出：2 解釋：13 = 4 + 9
//
//    提示：
//
//            1 <= n <= 10^4


    // 版本一，先遍歷物品, 再遍歷背包
    public int numSquares(int n) {
        int max = Integer.MAX_VALUE;
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = max; // 初始化
        }

        dp[0] = 0; // 當和為0時，組合的個數為0

        for (int i = 1; i * i <= n; i++) { // 遍歷物品
            for (int j = i * i; j <= n; j++) { // 遍歷背包
                if (dp[j - i * i] != max) {
                    dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
                }
            }
        }

        return dp[n];
    }


    // 版本二， 先遍歷背包, 再遍歷物品
    public int numSquares2(int n) {
        int max = Integer.MAX_VALUE;
        int[] dp = new int[n + 1];

        for (int j = 0; j <= n; j++) {
            dp[j] = max; // 初始化
        }

        dp[0] = 0; // 當和為0時，組合的個數為0

        for (int j = 1; j <= n; j++) { // 遍歷背包
            for (int i = 1; i * i <= j; i++) { // 遍歷物品
                dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
            }
        }
        return dp[n];
    }
}
