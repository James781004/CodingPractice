package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class PopcountDepth {

    // https://leetcode.cn/problems/number-of-integers-with-popcount-depth-equal-to-k-i/solutions/3728362/shu-wei-dpzuo-fa-tong-3352-ti-pythonjava-fjti/
    public long popcountDepth(long n, int k) {
        if (k == 0) {
            return 1;
        }

        // 注：也可以不轉成字符串，下面 dfs 用位運算取出 n 的第 i 位
        // 但轉成字符串的通用性更好
        char[] s = Long.toBinaryString(n).toCharArray();
        int m = s.length;
        if (k == 1) {
            return m - 1;
        }

        long[][] memo = new long[m][m + 1];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }

        long ans = 0;
        int[] f = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            f[i] = f[Integer.bitCount(i)] + 1;
            if (f[i] == k) {
                // 計算有多少個二進制數恰好有 i 個 1
                ans += dfs(0, i, true, s, memo);
            }
        }
        return ans;
    }

    private long dfs(int i, int left1, boolean isLimit, char[] s, long[][] memo) {
        if (i == s.length) {
            return left1 == 0 ? 1 : 0;
        }
        if (!isLimit && memo[i][left1] != -1) {
            return memo[i][left1];
        }

        int up = isLimit ? s[i] - '0' : 1;
        long res = 0;
        for (int d = 0; d <= Math.min(up, left1); d++) {
            res += dfs(i + 1, left1 - d, isLimit && d == up, s, memo);
        }

        if (!isLimit) {
            memo[i][left1] = res;
        }
        return res;
    }


}
