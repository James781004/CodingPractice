package EndlessCheng.GenreMenu.DP.Divide.Best;

public class DeleteString {

    // https://leetcode.cn/problems/maximum-deletions-on-a-string/solutions/1864049/xian-xing-dppythonjavacgo-by-endlesschen-gpx9/
    public int deleteString(String s) {
        int n = s.length();

        // lcp[i][j] 代表s[i:]和s[j:]的最長公共前綴長度
        int[][] lcp = new int[n][n];
        char[] str = s.toCharArray();
        for (int i = n - 1; i >= 0; --i) {
            for (int j = n - 1; j >= 0; --j) {
                if (str[i] == str[j]) {
                    lcp[i][j] = 1 + ((i < n - 1 && j < n - 1) ? lcp[i + 1][j + 1] : 0);
                }
            }
        }

        // 對於s[i:]，如果能夠滿足第二個條件（存在連續相同的子串，即s[i:j]=s[j+1:2j]），
        // 則能夠通過第二種方法進行刪除，嘗試更新最大值
        // 如果lcp[i][i+j] >= j，就說明s[i:]和s[i+j:]的最長公共前綴長度大於s[i:j]這個子串的長度
        // 說明s[i:j]=s[i+j:2j]

        // dp[i]代表對s[i:]進行刪除操作所需最大操作數
        // dp[i] = max(dp[i + j] + 1), i + j < n && lcp[i][i + j] >= j
        int[] dp = new int[n];
        dp[n - 1] = 1;
        for (int i = n - 2; i >= 0; --i) {
            dp[i] = 1;
            for (int j = 1; i + j < n; ++j) {
                // 嘗試通過第二種刪除方法更新最大值
                if (lcp[i][i + j] >= j) {
                    dp[i] = Math.max(dp[i], dp[i + j] + 1);
                }
            }
        }
        return dp[0];
    }

    private boolean allEqual(char[] s) {
        for (int i = 1; i < s.length; i++) {
            if (s[i] != s[0]) {
                return false;
            }
        }
        return true;
    }


}
