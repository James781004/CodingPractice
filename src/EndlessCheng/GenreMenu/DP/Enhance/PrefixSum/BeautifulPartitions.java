package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class BeautifulPartitions {

    // https://leetcode.cn/problems/number-of-beautiful-partitions/solutions/1981346/dong-tai-gui-hua-jian-ji-xie-fa-xun-huan-xyw3/
    private static final int MOD = (int) 1e9 + 7;

    public int beautifulPartitions(String S, int k, int l) {
        var s = S.toCharArray();
        var n = s.length;
        if (k * l > n || !isPrime(s[0]) || isPrime(s[n - 1])) // 剪枝
            return 0;
        var f = new int[k + 1][n + 1]; // 定義 f[i][j] 表示把 s 的前 j 個字符分割成 i 段的方案數（每段需要滿足題目的後兩個要求）
        f[0][0] = 1;
        for (var i = 1; i <= k; ++i) {
            var sum = 0;
            // 優化：枚舉的起點和終點需要給前後的子串預留出足夠的長度
            for (var j = i * l; j + (k - i) * l <= n; j++) {
                if (canPartition(s, j - l)) sum = (sum + f[i - 1][j - l]) % MOD; // j'=j-l 雙指針
                if (canPartition(s, j)) f[i][j] = sum;
            }
        }
        return f[k][n];
    }

    private boolean isPrime(char c) {
        return c == '2' || c == '3' || c == '5' || c == '7';
    }

    // 判斷是否可以在 j-1 和 j 之間分割（開頭和末尾也算）
    private boolean canPartition(char[] s, int j) {
        return j == 0 || j == s.length || !isPrime(s[j - 1]) && isPrime(s[j]);
    }


}
