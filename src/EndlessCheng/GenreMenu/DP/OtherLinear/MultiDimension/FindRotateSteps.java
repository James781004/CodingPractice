package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class FindRotateSteps {

    // https://leetcode.cn/problems/freedom-trail/solutions/2623534/onm-zuo-fa-cong-ji-yi-hua-sou-suo-dao-di-dnec/
    private char[] s, t;
    private int[][] left, right, memo;

    public int findRotateSteps(String S, String T) {
        s = S.toCharArray();
        t = T.toCharArray();
        int n = s.length;
        int m = t.length;

        // 先算出每個字母的最後一次出現的下標
        // 由於 s 是環形的，循環結束後的 pos 就剛好是 left[0]
        int[] pos = new int[26]; // 初始值不重要
        for (int i = 0; i < n; i++) {
            s[i] -= 'a';
            pos[s[i]] = i;
        }
        // 計算每個 s[i] 左邊 a-z 的最近下標（左邊沒有就從 n-1 往左找）
        left = new int[n][26];
        for (int i = 0; i < n; i++) {
            System.arraycopy(pos, 0, left[i], 0, 26);
            pos[s[i]] = i; // 更新下標
        }

        // 先算出每個字母的首次出現的下標
        // 由於 s 是環形的，循環結束後的 pos 就剛好是 right[n-1]
        for (int i = n - 1; i >= 0; i--) {
            pos[s[i]] = i;
        }
        // 計算每個 s[i] 右邊 a-z 的最近下標（左邊沒有就從 0 往右找）
        right = new int[n][26];
        for (int i = n - 1; i >= 0; i--) {
            System.arraycopy(pos, 0, right[i], 0, 26);
            pos[s[i]] = i; // 更新下標
        }

        memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(0, 0) + m;
    }

    private int dfs(int j, int i) {
        if (j == t.length) {
            return 0;
        }
        if (memo[j][i] != -1) { // 之前計算過
            return memo[j][i];
        }
        int c = t[j] - 'a';
        if (s[i] == c) { // 無需旋轉
            return memo[j][i] = dfs(j + 1, i);
        }
        // 左邊最近 or 右邊最近，取最小值
        int l = left[i][c], r = right[i][c];
        return memo[j][i] = Math.min(dfs(j + 1, l) + (l > i ? s.length - l + i : i - l),
                dfs(j + 1, r) + (r < i ? s.length - i + r : r - i));
    }

    public int findRotateStepsDP(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int n = s.length;
        int m = t.length;

        // 先算出每個字母的最後一次出現的下標
        // 由於 s 是環形的，循環結束後的 pos 就剛好是 left[0]
        int[] pos = new int[26]; // 初始值不重要
        for (int i = 0; i < n; i++) {
            s[i] -= 'a';
            pos[s[i]] = i;
        }
        // 計算每個 s[i] 左邊 a-z 的最近下標（左邊沒有就從 n-1 往左找）
        int[][] left = new int[n][26];
        for (int i = 0; i < n; i++) {
            System.arraycopy(pos, 0, left[i], 0, 26);
            pos[s[i]] = i; // 更新下標
        }

        // 先算出每個字母的首次出現的下標
        // 由於 s 是環形的，循環結束後的 pos 就剛好是 right[n-1]
        for (int i = n - 1; i >= 0; i--) {
            pos[s[i]] = i;
        }
        // 計算每個 s[i] 右邊 a-z 的最近下標（左邊沒有就從 0 往右找）
        int[][] right = new int[n][26];
        for (int i = n - 1; i >= 0; i--) {
            System.arraycopy(pos, 0, right[i], 0, 26);
            pos[s[i]] = i; // 更新下標
        }

        int[][] f = new int[m + 1][n];
        for (int j = m - 1; j >= 0; j--) {
            int c = t[j] - 'a';
            for (int i = 0; i < n; i++) {
                if (s[i] == c) { // 無需旋轉
                    f[j][i] = f[j + 1][i];
                } else { // 左邊最近 or 右邊最近，取最小值
                    int l = left[i][c], r = right[i][c];
                    f[j][i] = Math.min(f[j + 1][l] + (l > i ? n - l + i : i - l),
                            f[j + 1][r] + (r < i ? n - i + r : r - i));
                }
            }
        }
        return f[0][0] + m;
    }


}
