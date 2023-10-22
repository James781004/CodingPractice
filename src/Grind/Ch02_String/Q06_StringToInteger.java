package Grind.Ch02_String;

public class Q06_StringToInteger {
    // https://leetcode.com/problems/string-to-integer-atoi/
    public int myAtoi(String str) {
        int n = str.length();
        int i = 0;
        // 記錄正負號
        int sign = 1;
        // 用 long 避免 int 溢出
        long res = 0;
        // 跳過前導空格
        while (i < n && str.charAt(i) == ' ') {
            i++;
        }
        if (i == n) {
            return 0;
        }

        // 記錄符號位
        if (str.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if (str.charAt(i) == '+') {
            i++;
        }
        if (i == n) {
            return 0;
        }

        // 統計數字位
        while (i < n && '0' <= str.charAt(i) && str.charAt(i) <= '9') {
            res = res * 10 + str.charAt(i) - '0';
            if (res > Integer.MAX_VALUE) {
                break;
            }
            i++;
        }
        // 如果溢出，強轉成 int 就會和真實值不同
        if ((int) res != res) {
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        return (int) res * sign;
    }
}
