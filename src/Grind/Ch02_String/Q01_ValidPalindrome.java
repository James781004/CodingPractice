package Grind.Ch02_String;

public class Q01_ValidPalindrome {
    // https://leetcode.com/problems/valid-palindrome/
    public boolean isPalindrome(String s) {
        // 先把所有字符轉化成小寫，並過濾掉空格和標點這類字符
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }

        // 然後對剩下的這些目標字符執行雙指針算法，判斷回文串
        s = sb.toString();
        // 一左一右兩個指針相向而行
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }


    // in place
    public boolean isPalindrome2(String s) {
        if (s.isEmpty()) return true;
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left)))
                left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right)))
                right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right)))
                return false;
            left++;
            right--;
        }
        return true;
    }
}
