package LeetcodeMaster.String;

public class Q01_Reverse {
//    344.反轉字符串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0344.%E5%8F%8D%E8%BD%AC%E5%AD%97%E7%AC%A6%E4%B8%B2.md
//
//    編寫一個函數，其作用是將輸入的字符串反轉過來。輸入字符串以字符數組 char[] 的形式給出。
//
//    不要給另外的數組分配額外的空間，你必須原地修改輸入數組、使用 O(1) 的額外空間解決這一問題。
//
//    你可以假設數組中的所有字符都是 ASCII 碼表中的可打印字符。
//
//    示例 1：
//    輸入：["h","e","l","l","o"]
//    輸出：["o","l","l","e","h"]
//
//    示例 2：
//    輸入：["H","a","n","n","a","h"]
//    輸出：["h","a","n","n","a","H"]

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            s[left] ^= s[right];  // 構造 a ^ b 的結果，並放在 a 中
            s[right] ^= s[left];  // 將 a ^ b 這一結果再 ^ b ，存入b中，此時 b = a, a = a ^ b
            s[left] ^= s[right];  // a ^ b 的結果再 ^ a ，存入 a 中，此時 b = a, a = b 完成交換
            left++;
            right--;
        }
    }

}
