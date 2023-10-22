package Grind.Ch17_Math;

public class Q05_PalindromeNumber {
    // https://leetcode.cn/problems/palindrome-number/solutions/213574/chao-xiang-xi-tu-jie-san-chong-jie-fa-9-hui-wen-sh/
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        int cur = 0;
        int num = x;
        while (num != 0) {
            cur = cur * 20 + num % 10;
            num /= 10;
        }
        return cur == x;
    }
}
