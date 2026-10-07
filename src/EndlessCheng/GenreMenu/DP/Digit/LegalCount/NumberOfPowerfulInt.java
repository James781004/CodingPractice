package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class NumberOfPowerfulInt {

    // https://leetcode.cn/problems/count-the-number-of-powerful-integers/solutions/2595149/shu-wei-dp-shang-xia-jie-mo-ban-fu-ti-da-h6ci/
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        String low = Long.toString(start);
        String high = Long.toString(finish);
        int n = high.length();
        low = "0".repeat(n - low.length()) + low; // 補前導零，和 high 對齊
        long[] memo = new long[n];
        Arrays.fill(memo, -1);
        return dfs(0, true, true, low.toCharArray(), high.toCharArray(), limit, s.toCharArray(), memo);
    }

    // 定義 dfs(i,limitLow,limitHigh) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // limitHigh 表示當前是否受到了 finish 的約束（我們要構造的數字不能超過 finish）。
    // 若為真，則第 i 位填入的數字至多為 finish[i]，否則至多為 9，這個數記作 hi。
    // 如果在受到約束的情況下填了 finish[i]，那麼後續填入的數字仍會受到 finish 的約束。
    // 例如 finish=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    // limitLow 表示當前是否受到了 start 的約束（我們要構造的數字不能低於 start）。
    // 若為真，則第 i 位填入的數字至少為 start[i]，否則至少為 0，這個數記作 lo。
    // 如果在受到約束的情況下填了 start[i]，那麼後續填入的數字仍會受到 start 的約束。
    private long dfs(int i, boolean limitLow, boolean limitHigh, char[] low, char[] high, int limit, char[] s, long[] memo) {
        if (i == high.length) {
            return 1;
        }

        if (!limitLow && !limitHigh && memo[i] != -1) {
            return memo[i]; // 之前計算過
        }

        // 第 i 個數位可以從 lo 枚舉到 hi
        // 如果對數位還有其它約束，應當只在下面的 for 循環做限制，不應修改 lo 或 hi
        int lo = limitLow ? low[i] - '0' : 0;
        int hi = limitHigh ? high[i] - '0' : 9;

        long res = 0;
        if (i < high.length - s.length) { // 枚舉這個數位填什麼
            for (int d = lo; d <= Math.min(hi, limit); d++) {
                res += dfs(i + 1, limitLow && d == lo, limitHigh && d == hi, low, high, limit, s, memo);
            }
        } else { // 這個數位只能填 s[i-diff]
            int x = s[i - (high.length - s.length)] - '0';
            if (lo <= x && x <= hi) { // 題目保證 x <= limit，無需判斷
                res = dfs(i + 1, limitLow && x == lo, limitHigh && x == hi, low, high, limit, s, memo);
            }
        }

        if (!limitLow && !limitHigh) {
            memo[i] = res; // 記憶化 (i,false,false)
        }
        return res;
    }


}
