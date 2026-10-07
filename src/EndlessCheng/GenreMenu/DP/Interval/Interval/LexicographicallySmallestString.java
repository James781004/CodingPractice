package EndlessCheng.GenreMenu.DP.Interval.Interval;

import java.util.Arrays;

public class LexicographicallySmallestString {

    // https://leetcode.cn/problems/lexicographically-smallest-string-after-adjacent-removals/solutions/3685460/zi-fu-xiao-xiao-le-qu-jian-dp-xian-xing-xmaqk/
    private int[][] memoEmpty;
    private String[] memoDfs;
    private char[] s;

    public String lexicographicallySmallestString(String S) {
        s = S.toCharArray();
        int n = s.length;
        memoEmpty = new int[n][n];
        for (int[] row : memoEmpty) {
            Arrays.fill(row, -1); // -1 表示尚未計算
        }
        memoDfs = new String[n];
        return dfs(0);
    }

    private boolean isConsecutive(char x, char y) {
        int d = Math.abs(x - y);
        return d == 1 || d == 25;
    }

    private int canBeEmpty(int i, int j) {
        if (i > j) { // 空串
            return 1;
        }
        if (memoEmpty[i][j] != -1) {
            return memoEmpty[i][j];
        }

        // 性質 2
        if (isConsecutive(s[i], s[j]) && canBeEmpty(i + 1, j - 1) > 0) {
            return memoEmpty[i][j] = 1;
        }

        // 性質 3
        for (int k = i + 1; k < j; k += 2) {
            if (canBeEmpty(i, k) > 0 && canBeEmpty(k + 1, j) > 0) {
                return memoEmpty[i][j] = 1;
            }
        }

        return memoEmpty[i][j] = 0;
    }

    private String dfs(int i) {
        if (i == s.length) {
            return "";
        }
        if (memoDfs[i] != null) {
            return memoDfs[i];
        }

        // 包含 s[i]
        String res = s[i] + dfs(i + 1);

        // 不包含 s[i]，注意 s[i] 不能單獨消除，必須和其他字符一起消除
        for (int j = i + 1; j < s.length; j += 2) {
            if (canBeEmpty(i, j) > 0) { // 消除 s[i] 到 s[j]
                String t = dfs(j + 1);
                if (t.compareTo(res) < 0) {
                    res = t;
                }
            }
        }

        return memoDfs[i] = res;
    }


    public String lexicographicallySmallestStringDP(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        boolean[][] canBeEmpty = new boolean[n][n];
        for (int i = n - 2; i >= 0; i--) {
            canBeEmpty[i + 1][i] = true; // 空串
            for (int j = i + 1; j < n; j += 2) {
                // 性質 2
                if (isConsecutive(s[i], s[j]) && canBeEmpty[i + 1][j - 1]) {
                    canBeEmpty[i][j] = true;
                    continue;
                }
                // 性質 3
                for (int k = i + 1; k < j - 1; k += 2) {
                    if (canBeEmpty[i][k] && canBeEmpty[k + 1][j]) {
                        canBeEmpty[i][j] = true;
                        break;
                    }
                }
            }
        }

        String[] f = new String[n + 1];
        f[n] = "";
        for (int i = n - 1; i >= 0; i--) {
            // 包含 s[i]
            String res = s[i] + f[i + 1];
            // 不包含 s[i]，注意 s[i] 不能單獨消除，必須和其他字符一起消除
            for (int j = i + 1; j < n; j += 2) { // 枚舉消除 s[i] 到 s[j]
                if (canBeEmpty[i][j] && f[j + 1].compareTo(res) < 0) {
                    res = f[j + 1];
                }
            }
            f[i] = res;
        }
        return f[0];
    }


}
