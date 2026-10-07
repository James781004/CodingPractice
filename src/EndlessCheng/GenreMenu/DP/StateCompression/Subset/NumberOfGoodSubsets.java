package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumberOfGoodSubsets {

    // https://leetcode.cn/problems/the-number-of-good-subsets/solutions/1282742/pythonjavajavascriptgo-zhuang-tai-ya-suo-peme/
    private static final int[] PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
    private static final int MOD = (int) 1e9 + 7;
    private static final Set<Integer> FORBID = new HashSet<>();
    private static final int LEN_POWER = 1 << PRIMES.length;

    static {
        for (int i = 0; i < 3; i++)
            for (int j = PRIMES[i] * PRIMES[i]; j <= 30; j += PRIMES[i] * PRIMES[i])
                FORBID.add(j);
    }

    public int numberOfGoodSubsets(int[] nums) {
        Map<Integer, Integer> cnts = new HashMap<>();
        for (int num : nums)
            if (!FORBID.contains(num))
                cnts.put(num, cnts.getOrDefault(num, 0) + 1);
        Map<Integer, Integer> keyPrimes = new HashMap<>();
        for (int num : cnts.keySet()) {
            int cur = 0;
            for (int i = 0; i < PRIMES.length; i++)
                if (num % PRIMES[i] == 0)
                    cur |= 1 << i;
            keyPrimes.put(num, cur);
        }
        int[] dp = new int[LEN_POWER];
        dp[0] = qpow(2, cnts.getOrDefault(1, 0), (long) MOD);
        for (int i = 2; i <= 30; i++) {
            if (cnts.containsKey(i))
                for (int j = LEN_POWER - 1; j > 0; j--) {
                    int cur = keyPrimes.get(i);
                    if ((cur & j) == cur) {
                        dp[j] = (dp[j] + (int) ((long) cnts.get(i) * (long) dp[j ^ cur] % MOD)) % MOD;
                    }
                }
        }
        int ans = 0;
        for (int j = 1; j < LEN_POWER; j++)
            ans = (ans + dp[j]) % MOD;
        return ans;
    }

    int qpow(int a, int n, long mod) {
        long ans = 1, la = (long) a;
        while (n > 0) {
            if ((n & 1) == 1)
                ans = ans * la % mod;
            la = la * la % MOD;
            n >>= 1;
        }
        return (int) ans;
    }


}
