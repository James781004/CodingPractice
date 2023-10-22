package Grind.Ch16_Binary;

public class Q03_CountingBits {
    // https://leetcode.cn/problems/counting-bits/solutions/183117/zhe-ying-gai-shi-dao-easyti-ba-wei-yun-suan-ji-chu/
    public int[] countBits(int num) {
        if (num == 0) return new int[]{0};
        int[] dp = new int[num + 1]; // 設 dp[i]為 i 的二進制形式的 1 的個數
        dp[0] = 0;
        for (int i = 0; i <= num; i++) {
            // i 是奇數時，dp[i]=dp[i-1]+1,
            // 因為 i是在 i-1 的二進制數上加了個 1
            // i 是偶數時，dp[i]=dp[i/2],
            // 因為i就是把 i/2 往左移（是數左移，末尾 補0）得到的，
            // 所以 1 的個數沒變
            if (i % 2 == 0) dp[i] = dp[i / 2];
            else dp[i] = dp[i - 1] + 1;
        }
        return dp;
    }
}
