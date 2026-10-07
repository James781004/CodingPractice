package EndlessCheng.GenreMenu.DP.Enhance.Other;

public class DistinctSequences {

    // https://leetcode.cn/problems/number-of-distinct-roll-sequences/solutions/1625297/by-endlesscheng-tgkn/
    static final int MOD = (int) 1e9 + 7, MX = (int) 1e4;
    static final int[][] f = new int[MX + 1][6];

    static {
        for (var i = 0; i < 6; ++i)
            f[1][i] = 1;
        for (var i = 2; i <= MX; ++i)
            for (var j = 0; j < 6; ++j) {
                var s = 0L;
                for (var k = 0; k < 6; ++k)
                    if (k != j && gcd(k + 1, j + 1) == 1)
                        s += f[i - 1][k];
                s -= (long) (i > 3 ? f[2][j] - 1 : f[2][j]) * f[i - 2][j];
                f[i][j] = (int) (s % MOD);
            }
    }

    public int distinctSequences(int n) {
        var ans = 0L;
        for (var v : f[n])
            ans += v;
        return (int) (ans % MOD + MOD) % MOD; // 保證結果非負
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


}
