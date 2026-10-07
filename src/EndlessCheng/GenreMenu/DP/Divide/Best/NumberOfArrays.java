package EndlessCheng.GenreMenu.DP.Divide.Best;

public class NumberOfArrays {

    // https://leetcode.cn/problems/restore-the-array/solutions/2856695/ji-yi-hua-sou-suo-di-tui-by-yi-cheng-8i-jens/
    int[] dp;
    int mod = 1000000007;

    public int numberOfArrays(String s, int k) {
        int n = s.length();
        dp = new int[n];
        return dfs(n - 1, s, k);
    }

    // 定義dfs(i)代表了s[0...i]的不同個數
    private int dfs(int i, String s, int k) {
        if (i < 0) {
            return 1;
        }
        if (dp[i] != 0) {
            return dp[i];
        }
        int res = 0;
        for (int j = i; j >= i - 9 && j >= 0; j--) { // 注意長度最多為9
            long num = Long.valueOf(s.substring(j, i + 1));
            if (num > k) {
                break;
            }
            if (s.charAt(j) != '0') {
                res = (res + dfs(j - 1, s, k)) % mod;
            }
        }
        return dp[i] = res;
    }

    public int numberOfArraysDP(String s, int k) {
        int n = s.length();
        int mod = (int) 1e9 + 7;
        int l = String.valueOf(k).length();

        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                // 長度超過k的位數，直接終止
                if (i - j + 1 > l) break;

                // 以0開頭的數字無效
                if (s.charAt(j) == '0') continue;

                // 安全轉換：使用Long避免溢出
                long num = Long.parseLong(s.substring(j, i + 1));
                if (num > k) break;

                // 更新dp[i]
                dp[i] = (dp[i] + (j >= 1 ? dp[j - 1] : 1)) % mod;
            }
        }
        return dp[n - 1];
    }

}
