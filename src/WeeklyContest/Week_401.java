package WeeklyContest;

import java.math.BigInteger;
import java.util.Arrays;

public class Week_401 {
    // https://leetcode.cn/problems/find-the-child-who-has-the-ball-after-k-seconds/solutions/2805442/o1-gong-shi-pythonjavacgo-by-endlesschen-xl9f/
    // 首先判斷k秒後傳遞的方向，n個數傳遞n-1秒到達最後一個位置，
    // 所有當k/(n-1)是奇數時表示當前傳遞方向反向，為偶數表示正向；
    // 再進行取余運算即可計算出當前所在的位置。
    public int numberOfChild(int n, int k) {
        int t = k % (n - 1);
        return k / (n - 1) % 2 > 0 ? n - t - 1 : t;
    }


    // https://leetcode.cn/problems/find-the-n-th-value-after-k-seconds/solutions/2805408/zu-he-shu-xue-o1-gong-shi-pythonjavacgo-0fbgt/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 2001;

    // 組合數模板
    private static final long[] FAC = new long[MX];
    private static final long[] INV_FAC = new long[MX];

    static {
        FAC[0] = 1;
        for (int i = 1; i < MX; i++) {
            FAC[i] = FAC[i - 1] * i % MOD;
        }
        INV_FAC[MX - 1] = pow(FAC[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; i--) {
            INV_FAC[i - 1] = INV_FAC[i] * i % MOD;
        }
    }

    private static long comb(int n, int k) {
        return FAC[n] * INV_FAC[k] % MOD * INV_FAC[n - k] % MOD;
    }

    public int valueAfterKSeconds(int n, int k) {
        return (int) comb(n + k - 1, k);
    }

    private static long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }


    // https://leetcode.cn/problems/maximum-total-reward-using-operations-ii/solutions/2805413/bitset-you-hua-0-1-bei-bao-by-endlessche-m1xn/
    public int maxTotalReward(int[] rewardValues) {
        BigInteger f = BigInteger.ONE;
        for (int v : Arrays.stream(rewardValues).distinct().sorted().toArray()) {
            BigInteger mask = BigInteger.ONE.shiftLeft(v).subtract(BigInteger.ONE);
            f = f.or(f.and(mask).shiftLeft(v));
        }
        return f.bitLength() - 1;
    }
}


