package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class DistinctSequences {

    // https://leetcode.cn/problems/number-of-distinct-roll-sequences/solutions/1625297/by-endlesscheng-tgkn/
    static final int MOD = (int) 1e9 + 7, MX = (int) 1e4;
    static final int[][][] f = new int[MX + 1][6][6];

    static {
        for (var i = 0; i < 6; ++i)
            for (var j = 0; j < 6; ++j)
                if (j != i && gcd(j + 1, i + 1) == 1)
                    f[2][i][j] = 1;
        for (var i = 2; i < MX; ++i)
            for (var j = 0; j < 6; ++j)
                for (var last = 0; last < 6; ++last)
                    if (last != j && gcd(last + 1, j + 1) == 1)
                        for (var last2 = 0; last2 < 6; ++last2)
                            if (last2 != j)
                                f[i + 1][j][last] = (f[i + 1][j][last] + f[i][last][last2]) % MOD;
    }

    public int distinctSequences(int n) {
        if (n == 1) return 6;
        var ans = 0;
        for (var i = 0; i < 6; ++i)
            for (var j = 0; j < 6; ++j)
                ans = (ans + f[n][i][j]) % MOD;
        return ans;
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

   
}
