package LeetcodeMaster.DP;

public class Q42_LongestPalindrome {
//    5.最長回文子串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0005.%E6%9C%80%E9%95%BF%E5%9B%9E%E6%96%87%E5%AD%90%E4%B8%B2.md
//
//    給你一個字符串 s，找到 s 中最長的回文子串。
//
//    示例 1：
//
//    輸入：s = "babad"
//    輸出："bab"
//    解釋："aba" 同樣是符合題意的答案。
//    示例 2：
//
//    輸入：s = "cbbd"
//    輸出："bb"
//    示例 3：
//
//    輸入：s = "a"
//    輸出："a"
//    示例 4：
//
//    輸入：s = "ac"
//    輸出："a"


    // 動態規劃
    public String longestPalindromeDP(String s) {
        if (s.length() == 0 || s.length() == 1) return s;
        boolean[][] dp = new boolean[s.length()][s.length()];
        int maxlenth = 0;
        int left = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                }
                if (dp[i][j] && j - i + 1 > maxlenth) {
                    maxlenth = j - i + 1;
                    left = i;
                }
            }
        }
        return s.substring(left, maxlenth);
    }

    // 雙指針 動態規劃
    public String longestPalindromeDP2(String s) {
        if (s.length() == 0 || s.length() == 1) return s;
        int length = 1;
        int index = 0;
        boolean[][] palindrome = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            palindrome[i][i] = true;
        }

        for (int L = 2; L <= s.length(); L++) {
            for (int i = 0; i < s.length(); i++) {
                int j = i + L - 1;
                if (j >= s.length()) break;
                if (s.charAt(i) != s.charAt(j)) {
                    palindrome[i][j] = false;
                } else {
                    if (j - i < 3) {
                        palindrome[i][j] = true;
                    } else {
                        palindrome[i][j] = palindrome[i + 1][j - 1];
                    }
                }
                if (palindrome[i][j] && j - i + 1 > length) {
                    length = j - i + 1;
                    index = i;
                }
            }
        }
        return s.substring(index, index + length);
    }


    // 雙指針 中心擴散法
    public String longestPalindromeTwoPointers(String s) {
        String s1 = "";
        String s2 = "";
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            // 分兩種情況：即一個元素作為中心點，兩個元素作為中心點
            s1 = extend(s, i, i);  // 情況1
            res = s1.length() > res.length() ? s1 : res;
            s2 = extend(s, i, i + 1);  // 情況2
            res = s2.length() > res.length() ? s2 : res;
        }
        return res;
    }

    public String extend(String s, int start, int end) {
        String tmp = "";
        while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
            tmp = s.substring(start, end + 1);  // Java中substring是左閉右開的，所以要+1
            // 向兩邊擴散
            start--;
            end++;
        }
        return tmp;
    }
}
