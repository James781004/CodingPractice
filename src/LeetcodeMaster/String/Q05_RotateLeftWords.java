package LeetcodeMaster.String;

public class Q05_RotateLeftWords {
//    題目：劍指Offer58-II.左旋轉字符串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E5%89%91%E6%8C%87Offer58-II.%E5%B7%A6%E6%97%8B%E8%BD%AC%E5%AD%97%E7%AC%A6%E4%B8%B2.md
//
//    字符串的左旋轉操作是把字符串前面的若幹個字符轉移到字符串的尾部。請定義一個函數實現字符串左旋轉操作的功能。比如，輸入字符串"abcdefg"和數字2，該函數將返回左旋轉兩位得到的結果"cdefgab"。
//
//    示例 1：
//    輸入: s = "abcdefg", k = 2
//    輸出: "cdefgab"
//
//    示例 2：
//    輸入: s = "lrloseumgh", k = 6
//    輸出: "umghlrlose"
//
//    限制： 1 <= k < s.length <= 10000


    public String reverseLeftWords(String s, int n) {
        int len = s.length();
        StringBuilder sb = new StringBuilder(s);

        // 1. 反轉區間為前n的子串
        reverseString(sb, 0, n - 1);

        // 2. 反轉區間為n到末尾的子串
        reverseString(sb, n, len - 1);

        // 3.反轉整個字符串
        return sb.reverse().toString();
    }

    private void reverseString(StringBuilder sb, int start, int end) {
        while (start < end) {
            char temp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, temp);
            start++;
            end--;
        }
    }


    public String reverseLeftWords2(String s, int n) {
        char[] chas = s.toCharArray();
        rotate(chas, n);
        return new String(chas);
    }


    public void rotate(char[] chas, int size) {
        if (chas == null || size <= 0 || size >= chas.length) {
            return;
        }

        // 1. 首先翻轉指定長度左半部
        reverse(chas, 0, size - 1);

        // 2. 再來翻轉剩餘長度右半部
        reverse(chas, size, chas.length - 1);

        // 3. 最後翻轉整體字串
        reverse(chas, 0, chas.length - 1);
    }


    public void reverse(char[] chas, int start, int end) {
        char tmp = 0;
        while (start < end) {
            tmp = chas[start];
            chas[start] = chas[end];
            chas[end] = tmp;
            start++;
            end--;
        }
    }
}
