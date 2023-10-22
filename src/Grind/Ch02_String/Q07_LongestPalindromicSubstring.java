package Grind.Ch02_String;

public class Q07_LongestPalindromicSubstring {
    // https://leetcode.com/problems/longest-palindromic-substring/
    public String longestPalindrome(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            // 以 s[i] 為中心的最長回文子串
            String s1 = palindrome(s, i, i);
            // 以 s[i] 和 s[i+1] 為中心的最長回文子串
            String s2 = palindrome(s, i, i + 1);
            // res = longest(res, s1, s2)
            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }
        return res;
    }

    String palindrome(String s, int l, int r) {
        // 防止索引越界
        while (l >= 0 && r < s.length()
                && s.charAt(l) == s.charAt(r)) {
            // 向兩邊展開
            l--;
            r++;
        }
        // 返回以 s[l] 和 s[r] 為中心的最長回文串
        return s.substring(l + 1, r);
    }
}
