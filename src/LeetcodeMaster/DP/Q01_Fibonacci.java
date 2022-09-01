package LeetcodeMaster.DP;

public class Q01_Fibonacci {
    //    509. 斐波那契數
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0509.%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0.md
//
//    斐波那契數，通常用 F(n) 表示，形成的序列稱為 斐波那契數列 。該數列由 0 和 1 開始，後面的每一項數字都是前面兩項數字的和。
//    也就是： F(0) = 0，F(1) = 1 F(n) = F(n - 1) + F(n - 2)，其中 n > 1 給你n ，請計算 F(n) 。
//
//    示例 1：
//
//    輸入：2
//    輸出：1
//    解釋：F(2) = F(1) + F(0) = 1 + 0 = 1
//    示例 2：
//
//    輸入：3
//    輸出：2
//    解釋：F(3) = F(2) + F(1) = 1 + 1 = 2
//    示例 3：
//
//    輸入：4
//    輸出：3
//    解釋：F(4) = F(3) + F(2) = 2 + 1 = 3
//    提示：
//
//            0 <= n <= 30
    public int fib(int n) {
        if (n < 2) return n;
        int a = 0, b = 1, c = 0;
        for (int i = 1; i < n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    public int fib2(int n) {
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int index = 2; index <= n; index++) {
            dp[index] = dp[index - 1] + dp[index - 2];
        }
        return dp[n];
    }
}
