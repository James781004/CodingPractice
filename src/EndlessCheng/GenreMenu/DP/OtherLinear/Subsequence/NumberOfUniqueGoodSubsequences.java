package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

public class NumberOfUniqueGoodSubsequences {

    // https://leetcode.cn/problems/number-of-unique-good-subsequences/solutions/965042/jian-ji-xie-fa-by-endlesscheng-bvrx/
    public int numberOfUniqueGoodSubsequences(String s) {
        final int mod = (int) 1e9 + 7;
        int[] f = new int[2]; // 定義 f[i][0/1] 表示前 i 個字符中，以 0 或 1 結尾的不同子序列的個數(代碼實現時，第一維可以壓縮掉)

        // 反轉避免前導零的影響
        for (int i = s.length() - 1; i >= 0; i--) {
            int idx = s.charAt(i) & 1;
            f[idx] = (f[0] + f[1] + 1) % mod;
        }

        int ans = f[1];
        if (s.contains("0")) {
            ans = (ans + 1) % mod;
        }
        return ans;
    }

}
