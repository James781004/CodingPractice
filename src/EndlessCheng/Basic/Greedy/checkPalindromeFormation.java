package EndlessCheng.Basic.Greedy;

public class checkPalindromeFormation {

    // https://leetcode.cn/problems/split-two-strings-to-make-palindrome/solutions/2175393/mei-xiang-ming-bai-yi-zhang-tu-miao-dong-imvy/
    public boolean checkPalindromeFormation(String a, String b) {
        return check(a, b) || check(b, a);
    }

    // 如果 a_prefix + b_suffix 可以構成回文串則返回 true，否則返回 false
    private boolean check(String a, String b) {
        int i = 0, j = a.length() - 1; // 相向雙指針
        while (i < j && a.charAt(i) == b.charAt(j)) { // 前後綴盡量匹配
            ++i;
            --j;
        }
        return isPalindrome(a, i, j) || isPalindrome(b, i, j);
    }

    // 如果從 s[i] 到 s[j] 是回文串則返回 true，否則返回 false
    private boolean isPalindrome(String s, int i, int j) {
        while (i < j && s.charAt(i) == s.charAt(j)) {
            ++i;
            --j;
        }
        return i >= j;
    }


}
