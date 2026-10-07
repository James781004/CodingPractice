package EndlessCheng.GenreMenu.DP.Enhance.Squaring;

public class NumTilings {

    // https://leetcode.cn/problems/domino-and-tromino-tiling/solutions/1968516/by-endlesscheng-umpp/
    private static final int MOD = 1_000_000_007;

    public int numTilings(int n) {
        if (n == 1) {
            return 1;
        }
        long[][] f2 = {{2}, {1}, {1}};
        long[][] m = {
                {2, 0, 1},
                {1, 0, 0},
                {0, 1, 0},
        };
        long[][] fn = powMul(m, n - 2, f2);
        return (int) fn[0][0];
    }

    // a^n * f
    private long[][] powMul(long[][] a, int n, long[][] f) {
        long[][] res = f;
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
