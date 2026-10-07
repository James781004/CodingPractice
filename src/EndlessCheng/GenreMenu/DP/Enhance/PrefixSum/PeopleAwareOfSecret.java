package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class PeopleAwareOfSecret {

    // https://leetcode.cn/problems/number-of-people-aware-of-a-secret/solutions/1641511/by-endlesscheng-2x0z/
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        final int MOD = 1_000_000_007;
        int[] sum = new int[n + 1]; // known 數組的前綴和
        sum[1] = 1;

        for (int j = 2; j <= n; j++) {
            int known = (sum[Math.max(j - delay, 0)] - sum[Math.max(j - forget, 0)]) % MOD;
            sum[j] = (sum[j - 1] + known) % MOD;
        }

        int ans = sum[n] - sum[Math.max(n - forget, 0)];
        return (ans % MOD + MOD) % MOD; // 保證答案非負
    }

}
