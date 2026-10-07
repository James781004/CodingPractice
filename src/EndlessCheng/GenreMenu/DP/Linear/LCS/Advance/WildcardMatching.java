package EndlessCheng.GenreMenu.DP.Linear.LCS.Advance;

public class WildcardMatching {

    // https://leetcode.cn/problems/wildcard-matching/solutions/80942/dong-tai-gui-hua-dai-zhu-shi-by-tangweiqun/
    // 狀態 dp[i][j] : 表示 s 的前 i 個字符和 p 的前 j 個字符是否匹配 (true 的話表示匹配)
    // 狀態轉移方程：
    //      1. 當 s[i] == p[j]，或者 p[j] == ? 那麼 dp[i][j] = dp[i - 1][j - 1];
    //      2. 當 p[j] == * 那麼 dp[i][j] = dp[i][j - 1] || dp[i - 1][j]
    //      其中：
    //      dp[i][j - 1] 表示 * 代表的是空字符，例如 ab, ab*
    //      dp[i - 1][j] 表示 * 代表的是非空字符，例如 abcd, ab*
    // 初始化：
    //      1. dp[0][0] 表示什麼都沒有，其值為 true
    //      2. 第一行 dp[0][j]，換句話說，s 為空，與 p 匹配，所以只要 p 開始為 * 才為 true
    //      3. 第一列 dp[i][0]，當然全部為 false
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // 狀態 dp[i][j] : 表示 s 的前 i 個字符和 p 的前 j 個字符是否匹配
        boolean[][] dp = new boolean[m + 1][n + 1];

        // 初始化
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            dp[0][i] = dp[0][i - 1] && p.charAt(i - 1) == '*';
        }

        // 狀態轉移
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }

        // 返回結果
        return dp[m][n];

    }


}
