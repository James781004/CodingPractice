package Grind.Ch02_String;

import java.util.HashMap;
import java.util.Map;

public class Q05_MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : t.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);

        int left = 0, right = 0;
        int valid = 0;
        // 記錄最小覆蓋子串的起始索引及長度
        int start = 0, len = Integer.MAX_VALUE;

        while (right < s.length()) {
            // c 是將移入窗口的字符
            char c = s.charAt(right);
            // 右移窗口
            right++;
            // 進行窗口內數據的一系列更新
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c)))
                    valid++; // 重點：c 數目「正好一樣」時。有效字符才能+1，多了少了都不行，從這個時間點開始窗口開始滿足條件
            }

            // 判斷左側窗口是否要收縮
            while (valid == need.size()) {
                // 在這裡更新最小覆蓋子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d 是將移出窗口的字符
                char d = s.charAt(left);
                // 左移窗口
                left++;
                // 進行窗口內數據的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d)))
                        valid--; // c 數目「正好一樣」時。有效字符-1，因為從這個時間點開始窗口開始不滿足條件
                    window.put(d, window.get(d) - 1);
                }
            }
        }

        // 返回最小覆蓋子串
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }
}
