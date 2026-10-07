package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class NumberOfWaysKSteps {

    // https://leetcode.cn/problems/number-of-ways-to-reach-a-position-after-exactly-k-steps/solutions/1799464/by-endlesscheng-6yvy/
    public int numberOfWays(int startPos, int endPos, int k) {
        int mod = 1000000007;
        // dp[i][j] 表示從 startPos 到達 i 的步數是 j 的方案數
        long[][] dp = new long[3002][k + 1];
        // 考慮最後一個子問題，dp[i][j] 到達位置 i 的步數是 j，取決於到達位置 i + 1 或 i - 1 的步數是 j - 1 的方案數，故 dp[i][j] = dp[i + 1][j - 1] + dp[i - 1][j - 1]
        // 遍歷順序：dp[i][j] 依賴左上角 dp[i - 1][j - 1] 和 左下角 dp[i + 1][j - 1]，故遍歷順序為：先遍歷 j 再遍歷 i
        int start = startPos + 1000; // 解決數軸上存在負數的問題
        dp[start + 1][1] = 1;
        dp[start - 1][1] = 1;
        for (int j = 2; j <= k; j++) { // 移動步數：
            for (int i = start - j; i <= start + j; i++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i + 1][j - 1]) % mod;
            }
        }
        return (int) dp[endPos + 1000][k];
    }

}
