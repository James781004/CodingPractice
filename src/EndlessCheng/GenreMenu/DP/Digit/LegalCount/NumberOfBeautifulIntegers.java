package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class NumberOfBeautifulIntegers {

    // https://leetcode.cn/problems/number-of-beautiful-integers-in-the-range/solutions/2396206/shu-wei-dppythonjavacgo-by-endlesscheng-4gvw/
    private char[] sLow, sHigh;
    private int[][][] dp;
    private int k;

    public int numberOfBeautifulIntegers(int low, int high, int k) {
        sHigh = Integer.toString(high).toCharArray();
        int m = sHigh.length;
        // 補上前導0 與sHigh對齊
        String tmp = Integer.toString(low);
        sLow = ("0".repeat(m - tmp.length()) + tmp).toCharArray();

        // oddDiffeven加上了偏移量m
        dp = new int[m][k][m * 2 + 1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < k; j++)
                Arrays.fill(dp[i][j], -1);
        this.k = k;

        return f(0, 0, m, true, true, false);
    }
    
    // yu是之前填過的數位%k的余數
    // oddDiffeven是之前填過的數位上的數字 奇數、偶數個數之差(已加入偏移量m 所以最後看是否==m)
    public int f(int i, int yu, int oddDiffeven, boolean isLimitLow, boolean isLimitHigh, boolean isNum) {
        if (i == sHigh.length)
            return isNum && yu == 0 && oddDiffeven == sHigh.length ? 1 : 0;
        if (!isLimitLow && !isLimitHigh && isNum && dp[i][yu][oddDiffeven] != -1)
            return dp[i][yu][oddDiffeven];

        int ans = 0;
        // 前面沒填過數字 且sLow[i]==0 那麼這一位i也可以繼續不填(如果sLow[i]>0是下限,這一位i肯定要填個數字！不能不填！)
        if (!isNum && sLow[i] == '0')
            ans += f(i + 1, 0, sHigh.length, true, false, false);
        // 下面要做的是給當前位i填數字
        int downLow = isLimitLow ? sLow[i] - '0' : 0;
        int upHigh = isLimitHigh ? sHigh[i] - '0' : 9;
        int d0 = isNum ? 0 : 1;

        for (int d = Math.max(d0, downLow); d <= upHigh; d++)
            ans += f(i + 1, (yu * 10 + d) % k, oddDiffeven + (d % 2) * 2 - 1, isLimitLow && d == downLow, isLimitHigh && d == upHigh, true);

        if (!isLimitLow && !isLimitHigh && isNum)
            dp[i][yu][oddDiffeven] = ans;
        return ans;
    }


}
