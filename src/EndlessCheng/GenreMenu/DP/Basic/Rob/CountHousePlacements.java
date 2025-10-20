package EndlessCheng.GenreMenu.DP.Basic.Rob;

public class CountHousePlacements {

    // https://leetcode.cn/problems/count-number-of-ways-to-place-houses/solutions/1625979/d-by-endlesscheng-gybx/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 10_001;
    private static final int[] f = new int[MX];

    static {
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < MX; i++) {
            f[i] = (f[i - 1] + f[i - 2]) % MOD;
        }
    }

    public int countHousePlacements(int n) {
        return (int) ((long) f[n] * f[n] % MOD); // 兩側的房屋互相獨立，根據乘法原理答案為 f[n]^2

    }


}
