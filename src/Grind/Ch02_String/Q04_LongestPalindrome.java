package Grind.Ch02_String;

import java.util.HashMap;
import java.util.Map;

public class Q04_LongestPalindrome {
    // https://leetcode.cn/problems/longest-palindrome/solutions/1693273/409-zui-chang-hui-wen-chuan-by-jyd-ne80/
    public int longestPalindrome(String s) {
        // 統計各字符數量
        Map<Character, Integer> counter = new HashMap<>();
        for (int i = 0; i < s.length(); i++)
            counter.merge(s.charAt(i), 1, (a, b) -> a + b);
        // 統計構造回文串的最大長度
        int res = 0, odd = 0;
        for (Map.Entry<Character, Integer> kv : counter.entrySet()) {
            // 將當前字符出現次數向下取偶數，並計入 res
            int count = kv.getValue();
            int rem = count % 2; // 如果 count 是奇數，rem就會得 1，反之得 0
            res += count - rem;  // 所以 count 這邊會減去 1 或 0，總之 res 會得到偶數或 0
            if (rem == 1) odd = 1;  // 若當前字符出現次數為奇數，則將 odd 置 1
        }
        return res + odd;
    }
}
