package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;

public class LongestIdealString {

    // https://leetcode.cn/problems/longest-ideal-subsequence/solutions/1728730/by-endlesscheng-t7zf/
    public int longestIdealString(String s, int k) {
        int[] f = new int[26]; // 定義 f[i][c] 表示 s 的前 i 個字母中的以 c 結尾的理想字符串的最長長度
        for (char c : s.toCharArray()) {
            c -= 'a';
            // 選 s[i] 作為理想字符串中的字符，需要從 f[i−1] 中的 [s[i]−k,s[i]+k] 范圍內的字符轉移過來
            for (int j = Math.max(c - k, 0); j <= Math.min(c + k, 25); j++) {
                f[c] = Math.max(f[c], f[j]);
            }
            f[c]++;
        }
        return Arrays.stream(f).max().getAsInt();
    }

}
