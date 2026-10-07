package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountBalls {

    // https://leetcode.cn/problems/maximum-number-of-balls-in-a-box/solutions/3073112/san-chong-fang-fa-bao-li-mei-ju-qian-zhu-kze9/
    public int countBalls(int lowLimit, int highLimit) {
        String highS = String.valueOf(highLimit);
        int n = highS.length(); // highLimit 的十進制長度
        String lowS = String.valueOf(lowLimit);
        lowS = "0".repeat(n - lowS.length()) + lowS; // 補前導零，和 highS 對齊， 使得可共用 memo

        int m = highS.charAt(0) - '0' + (n - 1) * 9; // 數位和的上界
        int[][] memo = new int[n][m + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        int ans = 0;
        for (int j = 1; j <= m; j++) {
            // 遞歸入口：dfs(0,j,true,true)，表示：
            // 從最高位開始枚舉。
            // 一開始要受到 lowLimit 和 highLimit 的約束（否則就可以隨意填了，這肯定不行）。
            ans = Math.max(ans, dfs(0, j, true, true, lowS.toCharArray(), highS.toCharArray(), memo));
        }
        return ans;
    }

    // dfs(i,j,limitLow,limitHigh) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // j 表示剩余數字的數位和必須恰好等於 j。
    // limitHigh 表示當前是否受到了 limitHigh 的約束（我們要構造的數字不能超過 limitHigh）。
    // 若為真，則第 i 位填入的數字至多為 limitHigh[i]，否則至多為 9，這個數記作 hi。
    // 如果在受到約束的情況下填了 limitHigh[i]，那麼後續填入的數字仍會受到 limitHigh 的約束。
    // 例如 limitHigh=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    // limitLow 表示當前是否受到了 limitLow 的約束（我們要構造的數字不能低於 limitLow）。
    // 若為真，則第 i 位填入的數字至少為 limitLow[i]，否則至少為 0，這個數記作 lo。
    // 如果在受到約束的情況下填了 limitLow[i]，那麼後續填入的數字仍會受到 limitLow 的約束。
    private int dfs(int i, int j, boolean limitLow, boolean limitHigh, char[] lowS, char[] highS, int[][] memo) {
        if (i == highS.length) { // 填完了
            return j == 0 ? 1 : 0;
        }
        if (!limitLow && !limitHigh && memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        int lo = limitLow ? lowS[i] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;

        int res = 0;
        for (int d = lo; d <= Math.min(hi, j); d++) { // 枚舉當前數位填 d，但不能超過 j
            res += dfs(i + 1, j - d, limitLow && d == lo, limitHigh && d == hi, lowS, highS, memo);
        }

        if (!limitLow && !limitHigh) {
            memo[i][j] = res; // 記憶化
        }
        return res;
    }


}
