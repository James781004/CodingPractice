package LeetcodeMaster.HashMap;

import java.util.ArrayList;
import java.util.List;

public class Q02_CommonChars {
//1002. 查找常用字符
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1002.%E6%9F%A5%E6%89%BE%E5%B8%B8%E7%94%A8%E5%AD%97%E7%AC%A6.md
//
//    給你一個字符串數組 words ，請你找出所有在 words 的每個字符串中都出現的共用字符（ 包括重覆字符），並以數組形式返回。你可以按 任意順序 返回答案。
//
//    示例 1：
//
//    輸入：words = ["bella","label","roller"] 輸出：["e","l","l"] 示例 2：
//
//    輸入：words = ["cool","lock","cook"] 輸出：["c","o"]
//
//    提示： 1 <= words.length <= 100 1 <= words[i].length <= 100 words[i] 由小寫英文字母組成

    public List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<>();
        if (A.length == 0) return result;
        int[] hash = new int[26]; // 用來統計所有字符串里字符出現的最小頻率
        for (int i = 0; i < A[0].length(); i++) {
            hash[A[0].charAt(i) - 'a']++; // 用第一個字符串給hash初始化
        }

        // 統計除第一個字符串外字符的出現頻率
        for (int i = 0; i < A.length; i++) {
            int[] hashOtherStr = new int[26];
            for (int j = 0; j < A[i].length(); j++) {
                hashOtherStr[A[i].charAt(j) - 'a']++;
            }

            // 更新hash，保證hash里統計26個字符在所有字符串里出現的最小次數
            for (int k = 0; k < 26; k++) {
                hash[k] = Math.min(hash[k], hashOtherStr[k]);
            }
        }

        // 將hash統計的字符次數，轉成輸出形式
        for (int i = 0; i < 26; i++) {
            while (hash[i] != 0) { // 注意這里是while，多個重覆的字符
                char c = (char) (i + 'a');
                result.add(String.valueOf(c));
                hash[i]--;
            }
        }
        return result;
    }

}
