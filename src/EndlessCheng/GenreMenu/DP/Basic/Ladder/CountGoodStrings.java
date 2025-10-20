package EndlessCheng.GenreMenu.DP.Basic.Ladder;

import java.util.Arrays;

public class CountGoodStrings {

    // https://leetcode.cn/problems/count-ways-to-build-good-strings/solutions/1964910/by-endlesscheng-4j22/
    private static final int MOD = 1_000_000_007;

    public int countGoodStrings(int low, int high, int zero, int one) {
        int[] memo = new int[high + 1];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        int ans = 0;
        for (int i = low; i <= high; i++) {
            ans = (ans + dfs(i, zero, one, memo)) % MOD;
        }
        return ans;
    }

    private int dfs(int i, int zero, int one, int[] memo) {
        if (i < 0) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        return memo[i] = (dfs(i - zero, zero, one, memo) + dfs(i - one, zero, one, memo)) % MOD;
    }

    public int countGoodStringsDP(int low, int high, int zero, int one) {
        final int MOD = 1_000_000_007;
        int ans = 0;
        int[] f = new int[high + 1]; // f[i] 表示構造長為 i 的字符串的方案數
        f[0] = 1; // 構造空串的方案數為 1
        for (int i = 1; i <= high; i++) {
            if (i >= zero) f[i] = f[i - zero];
            if (i >= one) f[i] = (f[i] + f[i - one]) % MOD;
            if (i >= low) ans = (ans + f[i]) % MOD;
        }
        return ans;
    }

    public int countGoodStringsDP2(int low, int high, int zero, int one) {
        int g = gcd(zero, one);
        low = (low - 1) / g + 1;
        high /= g;
        zero /= g;
        one /= g;

        final int MOD = 1_000_000_007;
        int ans = 0;
        int[] f = new int[high + 1]; // f[i] 表示構造長為 i 的字符串的方案數
        f[0] = 1; // 構造空串的方案數為 1
        for (int i = 1; i <= high; i++) {
            if (i >= zero) f[i] = f[i - zero];
            if (i >= one) f[i] = (f[i] + f[i - one]) % MOD;
            if (i >= low) ans = (ans + f[i]) % MOD;
        }
        return ans;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


}
