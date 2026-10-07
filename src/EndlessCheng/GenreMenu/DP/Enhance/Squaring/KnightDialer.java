package EndlessCheng.GenreMenu.DP.Enhance.Squaring;

public class KnightDialer {

    // https://leetcode.cn/problems/knight-dialer/solutions/3004116/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-x06l/
    private static final int MOD = 1_000_000_007;

    public int knightDialer(int n) {
        if (n == 1) {
            return 10;
        }
        long[][] m = {
                {0, 1, 1, 0},
                {2, 0, 0, 0},
                {2, 0, 0, 1},
                {0, 0, 2, 0},
        };
        long[][] f0 = {{1}, {1}, {1}, {1}};
        long[][] fn = powMul(m, n - 1, f0);
        return (int) ((fn[0][0] * 4 + fn[1][0] * 2 +
                fn[2][0] * 2 + fn[3][0]) % MOD);
    }

    // a^n * f0
    private long[][] powMul(long[][] a, int n, long[][] f0) {
        long[][] res = f0;
        while (n > 0) {
            if ((n & 1) > 0) {
                res = mul(a, res);
            }
            a = mul(a, a);
            n >>= 1;
        }
        return res;
    }

    // 返回矩陣 a 和矩陣 b 相乘的結果
    private long[][] mul(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[i].length; k++) {
                if (a[i][k] == 0) {
                    continue;
                }
                for (int j = 0; j < b[k].length; j++) {
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return c;
    }

}
