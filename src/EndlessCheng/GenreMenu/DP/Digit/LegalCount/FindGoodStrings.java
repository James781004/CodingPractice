package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class FindGoodStrings {

    // https://leetcode.cn/problems/find-all-good-strings/solutions/186424/zhao-dao-suo-you-hao-zi-fu-chuan-by-leetcode-solut/
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        cs1 = s1.toCharArray();
        cs2 = s2.toCharArray();
        this.evil = evil.toCharArray();
        this.n = n;
        memo = new long[n][evil.length()];
        for (var arr : memo) Arrays.fill(arr, -1);
        next = getNext(evil);
        return (int) f(0, 0, true, true);
    }

    static long MOD = (long) (1e9 + 7);
    int n;
    char[] cs1;
    char[] cs2;
    char[] evil;
    int[] next;
    long[][] memo;

    int[] getNext(String s) {
        int[] next = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            int j = next[i - 1];//i的前一個字符匹配長度
            while (j > 0 && s.charAt(i) != s.charAt(j)) j = next[j - 1]; //第j個字符不匹配時, 回退到j的前一個字符匹配的長度
            if (s.charAt(i) == s.charAt(j)) j++;
            next[i] = j;
        }
        return next;
    }

    /**
     * i:              原串匹配的位置
     * ei:             模式串待匹配的位置
     * up_limited:     前i-1個字符是否取到了上限
     * down_limited:   前i-1個字符是否取到了下限
     */
    long f(int i, int j, boolean up_limited, boolean down_limited) {
        if (i >= n) return 1;
        if (!up_limited && !down_limited && memo[i][j] != -1) return memo[i][j];
        long cnt = 0;
        char down = down_limited ? cs1[i] : 'a', up = up_limited ? cs2[i] : 'z';
        for (char c = down; c <= up; c++) {
            //kmp match core code.
            int nj = j; //待匹配的字符
            while (nj > 0 && evil[nj] != c) nj = next[nj - 1];
            if (evil[nj] == c) nj++;
            if (nj >= evil.length) continue; //包含模式串了
            cnt += f(i + 1, nj, up_limited && c == up, down_limited && down == c);
            cnt %= MOD;
        }
        if (!up_limited && !down_limited) memo[i][j] = cnt;
        return cnt;
    }


}
