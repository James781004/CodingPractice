package EndlessCheng.GenreMenu.DP.Print;

public class LargestPalindrome {

    // https://leetcode.cn/problems/find-the-largest-palindrome-divisible-by-k/solutions/2884548/tong-yong-zuo-fa-jian-tu-dfsshu-chu-ju-t-m3pu/
    public String largestPalindrome(int n, int k) {
        int[] pow10 = new int[n];
        pow10[0] = 1;
        for (int i = 1; i < n; i++) {
            pow10[i] = pow10[i - 1] * 10 % k;
        }

        char[] ans = new char[n];
        int m = (n + 1) / 2;
        boolean[][] vis = new boolean[m + 1][k];
        dfs(0, 0, n, k, m, pow10, ans, vis);
        return new String(ans);
    }

    private boolean dfs(int i, int j, int n, int k, int m, int[] pow10, char[] ans, boolean[][] vis) {
        if (i == m) {
            return j == 0;
        }
        vis[i][j] = true;
        for (int d = 9; d >= 0; d--) { // 貪心：從大到小枚舉
            int j2;
            if (n % 2 > 0 && i == m - 1) { // 正中間
                j2 = (j + d * pow10[i]) % k;
            } else {
                j2 = (j + d * (pow10[i] + pow10[n - 1 - i])) % k;
            }
            if (!vis[i + 1][j2] && dfs(i + 1, j2, n, k, m, pow10, ans, vis)) {
                ans[i] = ans[n - 1 - i] = (char) ('0' + d);
                return true;
            }
        }
        return false;
    }
}
