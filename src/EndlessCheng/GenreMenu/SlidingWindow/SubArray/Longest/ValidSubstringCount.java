package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Longest;

public class ValidSubstringCount {

    // https://leetcode.cn/problems/count-substrings-that-can-be-rearranged-to-contain-a-string-ii/solutions/2925828/on-hua-dong-chuang-kou-qiu-ge-shu-python-0x7a/
    // 由於子串可以重排，只要子串可以涵蓋字符串 t，那麼子串就可以通過重排，使得 t 是子串的前綴
    public long validSubstringCount(String S, String T) {
        if (S.length() < T.length()) {
            return 0;
        }

        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[] cnt = new int[26]; // t 的字母出現次數與 s 的字母出現次數之差
        int less = 0; // 統計窗口內有多少個字母的出現次數比 t 的少

        for (char b : t) {
            int c = b - 'a';
            if (cnt[c] == 0) less++;
            cnt[c]++;
        }

        long ans = 0;
        int left = 0;
        for (char b : s) {
            cnt[b - 'a']--;
            if (cnt[b - 'a'] == 0) {
                // b 移入窗口後，窗口內 b 的出現次數和 t 一樣
                less--;
            }
            while (less == 0) { // 窗口符合要求
                char outChar = s[left++]; // 准備移出窗口的字母
                if (cnt[outChar - 'a'] == 0) {
                    // outChar 移出窗口之前檢查出現次數，
                    // 如果窗口內 outChar 的出現次數和 t 一樣，
                    // 那麼 outChar 移出窗口後，窗口內 outChar 的出現次數比 t 的少
                    less++;
                }
                cnt[outChar - 'a']++;
            }
            ans += left;
        }
        return ans;
    }


}
