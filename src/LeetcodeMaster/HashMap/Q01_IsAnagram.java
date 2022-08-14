package LeetcodeMaster.HashMap;

public class Q01_IsAnagram {
//    242.有效的字母異位詞
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0242.%E6%9C%89%E6%95%88%E7%9A%84%E5%AD%97%E6%AF%8D%E5%BC%82%E4%BD%8D%E8%AF%8D.md
//
//    給定兩個字符串 s 和 t ，編寫一個函數來判斷 t 是否是 s 的字母異位詞。
//    示例 1: 輸入: s = "anagram", t = "nagaram" 輸出: true
//    示例 2: 輸入: s = "rat", t = "car" 輸出: false
//    說明: 你可以假設字符串只包含小寫字母。

    public boolean isAnagram(String s, String t) {
        int[] dict = new int[26];
        for (char c : s.toCharArray()) {
            dict[c - 'a']++; // 並不需要記住字符a的ASCII，只要求出一個相對數值就可以了
        }

        for (char c : t.toCharArray()) {
            dict[c - 'a']--;
            if (dict[c - 'a'] < 0) return false;  // dict數組如果有的元素不為0，說明字符串s和t 一定是誰多了字符或者誰少了字符。
        }

        return true;
    }

}
