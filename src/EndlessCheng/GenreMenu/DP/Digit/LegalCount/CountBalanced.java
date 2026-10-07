package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

public class CountBalanced {

    // https://leetcode.cn/problems/number-of-balanced-integers-in-a-range/solutions/3867594/mo-ban-shang-xia-jie-shu-wei-dppythonjav-lnd8/
    public long countBalanced(long low, long high) {
        // 最小的滿足要求的數是 11
        if (high < 11) {
            return 0;
        }

        low = Math.max(low, 11);
        char[] lowS = String.valueOf(low).toCharArray();
        char[] highS = String.valueOf(high).toCharArray();

        int n = highS.length;

        // diff=奇數位之和−偶數位之和
        // Diff== n/2 * 9: 例子為： 909090... = 6/2 * 9 =3*9= 27
        // diff 至少 floor(n/2) * 9，至多 ceil(n/2) * 9，值域大小 n * 9
        long[][] memo = new long[n][n * 9 + 1];

        return dfs(0, n / 2 * 9, true, true, lowS, highS, memo);
    }

    private long dfs(int i, int diff, boolean limitLow, boolean limitHigh, char[] lowS, char[] highS, long[][] memo) {
        int n = highS.length;
        if (i == n) {
            return diff == n / 2 * 9 ? 1 : 0;
        }

        if (!limitLow && !limitHigh && memo[i][diff] > 0) {
            return memo[i][diff] - 1; // 記憶化的時候 +1，這裡減掉
        }

        int diffLH = n - lowS.length;
        int lo = limitLow && i >= diffLH ? lowS[i - diffLH] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;

        long res = 0;
        int d = lo;

        // 通過 limitLow 和 i 可以判斷能否不填數字，無需 isNum 參數
        if (limitLow && i < diffLH) {
            // 不填數字，上界不受約束
            res = dfs(i + 1, diff, true, false, lowS, highS, memo);
            d = 1; // 下面填數字，至少從 1 開始填
        }

        for (; d <= hi; d++) {
            res += dfs(i + 1,
                    diff + (i % 2 == 0 ? d : -d),
                    limitLow && d == lo,
                    limitHigh && d == hi,
                    lowS, highS, memo);
        }

        if (!limitLow && !limitHigh) {
            memo[i][diff] = res + 1; // 記憶化的時候加一，這樣 memo 可以初始化成 0
        }
        return res;
    }


}
