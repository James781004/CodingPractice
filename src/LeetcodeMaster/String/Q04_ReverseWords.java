package LeetcodeMaster.String;

public class Q04_ReverseWords {
//    151.翻轉字符串里的單詞
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0151.%E7%BF%BB%E8%BD%AC%E5%AD%97%E7%AC%A6%E4%B8%B2%E9%87%8C%E7%9A%84%E5%8D%95%E8%AF%8D.md
//
//    給定一個字符串，逐個翻轉字符串中的每個單詞。
//
//    示例 1：
//    輸入: "the sky is blue"
//    輸出: "blue is sky the"
//
//    示例 2：
//    輸入: "  hello world!  "
//    輸出: "world! hello"
//    解釋: 輸入字符串可以在前面或者後面包含多余的空格，但是反轉後的字符不能包括。
//
//    示例 3：
//    輸入: "a good   example"
//    輸出: "example good a"
//    解釋: 如果兩個單詞間有多余的空格，將反轉後單詞間的空格減少到只含一個。

    public String reverseWords(String s) {
        // 1.去除首尾以及中間多余空格
        StringBuilder sb = removeSpace(s);
        // 2.反轉整個字符串
        reverseString(sb, 0, sb.length() - 1);
        // 3.反轉各個單詞
        reverseEachWord(sb);
        return sb.toString();
    }

    private StringBuilder removeSpace(String s) {
        int start = 0;
        int end = s.length() - 1;
        while (s.charAt(start) == ' ') start++;
        while (s.charAt(end) == ' ') end--;
        StringBuilder sb = new StringBuilder();
        while (start <= end) {
            char c = s.charAt(start);

            // 1. 如果c不是空格，直接加入sb
            // 2.如果c是空格:
            // 2-1. 當下sb最後一位不是空格也可以加入sb，因為c可以判定為單字間的空格
            // 2-2. 當下sb最後一位是空格，c就判定為多餘空格，直接跳過
            if (c != ' ' || sb.charAt(sb.length() - 1) != ' ') {
                sb.append(c);
            }
            start++;
        }
        return sb;
    }


    public void reverseString(StringBuilder sb, int start, int end) {
        while (start < end) {
            char temp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, temp);
            start++;
            end--;
        }
    }

    private void reverseEachWord(StringBuilder sb) {
        int start = 0;
        int end = 1;
        int n = sb.length();
        while (start < n) {
            while (end < n && sb.charAt(end) != ' ') {
                end++;
            }
            reverseString(sb, start, end - 1);
            start = end + 1; // end當下是在空格(或者最後一位字元)的位置，所以start移動end到下一位繼續循環
            end = start + 1; // end從start下一位開始
        }
    }

}
