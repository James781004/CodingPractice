package LeetcodeMaster.String;

public class Q06_KMP {
//    28. 實現 strStr()
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0028.%E5%AE%9E%E7%8E%B0strStr.md
//
//    實現 strStr() 函數。
//
//    給定一個 haystack 字符串和一個 needle 字符串，在 haystack 字符串中找出 needle 字符串出現的第一個位置 (從0開始)。如果不存在，則返回  -1。
//
//    示例 1: 輸入: haystack = "hello", needle = "ll" 輸出: 2
//
//    示例 2: 輸入: haystack = "aaaaa", needle = "bba" 輸出: -1
//
//    說明: 當 needle 是空字符串時，我們應當返回什麼值呢？這是一個在面試中很好的問題。 對於本題而言，當 needle 是空字符串時我們應當返回 0 。這與C語言的 strstr() 以及 Java的 indexOf() 定義相符。


    // Two Pointer
    public int strStrTwoPointer(String haystack, String needle) {
        int m = needle.length();
        if (m == 0) return 0;
        int n = haystack.length();
        if (n < m) return -1;

        int i = 0, j = 0;
        while (i < n - m + 1) {
            // 找到首字母相等
            while (i < n && haystack.charAt(i) != needle.charAt(j)) {
                i++;
            }

            if (i == n) { // 沒有首字母相等的
                return -1;
            }

            // 遍歷後續字符，判斷是否相等
            i++;
            j++;
            while (i < n && j < m && haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            if (j == m) {// 找到
                return i - j;
            } else { // 未找到
                i -= j - 1;
                j = 0;
            }
        }
        return -1;
    }

    // 前綴表（統一減一）Java實現
    public int strStr1(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }

        int[] next = new int[needle.length()];
        getNext1(next, needle);
        int j = -1; // // 因為next數組里記錄的起始位置為-1
        for (int i = 0; i < haystack.length(); i++) { // 注意i就從0開始
            while (j >= 0 && haystack.charAt(i) != needle.charAt(j + 1)) {
                j = next[j]; // 不匹配，j尋找之前匹配的位置
            }

            if (haystack.charAt(i) == needle.charAt(j + 1)) {
                j++; // 匹配，j和i同時向後移動 (i的移動在for循環里)
            }

            if (j == needle.length() - 1) {  // 文本串s里出現了模式串t
                return (i - needle.length() + 1);
            }
        }

        return -1;
    }

    private void getNext1(int[] next, String needle) {
        int j = -1;
        next[0] = j;
        for (int i = 1; i < needle.length(); i++) { // 注意i從1開始
            while (j >= 0 && needle.charAt(i) != needle.charAt(j + 1)) {
                j = next[j]; // 前後綴不相同了，向前回退
            }

            if (needle.charAt(i) == needle.charAt(j + 1)) {
                j++; // 找到相同的前後綴，j++
            }
            next[i] = j; // 將j（前綴的長度）賦給next[i]
        }
    }


    // 前綴表（不減一）Java實現
    public int strStr2(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        int[] next = new int[needle.length()];
        getNext2(next, needle);

        int j = 0;
        for (int i = 0; i < haystack.length(); i++) {
            while (j > 0 && needle.charAt(j) != haystack.charAt(i))   // j要保證大於0，因為下面有取j-1作為數組下標的操作
                j = next[j - 1];  // 注意這里，是要找前一位的對應的回退位置了
            if (needle.charAt(j) == haystack.charAt(i))
                j++;
            if (j == needle.length())
                return i - needle.length() + 1;
        }
        return -1;
    }

    private void getNext2(int[] next, String needle) {
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < needle.length(); i++) {
            while (j > 0 && needle.charAt(j) != needle.charAt(i))   // j要保證大於0，因為下面有取j-1作為數組下標的操作
                j = next[j - 1];  // 注意這里，是要找前一位的對應的回退位置了
            if (needle.charAt(j) == needle.charAt(i))
                j++;
            next[i] = j; // 將j（前綴的長度）賦給next[i]
        }
    }
}
