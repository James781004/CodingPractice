package FuckingAlgorithm.DP;

public class Q18_FourKeysKeyboard {
//    https://github.com/fedono/leetcode/blob/master/solutions/651-%E5%9B%9B%E9%94%AE%E9%94%AE%E7%9B%98.md
//    https://labuladong.github.io/algo/3/28/94/


    public int maxA(int N) {
        int[] dp = new int[N + 1]; // // 定義：dp[i] 表示 i 次操作後最多能顯示多少個 A
        dp[0] = 0;
        for (int i = 1; i <= N; i++) {
            // 按 A 鍵
            dp[i] = dp[i - 1] + 1;
            for (int j = 2; j < i; j++) {
                // dp[i] = max( 這次按 A 鍵, 這次按 C-V )
                // 最優的操作序列一定是 C-A C-C 接著若干 C-V，所以我們用一個變量 j 作為若干 C-V 的起點。
                // 那麼 j 之前的 2 個操作就應該是 C-A C-C 了
                // 全選 & 復制 dp[j-2]，連續粘貼 i - j 次
                // 屏幕上共 dp[j - 2] * (i - j + 1) 個 A
                dp[i] = Math.max(dp[i], dp[j - 2] * (i - j + 1));
            }
        }
        // N 次按鍵之後最多有幾個 A？
        return dp[N];
    }
}
