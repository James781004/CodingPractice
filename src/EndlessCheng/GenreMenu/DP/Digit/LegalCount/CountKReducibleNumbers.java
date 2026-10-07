package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountKReducibleNumbers {

    // https://leetcode.cn/problems/count-k-reducible-numbers-less-than-n/solutions/2983541/xian-xing-dp-shu-wei-dppythonjavacgo-by-yw0dl/
    private static final int MOD = 1_000_000_007;

    public int countKReducibleNumbers(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length; // 設 s 的長度為 n
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        long ans = 0;
        int[] f = new int[n];
        for (int i = 1; i < n; i++) {
            f[i] = f[Integer.bitCount(i)] + 1;
            if (f[i] <= k) {
                // 計算有多少個二進制數恰好有 i 個 1
                ans += dfs(0, i, true, s, memo);
            }
        }
        return (int) (ans % MOD);
    }

    // 數位 DP
    // 本題需要嚴格小於 s，這可以用 isLimit 判斷：遞歸到 i=n 時，若仍有 isLimit=true，則返回 0。
    // 此外，我們只關心 1 的個數，是否有前導零無影響，所以無需 isNum 參數。
    private int dfs(int i, int left1, boolean isLimit, char[] s, int[][] memo) {
        if (i == s.length) {
            return !isLimit && left1 == 0 ? 1 : 0;
        }
        if (!isLimit && memo[i][left1] != -1) {
            return memo[i][left1];
        }
        int up = isLimit ? s[i] - '0' : 1;
        int res = 0;
        for (int d = 0; d <= Math.min(up, left1); d++) {
            res = (res + dfs(i + 1, left1 - d, isLimit && d == up, s, memo)) % MOD;
        }
        if (!isLimit) {
            memo[i][left1] = res;
        }
        return res;
    }


}
