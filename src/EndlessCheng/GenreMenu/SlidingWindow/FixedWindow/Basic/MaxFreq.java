package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaxFreq {

    // https://leetcode.cn/problems/maximum-number-of-occurrences-of-a-substring/solutions/59781/gu-ding-chuang-kou-da-xiao-de-hua-dong-chuang-kou-/
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        char[] chars = s.toCharArray();
        Map<String, Integer> map = new HashMap<>();
        // 固定窗口大小的滑動窗口，枚舉每個左邊界的起始位置，那麼右邊界位置等於 i + minSize - 1
        for (int i = 0; i <= chars.length - minSize; i++) {
            if (counter(chars, i, i + minSize - 1) <= maxLetters) {
                String key = String.valueOf(chars, i, minSize);
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }
        int max = 0;
        for (Integer value : map.values()) {
            max = Math.max(max, value);
        }
        return max;
    }

    // 用來統計不同元素的個數
    private int counter(char[] chars, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            set.add(chars[i]);
        }
        return set.size();
    }


}
