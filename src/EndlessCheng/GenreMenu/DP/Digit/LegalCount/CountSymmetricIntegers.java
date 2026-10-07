package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountSymmetricIntegers {

    // https://leetcode.cn/problems/count-symmetric-integers/solutions/2424088/mei-ju-by-endlesscheng-oo2d/
    private char[] lowS, highS;
    private int n, m, diffLh;
    private int[][][] memo;

    public int countSymmetricIntegers(int low, int high) {
        lowS = String.valueOf(low).toCharArray();
        highS = String.valueOf(high).toCharArray();
        n = highS.length;
        diffLh = n - lowS.length;

        // dfs 中的 start <= diffLh，左半元素和 <= floor(n/2) * 9
        memo = new int[n][diffLh + 1][n / 2 * 9 + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        return dfs(0, -1, 0, true, true);
    }

    // dfs(i,start,diff,limitLow,limitHigh) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // start 表示從左往右第一個非零數字的下標。用於判斷現在處於對稱整數的左半還是右半。
    // diff 表示左半數字與右半數字之差。如果最終 diff=0，則說明我們成功構造出一個對稱整數。
    // limitHigh 表示當前是否受到了 high 的約束（我們要構造的數字不能超過 high）。
    // 若為真，則第 i 位填入的數字至多為 high[i]，否則至多為 9，這個數記作 hi。
    // 如果在受到約束的情況下填了 hi，那麼後續填入的數字仍會受到 high 的約束。
    // 例如 high=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    // limitLow 表示當前是否受到了 low 的約束（我們要構造的數字不能低於 low）。
    // 若為真，則第 i 位填入的數字至少為 low[i]，否則至少為 0，這個數記作 lo。
    // 如果在受到約束的情況下填了 lo，那麼後續填入的數字仍會受到 low 的約束。
    private int dfs(int i, int start, int diff, boolean limitLow, boolean limitHigh) {
        if (diff < 0) {
            return 0;
        }
        if (i == n) {
            return diff == 0 ? 1 : 0;
        }

        // start 當 isNum 用
        if (start != -1 && !limitLow && !limitHigh && memo[i][start][diff] != -1) {
            return memo[i][start][diff];
        }

        int lo = limitLow && i >= diffLh ? lowS[i - diffLh] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;

        // 如果前面沒有填數字，且剩余數位個數是奇數，那麼當前數位不能填數字
        if (start < 0 && (n - i) % 2 > 0) {
            // 如果必須填數字（lo > 0），不合法，返回 0
            return lo > 0 ? 0 : dfs(i + 1, start, diff, true, false);
        }

        int res = 0;
        boolean isLeft = start < 0 || i < (start + n) / 2;

        // 枚舉第 i 位填數字 d=lo,lo+1,…,hi
        for (int d = lo; d <= hi; d++) {
            // 如果之前沒有填過數字，且當前填的數字大於 0，那麼記錄 start=i
            // isLeft: 如果 i < (start + n) / 2，說明在左半，把 diff 增加 d，否則把 diff 減少 d
            res += dfs(i + 1,
                    start < 0 && d > 0 ? i : start, // 記錄第一個填數字的位置
                    diff + (isLeft ? d : -d), // 左半 +，右半 -
                    limitLow && d == lo,
                    limitHigh && d == hi);
        }

        if (start != -1 && !limitLow && !limitHigh) {
            memo[i][start][diff] = res;
        }
        return res;
    }


}
