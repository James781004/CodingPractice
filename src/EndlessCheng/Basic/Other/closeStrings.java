package EndlessCheng.Basic.Other;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class closeStrings {

    // https://leetcode.cn/problems/determine-if-two-strings-are-close/solutions/2547579/li-jie-cao-zuo-ben-zhi-jian-ji-xie-fa-py-b18i/
    public boolean closeStrings(String s, String t) {

        // 判斷 s 和 t 的長度是否一樣，如果不一樣直接返回 false
        if (s.length() != t.length()) {
            return false;
        }

        // 判斷 s 和 t 的字符集合是否一樣，如果不一樣直接返回 false。
        // 例如 s 中有字符 abc，t 中有字符 def，無論如何都不能把 s 變成 t
        int[] sCnt = new int[26];
        for (byte c : s.getBytes(ISO_8859_1)) {
            sCnt[c - 'a']++;
        }

        int[] tCnt = new int[26];
        for (byte c : t.getBytes(ISO_8859_1)) {
            tCnt[c - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if ((sCnt[i] == 0) != (tCnt[i] == 0)) {
                return false;
            }
        }

        // 判斷 s 的字符出現次數的集合，是否等於 t 的字符出現次數的集合，等於返回 true，不等於返回 false。
        // 注意集合可以有相同元素，比如 aabbbccc 對應的集合就是 {2,3,3}
        Arrays.sort(sCnt);
        Arrays.sort(tCnt);
        return Arrays.equals(sCnt, tCnt);
    }


}
