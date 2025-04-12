package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class ExpressiveWords {

    // https://leetcode.cn/problems/expressive-words/solutions/573200/809bi-jiao-mo-ban-miao-sha-zi-fu-chuan-b-2n0g/
    public int expressiveWords(String S, String[] words) {
        if (S == null || S.length() == 0 || words == null || words.length == 0) return 0;
        int cnt = 0;
        for (String word : words) {
            if (isStrechy(S, word)) cnt++;
        }
        return cnt;
    }

    // 判斷 t 是否可以擴張成 s
    private boolean isStrechy(String s, String t) {
        int n = s.length(), m = t.length();
        if (n < m) return false;
        int i = 0, j = 0;
        while (i < n && j < m) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(j);  // 優化：可以在這之後直接判斷字符是否相等。

            // c1幾個
            int cnt1 = 0;
            while (i < n && s.charAt(i) == c1) {
                i++;
                cnt1++;
            }

            // c2幾個
            int cnt2 = 0;
            while (j < m && t.charAt(j) == c2) {
                j++;
                cnt2++;
            }
            /** 3 種無法擴張的情況
             1. c1, c2 不同
             2. c2, c2 長度不同，而且 c1 長度只有 2，無法被擴張
             3. c2 長度 > c1 長度
             **/
            if (c1 != c2 || cnt1 < cnt2 || cnt1 <= 2 && cnt1 != cnt2) return false;

        }
        // 判斷 s, t 都走完了嗎？
        return i == s.length() && j == t.length();
    }


}
