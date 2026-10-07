package EndlessCheng.GenreMenu.DP.Digit.LegalSum;

import java.util.Arrays;

public class CountDigitOne {

    // https://leetcode.cn/problems/number-of-digit-one/solutions/1750339/by-endlesscheng-h9ua/
    public int countDigitOne(int n) {
        char[] s = Integer.toString(n).toCharArray();
        int m = s.length;
        int[][] memo = new int[m][m];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(0, 0, true, s, memo);
    }

    // 定義 dfs(i,cnt_1 ,isLimit) 表示在前 i 位有 cnt_1  個 1 的前提下，我們能構造出的數中的 1 的個數總和
    // 例如 n=9999，如果前三位我們都填了 1，
    // 那麼填到最後一位，此時 dfs 計算的就是 1110,1111,1112,…,1119 這 10 個數中一共有多少個 1（一共有 31 個 1）。
    // dfs 中的 isLimit 表示當前是否受到了 n 的約束（我們要構造的數字不能超過 n）。
    // 若為真，則第 i 位填入的數字至多為 n[i]，否則至多為 9，把這個上限記作 up。
    // 如果在受到約束的情況下填了數字 up，那麼後續填入的數字仍會受到 n 的約束。
    // 例如 n=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    private int dfs(int i, int cnt1, boolean isLimit, char[] s, int[][] memo) {
        if (i == s.length) {
            return cnt1;
        }
        if (!isLimit && memo[i][cnt1] >= 0) { // 之前計算過
            return memo[i][cnt1];
        }

        int up = isLimit ? s[i] - '0' : 9;
        int res = 0;
        for (int d = 0; d <= up; d++) { // 枚舉要填入的數字 d
            res += dfs(i + 1, cnt1 + (d == 1 ? 1 : 0), isLimit && d == up, s, memo);
        }

        if (!isLimit) {
            memo[i][cnt1] = res; // 記憶化
        }
        return res;
    }


}
