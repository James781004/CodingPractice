package 程序员代码面试指南.ch09;

public class Q22_PalindromeNumber {
//    CD73 判斷一個數是否是回文數
//    描述
//    定義回文數的概念如下：
//    如果一個非負數左右完全對應，則該數是回文數，例如：121,22等。
//    如果一個負數的絕對值左右完全對應，也是回文數，例如：-121,-22等。
//    給定一個32位整數num，判斷num是否是回文數。
//            [要求]
//    O(log10n)

    public static boolean isPalindrome(int n) {
        if (n == Integer.MIN_VALUE) {
            return false;
        }
        n = Math.abs(n);
        int help = 1;
        while (n / help >= 10) { // help 是用來讓n除help之後可以獲得最高位數的參數
            help *= 10; // 必須乘以十直到與n同位
        }
        while (n != 0) {
            if (n / help != n % 10) return false; // 比較最高位以及最低位的數字
            n = (n % help) / 10; // n除去最高位以及最低位
            help /= 100; // help也減兩位繼續比較
        }
        return true;
    }

    // 也可以對輸入數字不斷除以10取余來計算其翻轉的數字，然後比較二者是否相等
    private static int reverse(int num) {
        int reverseNum = 0;
        while (num > 0) {
            reverseNum = reverseNum * 10 + num % 10;
            num /= 10;
        }
        return reverseNum;
    }

    public static void main(String[] args) {
        int test = -10001;
        System.out.println(isPalindrome(test));

    }
}
