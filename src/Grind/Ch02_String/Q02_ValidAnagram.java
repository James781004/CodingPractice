package Grind.Ch02_String;

public class Q02_ValidAnagram {
    // https://leetcode.com/problems/valid-anagram/
    public boolean isAnagram(String s, String t) {
        int[] count1 = encode(s);
        int[] count2 = encode(t);
        // 確保兩個字符串中所有字符出現的數量相同
        for (int i = 0; i < count1.length; i++) {
            if (count1[i] != count2[i]) {
                return false;
            }
        }

        return true;
    }

    // 計算字符的出現次數
    int[] encode(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            int delta = c - 'a';
            count[delta]++;
        }
        return count;
    }


    // 剪枝加速
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] arr = new int[26];
        for (char c : s.toCharArray())
            ++arr[c - 'a'];
        for (char c : t.toCharArray()) {
            --arr[c - 'a'];
            if (arr[c - 'a'] < 0) return false;
        }
        return true;
    }
}
