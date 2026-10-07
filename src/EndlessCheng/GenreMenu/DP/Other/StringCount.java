package EndlessCheng.GenreMenu.DP.Other;

public class StringCount {

    // https://leetcode.cn/problems/number-of-strings-which-can-be-rearranged-to-contain-substring/solutions/2522964/olog-n-rong-chi-yuan-li-fu-ji-yi-hua-sou-okjf/
    private static final long MOD = (long) 1e9 + 7;

    public int stringCount(int n) {
        return (int) (((pow(26, n)
                - pow(25, n - 1) * (75 + n)
                + pow(24, n - 1) * (72 + n * 2)
                - pow(23, n - 1) * (23 + n)) % MOD + MOD) % MOD); // 保證結果非負
    }

    private long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }

}
