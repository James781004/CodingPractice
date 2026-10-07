package EndlessCheng.GenreMenu.DP.Linear.LCS.Advance;

public class NumOfSubsequences {

    // https://leetcode.cn/problems/maximum-number-of-subsequences-after-one-inserting/solutions/3734800/fu-yong-115-ti-dai-ma-qian-hou-zhui-fen-gtkqz/
    public long numOfSubsequences(String S) {
        char[] s = S.toCharArray();
        long extra = Math.max(Math.max(numDistinct(s, "CT"), numDistinct(s, "LC")), calcInsertC(s));
        return numDistinct(s, "LCT") + extra;
    }

    // 115. 不同的子序列
    private long numDistinct(char[] s, String T) {
        int n = s.length;
        int m = T.length();
        if (n < m) {
            return 0;
        }

        char[] t = T.toCharArray();
        long[] f = new long[m + 1];
        f[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = Math.min(i, m - 1); j >= Math.max(m - n + i, 0); j--) {
                if (s[i] == t[j]) {
                    f[j + 1] += f[j];
                }
            }
        }
        return f[m];
    }

    // 計算插入 C 額外產生的 LCT 子序列個數的最大值
    private long calcInsertC(char[] s) {
        int cntT = 0;
        for (char c : s) {
            if (c == 'T') {
                cntT++;
            }
        }

        long res = 0;
        int cntL = 0;
        for (char c : s) {
            if (c == 'T') {
                cntT--;
            }
            if (c == 'L') {
                cntL++;
            }
            res = Math.max(res, (long) cntL * cntT);
        }
        return res;
    }


}
