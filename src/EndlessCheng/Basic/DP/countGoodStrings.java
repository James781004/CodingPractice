package EndlessCheng.Basic.DP;

public class countGoodStrings {

    // https://leetcode.cn/problems/count-ways-to-build-good-strings/solutions/1964910/by-endlesscheng-4j22/
    public int countGoodStrings(int low, int high, int zero, int one) {
        final int MOD = 1_000_000_007;
        int ans = 0;
        int[] f = new int[high + 1]; // f[i] 表示構造長為 i 的字符串的方案數
        f[0] = 1; // 構造空串的方案數為 1
        for (int i = 1; i <= high; i++) {
            if (i >= zero) f[i] = (f[i] + f[i - zero]) % MOD;
            if (i >= one) f[i] = (f[i] + f[i - one]) % MOD;
            if (i >= low) ans = (ans + f[i]) % MOD;
        }
        return ans;
    }

}
