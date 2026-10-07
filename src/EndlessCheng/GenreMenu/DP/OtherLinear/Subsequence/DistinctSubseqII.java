package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;

public class DistinctSubseqII {

    // https://leetcode.cn/problems/distinct-subsequences-ii/solutions/1890716/xi-fen-wen-ti-fu-za-du-you-hua-pythonjav-1ihu/
    private static final int MOD = 1_000_000_007;

    public int distinctSubseqII(String s) {
        int n = s.length();
        long[][] f = new long[n + 1][26];
        for (int i = 0; i < n; i++) {
            f[i + 1] = f[i].clone();
            f[i + 1][s.charAt(i) - 'a'] = (1 + Arrays.stream(f[i]).sum()) % MOD;
        }
        return (int) (Arrays.stream(f[n]).sum() % MOD);
    }


    public int distinctSubseqII2(String s) {
        long[] f = new long[26];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            f[s.charAt(i) - 'a'] = (1 + Arrays.stream(f).sum()) % MOD;
        }
        return (int) (Arrays.stream(f).sum() % MOD);
    }


}
