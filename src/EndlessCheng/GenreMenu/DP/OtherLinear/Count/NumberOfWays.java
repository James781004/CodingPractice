package EndlessCheng.GenreMenu.DP.OtherLinear.Count;

public class NumberOfWays {

    // https://leetcode.cn/problems/find-the-number-of-possible-ways-for-an-event/solutions/2948578/zu-he-shu-xue-di-er-lei-si-te-lin-shu-py-e6sv/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 1001;
    private static final int[][] s = new int[MX][MX]; // S(i,j) 表示把 i 個人劃分成 j 個非空集合的方案數

    static {
        s[0][0] = 1;
        for (int i = 1; i < MX; i++) {
            for (int j = 1; j <= i; j++) {
                // 第 i 個人單獨形成一個集合，那麼問題變成把 i−1 個人劃分成 j−1 個非空集合的方案數，即 S(i−1,j−1)
                // 第 i 個人放入前面 j 個集合中，有 j 種方法，問題變成把 i−1 個人劃分成 j 個非空集合的方案數，二者相乘得 j⋅S(i−1,j)。
                // 加法原理，二者相加得 S(i,j)=S(i−1,j−1)+j⋅S(i−1,j)
                s[i][j] = (int) ((s[i - 1][j - 1] + (long) j * s[i - 1][j]) % MOD);
            }
        }
    }

    public int numberOfWays(int n, int x, int y) {
        long ans = 0;
        long perm = 1;
        long powY = 1;
        for (int i = 1; i <= Math.min(n, x); i++) {
            perm = perm * (x + 1 - i) % MOD;
            powY = powY * y % MOD;
            ans = (ans + perm * s[n][i] % MOD * powY) % MOD;
        }
        return (int) ans;
    }


}
