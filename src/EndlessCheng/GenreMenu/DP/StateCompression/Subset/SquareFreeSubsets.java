package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

public class SquareFreeSubsets {

    // https://leetcode.cn/problems/count-the-number-of-square-free-subsets/solutions/2121032/liang-chong-xie-fa-01bei-bao-zi-ji-zhuan-3ooi/
    private static final int[] PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
    private static final int MOD = (int) 1e9 + 7, MX = 30, N_PRIMES = PRIMES.length, M = 1 << N_PRIMES;
    private static final int[] SF_TO_MASK = new int[MX + 1]; // SF_TO_MASK[i] 為 i 的質因子集合（用二進制表示）

    static {
        for (int i = 2; i <= MX; ++i)
            for (int j = 0; j < N_PRIMES; ++j) {
                int p = PRIMES[j];
                if (i % p == 0) {
                    if (i % (p * p) == 0) { // 有平方因子
                        SF_TO_MASK[i] = -1; // square-free number
                        break;
                    }
                    SF_TO_MASK[i] |= 1 << j; // 把 j 加到集合中
                }
            }
    }

    public int squareFreeSubsets(int[] nums) {
        var cnt = new int[MX + 1];
        int pow2 = 1;
        for (int x : nums)
            if (x == 1) pow2 = pow2 * 2 % MOD;
            else ++cnt[x];

        var f = new long[M]; // f[j] 表示恰好組成質數集合 j 的方案數
        f[0] = pow2; // 用 1 組成空質數集合的方案數
        for (int x = 2; x <= MX; ++x) {
            int mask = SF_TO_MASK[x], c = cnt[x];
            if (mask > 0 && c > 0) {
                int other = (M - 1) ^ mask, j = other; // mask 的補集 other
                do { // 枚舉 other 的子集 j
                    f[j | mask] = (f[j | mask] + f[j] * cnt[x]) % MOD; // 不選 mask + 選 mask
                    j = (j - 1) & other;
                } while (j != other);
            }
        }
        var ans = -1L; // 去掉空集（nums 的空子集）
        for (var v : f) ans += v;
        return (int) (ans % MOD);
    }


}
