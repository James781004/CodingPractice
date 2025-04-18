package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.HashMap;
import java.util.Map;

public class LengthOfLongestSubstring {

    // https://labuladong.online/algo/essential-technique/sliding-window-framework/
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();

        int left = 0, right = 0;
        // 記錄結果
        int res = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            // 進行窗口內數據的一系列更新
            window.put(c, window.getOrDefault(c, 0) + 1);
            // 判斷左側窗口是否要收縮
            while (window.get(c) > 1) {
                char d = s.charAt(left);
                left++;
                // 進行窗口內數據的一系列更新
                window.put(d, window.get(d) - 1);
            }
            // 在這裡更新答案
            res = Math.max(res, right - left);
        }
        return res;
    }

}
