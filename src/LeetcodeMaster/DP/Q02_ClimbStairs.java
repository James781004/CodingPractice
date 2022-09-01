package LeetcodeMaster.DP;

public class Q02_ClimbStairs {
//    70. 爬樓梯
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0070.%E7%88%AC%E6%A5%BC%E6%A2%AF.md
//
//    假設你正在爬樓梯。需要 n 階你才能到達樓頂。
//
//    每次你可以爬 1 或 2 個台階。你有多少種不同的方法可以爬到樓頂呢？
//
//    注意：給定 n 是一個正整數。
//
//    示例 1：
//
//    輸入： 2
//    輸出： 2
//    解釋： 有兩種方法可以爬到樓頂。
//            1 階 + 1 階
//2 階
//    示例 2：
//
//    輸入： 3
//    輸出： 3
//    解釋： 有三種方法可以爬到樓頂。
//            1 階 + 1 階 + 1 階
//1 階 + 2 階
//2 階 + 1 階

    public int climbStairs(int n) {
        // 跟斐波那契數列一樣
        if (n <= 2) return n;
        int a = 1, b = 2, sum = 0;

        for (int i = 3; i <= n; i++) {
            sum = a + b;          // f(i - 1) + f(n - 2)
            a = b;                // 記錄上一輪的值
            b = sum;              // 向後步進1個數
        }
        return b;
    }


    // 常規方式
    public int climbStairs2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }


}
