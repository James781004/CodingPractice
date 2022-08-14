package LeetcodeMaster.TwoPointer;

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

    public void rotateWord(char[] chas) {
        if (chas == null || chas.length == 0) {
            return;
        }

        // 1. 首先整體翻轉一次
        reverse(chas, 0, chas.length - 1);

        // 2. 之後每個單詞各自翻轉
        int left = -1;
        int right = -1;
        for (int i = 0; i < chas.length; i++) {
            if (chas[i] != ' ') {

                // 0位置或者前一位是空格，紀錄位置為左位
                left = (i == 0 || chas[i - 1] == ' ') ? i : left;

                // 尾部位置或者後一位是空格，紀錄位置為右位
                right = (i == chas.length - 1 || chas[i + 1] == ' ') ? i : right;
            }

            // 左右都不是-1時進行翻轉，之後重置左右位-1
            if (left != -1 && right != -1) {
                reverse(chas, left, right);
                left = -1;
                right = -1;
            }
        }
    }

    private void reverse(char[] chas, int start, int end) {
        char tmp;
        while (start < end) {
            tmp = chas[start];
            chas[start] = chas[end];
            chas[end] = tmp;
            start++;
            end--;
        }
    }

}
