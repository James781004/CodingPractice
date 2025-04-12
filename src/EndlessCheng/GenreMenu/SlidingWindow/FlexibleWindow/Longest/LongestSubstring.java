package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.HashMap;

public class LongestSubstring {

    // https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters/solutions/623991/jie-ben-ti-bang-zhu-da-jia-li-jie-di-gui-obla/
    public int longestSubstring(String s, int k) {
        if (s.length() < k) return 0;
        HashMap<Character, Integer> counter = new HashMap();
        for (int i = 0; i < s.length(); i++) {
            counter.put(s.charAt(i), counter.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (char c : counter.keySet()) {
            // 如果一個字符 c 在 s 中出現的次數少於 k 次，那麼 s 中所有的包含 c 的子字符串都不能滿足題意。
            // 所以應該在 s 的所有不包含 c 的子字符串中繼續尋找結果：把 s 按照 c 分割（分割後每個子串都不包含 c），得到很多子字符串 t
            // 下一步要求 t 作為源字符串的時候，它的最長的滿足題意的子字符串長度
            if (counter.get(c) < k) {
                int res = 0;
                for (String t : s.split(String.valueOf(c))) {
                    res = Math.max(res, longestSubstring(t, k));
                }
                return res;
            }
        }

        // 如果 s 中的每個字符出現的次數都大於 k 次，那麼 s 就是我們要求的字符串，直接返回該字符串的長度
        return s.length();
    }


    // https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters/solutions/623432/zhi-shao-you-kge-zhong-fu-zi-fu-de-zui-c-o6ww/
    // 滑動窗口
    public int longestSubstring2(String s, int k) {
        int ret = 0;
        int n = s.length();
        for (int t = 1; t <= 26; t++) {
            int l = 0, r = 0;
            int[] cnt = new int[26];
            int tot = 0;
            int less = 0;
            while (r < n) {
                cnt[s.charAt(r) - 'a']++;
                if (cnt[s.charAt(r) - 'a'] == 1) {
                    tot++;
                    less++;
                }
                if (cnt[s.charAt(r) - 'a'] == k) {
                    less--;
                }

                while (tot > t) {
                    cnt[s.charAt(l) - 'a']--;
                    if (cnt[s.charAt(l) - 'a'] == k - 1) {
                        less++;
                    }
                    if (cnt[s.charAt(l) - 'a'] == 0) {
                        tot--;
                        less--;
                    }
                    l++;
                }
                if (less == 0) {
                    ret = Math.max(ret, r - l + 1);
                }
                r++;
            }
        }
        return ret;
    }


}
