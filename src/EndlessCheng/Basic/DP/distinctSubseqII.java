package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class distinctSubseqII {

    // https://leetcode.cn/problems/distinct-subsequences-ii/solutions/1890716/xi-fen-wen-ti-fu-za-du-you-hua-pythonjav-1ihu/
    private final long MOD = (long) 1e9 + 7;

    public int distinctSubseqII(String s) {
        var n = s.length();
        var f = new long[n][26]; // 用 s 的前 i 個字符組成以 j 結尾的不同非空子序列的個數
        f[0][s.charAt(0) - 'a'] = 1;
        for (var i = 1; i < n; i++) {
            f[i] = f[i - 1].clone();

            // 把 s[i] 加到前 i−1 個字符組成的不同子序列的末尾，同時把 s[i] 單獨作為一個子序列
            f[i][s.charAt(i) - 'a'] = (1 + Arrays.stream(f[i - 1]).sum()) % MOD;
        }
        return (int) (Arrays.stream(f[n - 1]).sum() % MOD);
    }


}
