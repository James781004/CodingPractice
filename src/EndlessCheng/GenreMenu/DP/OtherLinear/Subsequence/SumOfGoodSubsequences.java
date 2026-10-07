package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.HashMap;
import java.util.Map;

public class SumOfGoodSubsequences {

    // https://leetcode.cn/problems/sum-of-good-subsequences/solutions/2983496/te-shu-zi-xu-lie-dppythonjavacgo-by-endl-vv7e/
    public int sumOfGoodSubsequences(int[] nums) {
        final int MOD = 1_000_000_007;
        Map<Integer, Integer> f = new HashMap<>(); // f[x] 表示以元素 x 結尾的子序列的元素之和。子序列的倒數第二個數是 x−1 或 x+1
        Map<Integer, Integer> cnt = new HashMap<>(); // 以 x 結尾的子序列的個數，記作 cnt[x]
        for (int x : nums) {
            // x 可以加在所有以 x−1 結尾的子序列之後，以及所有以 x+1 結尾的子序列之後
            long c = cnt.getOrDefault(x - 1, 0) + cnt.getOrDefault(x + 1, 0) + 1;

            // 不選 x，元素和為 f[x]
            // x 加在所有以 x−1 結尾的子序列之後，這會額外產生 cnt[x−1] 個子序列，這些子序列的元素總和為 f[x−1]+x⋅cnt[x−1]
            // x 加在所有以 x+1 結尾的子序列之後，這會額外產生 cnt[x+1] 個子序列，這些子序列的元素總和為 f[x+1]+x⋅cnt[x+1]
            // x 單獨作為一個子序列，元素和為 x
            // 所以有 f[x]=f[x]+f[x−1]+f[x+1]+x⋅(cnt[x−1]+cnt[x+1]+1)
            f.put(x, (int) ((x * c + f.getOrDefault(x, 0) + f.getOrDefault(x - 1, 0) + f.getOrDefault(x + 1, 0)) % MOD));

            // 同時，額外產生了 cnt[x−1]+cnt[x+1]+1 個以 x 結尾的子序列，
            // 所以有 cnt[x]=cnt[x]+cnt[x−1]+cnt[x+1]+1
            cnt.put(x, (int) ((cnt.getOrDefault(x, 0) + c) % MOD));
        }

        long ans = 0;
        for (int s : f.values()) {
            ans += s;
        }
        return (int) (ans % MOD);
    }


}
