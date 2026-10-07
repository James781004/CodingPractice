package EndlessCheng.GenreMenu.DP.Digit.LegalSum;

public class TotalWaviness {

    // https://leetcode.cn/problems/total-waviness-of-numbers-in-range-ii/solutions/3839571/shang-xia-jie-shu-wei-dppythonjavacgo-by-74vp/
    public long totalWaviness(long num1, long num2) {
        char[] lowS = Long.toString(num1).toCharArray();
        char[] highS = Long.toString(num2).toCharArray();
        int n = highS.length;
        long[][][][] memo = new long[n][n - 1][3][10]; // 一個數至多包含 n-2 個峰或谷
        return dfs(0, 0, 0, 0, true, true, lowS, highS, memo);
    }

    // dfs(i,waviness,lastCmp,lastDigit,limitLow,limitHigh) 表示能得到的波動值之和
    // 當前在填從高到低第 i 個數位（i 從 0 開始）
    // 前面產生的波動值（峰和谷的個數）為 waviness
    // 上下界約束參數 limitLow 和 limitHigh
    // 記錄 i−1,i−2 所填數字的大小關系 lastCmp（用 −1,0,1 分別表示小於、等於和大於）。
    // 記錄 i−1 所填數字 lastDigit。
    private long dfs(int i, int waviness, int lastCmp, int lastDigit, boolean limitLow, boolean limitHigh, char[] lowS, char[] highS, long[][][][] memo) {
        if (i == highS.length) {
            return waviness;
        }
        if (!limitLow && !limitHigh && memo[i][waviness][lastCmp + 1][lastDigit] > 0) {
            return memo[i][waviness][lastCmp + 1][lastDigit] - 1;
        }

        int diffLh = highS.length - lowS.length;
        int lo = limitLow && i >= diffLh ? lowS[i - diffLh] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;

        long res = 0;
        boolean isNum = !limitLow || i > diffLh; // 前面是否填過數字
        for (int d = lo; d <= hi; d++) {
            // 當前填的數不是最高位，cmp 才有意義
            int cmp = isNum ? Integer.compare(d, lastDigit) : 0;
            int w = waviness + (cmp * lastCmp < 0 ? 1 : 0);
            res += dfs(i + 1, w, cmp, d, limitLow && d == lo, limitHigh && d == hi, lowS, highS, memo);
        }

        if (!limitLow && !limitHigh) {
            memo[i][waviness][lastCmp + 1][lastDigit] = res + 1;
        }
        return res;
    }


}
