package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.math.BigInteger;
import java.util.Arrays;

public class CountNumbers {

    // https://leetcode.cn/problems/count-numbers-with-non-decreasing-digits/solutions/3649556/mo-ban-shang-xia-jie-shu-wei-dp-by-endle-rhuw/
    private static final int MOD = 1_000_000_007;

    public int countNumbers(String l, String r, int b) {
        char[] low = trans(l, b);
        char[] high = trans(r, b);
        int[][] memo = new int[high.length][b];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, 0, true, true, b, low, high, memo);
    }

    // 把十進制字符串 s 轉成 b 進制字符數組
    private char[] trans(String s, int b) {
        return new BigInteger(s).toString(b).toCharArray();
    }

    // dfs(i,pre,limitLow,limitHigh) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // pre 表示前一個位置填的數字，初始值為 0。
    // limitHigh 表示當前是否受到了 high 的約束（我們要構造的數字不能超過 high）。
    // 若為真，則第 i 位填入的數字至多為 high[i]，否則至多為 b−1，這個數記作 hi。
    // 如果在受到約束的情況下填了 hi，那麼後續填入的數字仍會受到 high 的約束。
    // 例如 high=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    // limitLow 表示當前是否受到了 low 的約束（我們要構造的數字不能低於 low）。
    // 若為真，則第 i 位填入的數字至少為 low[i]，否則至少為 0，這個數記作 lo。
    // 如果在受到約束的情況下填了 lo，那麼後續填入的數字仍會受到 low 的約束。
    private int dfs(int i, int pre, boolean limitLow, boolean limitHigh, int b, char[] low, char[] high, int[][] memo) {
        // 遞歸終點：i=n 時，找到了一個合法數字，返回 1
        if (i == high.length) {
            return 1;
        }
        if (!limitLow && !limitHigh && memo[i][pre] >= 0) {
            return memo[i][pre];
        }

        int diffLH = high.length - low.length;
        int lo = limitLow && i >= diffLH ? low[i - diffLH] - '0' : 0;
        int hi = limitHigh ? high[i] - '0' : b - 1;

        long res = 0;

        // 狀態轉移：枚舉第 i 位填數字 d=max(lo,pre),lo+1,…,hi。繼續遞歸，把 i 加一，把 pre 置為 d
        for (int d = Math.max(lo, pre); d <= hi; d++) {
            res += dfs(i + 1, d, limitLow && d == lo, limitHigh && d == hi, b, low, high, memo);
        }
        res %= MOD;

        if (!limitLow && !limitHigh) {
            memo[i][pre] = (int) res;
        }
        return (int) res;
    }


}
