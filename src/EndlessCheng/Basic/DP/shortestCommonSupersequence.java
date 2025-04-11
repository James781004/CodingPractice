package EndlessCheng.Basic.DP;

public class shortestCommonSupersequence {

    // https://leetcode.cn/problems/shortest-common-supersequence/solutions/2194615/cong-di-gui-dao-di-tui-jiao-ni-yi-bu-bu-auy8z/
    private String s, t;
    private int[][] memo;

    public String shortestCommonSupersequence(String str1, String str2) {
        s = str1;
        t = str2;
        memo = new int[s.length()][t.length()];
        return makeAns(s.length() - 1, t.length() - 1);
    }

    // dfs(i,j) 返回 s 的前 i 個字母和 t 的前 j 個字母的最短公共超序列的長度
    private int dfs(int i, int j) {
        if (i < 0) return j + 1; // s 是空串，返回剩余的 t 的長度
        if (j < 0) return i + 1; // t 是空串，返回剩余的 s 的長度
        if (memo[i][j] > 0) return memo[i][j]; // 避免重復計算 dfs 的結果
        if (s.charAt(i) == t.charAt(j)) // 最短公共超序列一定包含 s[i]
            return memo[i][j] = dfs(i - 1, j - 1) + 1;
        return memo[i][j] = Math.min(dfs(i - 1, j), dfs(i, j - 1)) + 1;
    }

    // makeAns(i,j) 返回 s 的前 i 個字母和 t 的前 j 個字母的最短公共超序列
    // 看上去和 dfs 沒啥區別，但是末尾的遞歸是 if-else
    // makeAns(i-1,j) 和 makeAns(i,j-1) 不會都調用
    // 所以 makeAns 的遞歸樹僅僅是一條鏈
    private String makeAns(int i, int j) {
        if (i < 0) return t.substring(0, j + 1); // s 是空串，返回剩余的 t
        if (j < 0) return s.substring(0, i + 1); // t 是空串，返回剩余的 s
        if (s.charAt(i) == t.charAt(j)) // 最短公共超序列一定包含 s[i]
            return makeAns(i - 1, j - 1) + s.charAt(i);
        // 如果下面 if 成立，說明上面 dfs 中的 min 取的是 dfs(i - 1, j)
        // 說明 dfs(i - 1, j) 對應的公共超序列更短
        // 那麼就在 makeAns(i - 1, j) 的結果後面加上 s[i]
        // 否則說明 dfs(i, j - 1) 對應的公共超序列更短
        // 那麼就在 makeAns(i, j - 1) 的結果後面加上 t[j]
        if (dfs(i, j) == dfs(i - 1, j) + 1)
            return makeAns(i - 1, j) + s.charAt(i);
        return makeAns(i, j - 1) + t.charAt(j);
    }

    public String shortestCommonSupersequenceDP(String str1, String str2) {
        // f[i+1][j+1] 表示 s 的前 i 個字母和 t 的前 j 個字母的最短公共超序列的長度
        char[] s = str1.toCharArray(), t = str2.toCharArray();
        int n = s.length, m = t.length;
        var f = new int[n + 1][m + 1];
        for (int j = 1; j <= m; ++j) f[0][j] = j; // 遞歸邊界
        for (int i = 1; i <= n; ++i) f[i][0] = i; // 遞歸邊界
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < m; ++j)
                if (s[i] == t[j]) // 最短公共超序列一定包含 s[i]
                    f[i + 1][j + 1] = f[i][j] + 1;
                else // 取更短的組成答案
                    f[i + 1][j + 1] = Math.min(f[i][j + 1], f[i + 1][j]) + 1;

        int na = f[n][m];
        var ans = new char[na];
        for (int i = n - 1, j = m - 1, k = na - 1; ; ) {
            if (i < 0) { // s 是空串，剩余的 t 就是最短公共超序列
                System.arraycopy(t, 0, ans, 0, j + 1);
                break; // 相當於遞歸邊界
            }
            if (j < 0) { // t 是空串，剩余的 s 就是最短公共超序列
                System.arraycopy(s, 0, ans, 0, i + 1);
                break; // 相當於遞歸邊界
            }
            if (s[i] == t[j]) { // 公共超序列一定包含 s[i]
                ans[k--] = s[i--]; // 倒著填 ans
                --j; // 相當於繼續遞歸 makeAns(i - 1, j - 1)
            } else if (f[i + 1][j + 1] == f[i][j + 1] + 1)
                ans[k--] = s[i--]; // 相當於繼續遞歸 makeAns(i - 1, j)
            else
                ans[k--] = t[j--]; // 相當於繼續遞歸 makeAns(i, j - 1)
        }
        return new String(ans);
    }


}
