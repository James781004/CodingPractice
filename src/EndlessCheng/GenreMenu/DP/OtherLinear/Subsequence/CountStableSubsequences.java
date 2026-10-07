package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

public class CountStableSubsequences {

    // https://leetcode.cn/problems/number-of-stable-subsequences/solutions/3781347/he-fa-zi-xu-lie-dppythonjavacgo-by-endle-7sz0/
    public int countStableSubsequences(int[] nums) {
        final int MOD = 1_000_000_007;
        long[][] f = new long[2][2]; // 定義 f[i+1][x][j] 表示滿足如下約束的穩定子序列的個數（+1 是為了用 f[0] 表示空前綴）
        for (int x : nums) {
            x %= 2;
            f[x][1] = (f[x][1] + f[x][0]) % MOD;
            f[x][0] = (f[x][0] + f[x ^ 1][0] + f[x ^ 1][1] + 1) % MOD;
        }
        return (int) ((f[0][0] + f[0][1] + f[1][0] + f[1][1]) % MOD);
    }


}
