package LeetcodeMaster.DP;

public class Q06_IntegerBreak {
//    343. 整數拆分
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0343.%E6%95%B4%E6%95%B0%E6%8B%86%E5%88%86.md
//
//    給定一個正整數 n，將其拆分為至少兩個正整數的和，並使這些整數的乘積最大化。 返回你可以獲得的最大乘積。
//
//    示例 1:
//
//    輸入: 2
//    輸出: 1
//    解釋: 2 = 1 + 1, 1 × 1 = 1。
//    示例 2:
//
//    輸入: 10
//    輸出: 36
//    解釋: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
//    說明: 你可以假設 n 不小於 2 且不大於 58。


    public int integerBreak(int n) {
        int[] dp = new int[n + 1]; // dp[i] 為正整數 i 拆分後的結果的最大乘積
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j <= i - j; j++) {
                // 這里的 j 其實最大值為 i-j,再大只不過是重覆而已，
                // 並且，在本題中，我們分析 dp[0], dp[1]都是無意義的，
                // j 最大到 i-j,就不會用到 dp[0]與dp[1]
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
                // j * (i - j) 是單純的把整數 i 拆分為兩個數 也就是 j 和 i-j，再相乘
                // 而j * dp[i - j]是將 i-j 再度拆分成兩個以及兩個以上的個數，再相乘。
            }
        }
        return dp[n];
    }

    // 貪心，數學解
    public int integerBreakGreedy(int n) {
        if (n == 2) return 1;
        if (n == 3) return 2;
        if (n == 4) return 4;
        int result = 1;
        while (n > 4) {
            result *= 3;
            n -= 3;
        }
        result *= n;
        return result;
    }
}
