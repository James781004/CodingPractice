package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.HashMap;
import java.util.Map;

public class BeautifulNumbers {

    // https://leetcode.cn/problems/count-beautiful-numbers/solutions/3613931/mo-ban-shu-wei-dp-v21pythonjavacgo-by-en-fdzz/
    public int beautifulNumbers(int l, int r) {
        char[] low = String.valueOf(l).toCharArray(); // 無需補前導零
        char[] high = String.valueOf(r).toCharArray();
        Map<Long, Integer> memo = new HashMap<>();

        // 遞歸入口：dfs(0,1,0,true,true)。
        // 一開始沒有填數字，數位乘積為 1（乘法單位元），數位之和為 0（加法單位元）。
        return dfs(0, 1, 0, true, true, low, high, memo);
    }

    // dfs(i,m,s,limitLow,limitHigh) 表示在如下約束時的美麗整數個數：
    // 前 i 個數位已經填完了。
    // 之前填的數位乘積為 m。
    // 之前填的數位之和為 s。
    private int dfs(int i, int m, int s, boolean limitLow, boolean limitHigh,
                    char[] low, char[] high, Map<Long, Integer> memo) {
        if (i == high.length) {
            return s > 0 && m % s == 0 ? 1 : 0;
        }
        long mask = (long) m << 32 | i << 16 | s; // 三個 int 壓縮成一個 long
        if (!limitLow && !limitHigh && memo.containsKey(mask)) {
            return memo.get(mask);
        }

        int diffLh = high.length - low.length;
        int lo = limitLow && i >= diffLh ? low[i - diffLh] - '0' : 0;
        int hi = limitHigh ? high[i] - '0' : 9;

        int res = 0;
        int d = lo;
        if (limitLow && i < diffLh) { // 利用 limitLow 和 i 可以判斷出 isNum 是否為 true，所以 isNum 可以省略
            res = dfs(i + 1, 1, 0, true, false, low, high, memo); // 什麼也不填
            d = 1; // 下面循環從 1 開始
        }

        // 枚舉當前數位填 d，那麼 m 變成 m⋅d，s 變成 s+d，繼續遞歸
        for (; d <= hi; d++) {
            res += dfs(i + 1, m * d, s + d, limitLow && d == lo, limitHigh && d == hi, low, high, memo);
        }

        if (!limitLow && !limitHigh) {
            memo.put(mask, res);
        }
        return res;
    }


}
