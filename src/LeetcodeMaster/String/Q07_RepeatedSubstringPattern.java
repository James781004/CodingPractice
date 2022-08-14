package LeetcodeMaster.String;

public class Q07_RepeatedSubstringPattern {
//    459.重覆的子字符串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0459.%E9%87%8D%E5%A4%8D%E7%9A%84%E5%AD%90%E5%AD%97%E7%AC%A6%E4%B8%B2.md
//
//    給定一個非空的字符串，判斷它是否可以由它的一個子串重覆多次構成。給定的字符串只含有小寫英文字母，並且長度不超過10000。
//
//    示例 1:
//    輸入: "abab"
//    輸出: True
//    解釋: 可由子字符串 "ab" 重覆兩次構成。
//
//    示例 2:
//    輸入: "aba"
//    輸出: False
//
//    示例 3:
//    輸入: "abcabcabcabc"
//    輸出: True
//    解釋: 可由子字符串 "abc" 重覆四次構成。 (或者子字符串 "abcabc" 重覆兩次構成。)

    public boolean repeatedSubstringPattern(String s) {
        if (s.equals("")) return false;

        int len = s.length();
        // 原串加個空格(哨兵)，使下標從1開始，這樣j從0開始，也不用初始化了
        s = " " + s;
        char[] chars = s.toCharArray();
        int[] next = new int[len + 1];

        // 構造 next 數組過程，j從0開始(空格)，i從2開始
        for (int i = 2, j = 0; i <= len; i++) {
            // 匹配不成功，j回到前一位置 next 數組所對應的值
            while (j > 0 && chars[i] != chars[j + 1]) j = next[j];
            // 匹配成功，j往後移
            if (chars[i] == chars[j + 1]) j++;
            // 更新 next 數組的值
            next[i] = j;
        }

        // 最後判斷是否是重覆的子字符串，這里 next[len] 即代表next數組末尾的值
        if (next[len] > 0 && len % (len - next[len]) == 0) {
            return true;
        }
        return false;
    }

    // 比較快的解法
    public boolean repeatedSubstringPattern2(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.append(s);
        return sb.toString().substring(1, sb.length()).contains(s);
    }
}
