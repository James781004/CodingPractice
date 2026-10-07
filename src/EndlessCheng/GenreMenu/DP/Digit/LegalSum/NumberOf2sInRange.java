package EndlessCheng.GenreMenu.DP.Digit.LegalSum;

import java.util.Arrays;

public class NumberOf2sInRange {

    // https://leetcode.cn/problems/number-of-2s-in-range-lcci/solutions/1750395/by-endlesscheng-x4mf/
    char s[];
    int dp[][];

    public int numberOf2sInRange(int n) {
        s = Integer.toString(n).toCharArray();
        var m = s.length;
        dp = new int[m][m];
        for (var i = 0; i < m; i++) Arrays.fill(dp[i], -1);
        return f(0, 0, true);
    }

    // 定義 f(i,cnt_2 ,isLimit,isNum) 表示在前 i 位有 cnt_2 個 2 的前提下，我們能構造出的數中的 2 的個數總和。
    // 其余參數的含義為：
    // isLimit 表示當前是否受到了 n 的約束。若為真，則第 i 位填入的數字至多為 s[i]，否則可以是 9。
    // 如果在受到約束的情況下填了 s[i]，那麼後續填入的數字仍會受到 n 的約束。
    // isNum 表示 i 前面的數位是否填了數字。
    // 若為假，則當前位可以跳過（不填數字），或者要填入的數字至少為 1；
    // 若為真，則要填入的數字可以從 0 開始。
    // 對於本題來說，由於前導零對答案無影響，isNum 可以省略
    int f(int i, int cnt2, boolean isLimit) {
        if (i == s.length) return cnt2;
        if (!isLimit && dp[i][cnt2] >= 0) return dp[i][cnt2];
        var res = 0;
        for (int d = 0, up = isLimit ? s[i] - '0' : 9; d <= up; ++d) // 枚舉要填入的數字 d
            res += f(i + 1, cnt2 + (d == 2 ? 1 : 0), isLimit && d == up);
        if (!isLimit) dp[i][cnt2] = res;
        return res;
    }


}
