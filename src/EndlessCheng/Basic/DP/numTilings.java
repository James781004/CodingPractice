package EndlessCheng.Basic.DP;

public class numTilings {

    // https://leetcode.cn/problems/domino-and-tromino-tiling/solutions/1968516/by-endlesscheng-umpp/
    private static final long MOD = (long) 1e9 + 7;

    public int numTilings(int n) {
        if (n == 1) return 1;
        long[] f = new long[n + 1];
        f[0] = f[1] = 1;
        f[2] = 2;
        for (int i = 3; i <= n; ++i)
            f[i] = (f[i - 1] * 2 + f[i - 3]) % MOD;
        return (int) f[n];
    }


    public int numTilings2(int n) {
        if (n == 1) return 1;
        long a = 1, b = 1, c = 2;
        for (int i = 3; i <= n; ++i) {
            long f = (c * 2 + a) % MOD;
            a = b;
            b = c;
            c = f;
        }
        return (int) c;
    }


}
