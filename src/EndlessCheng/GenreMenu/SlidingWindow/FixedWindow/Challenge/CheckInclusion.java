package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Challenge;

import java.util.HashMap;
import java.util.Map;

public class CheckInclusion {

    // https://labuladong.online/algo/essential-technique/sliding-window-framework/
    // 判斷 s 中是否存在 t 的排列
    public boolean checkInclusion(String t, String s) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : t.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);

        int left = 0, right = 0;
        int valid = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            // 進行窗口內數據的一系列更新
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c)))
                    valid++;
            }

            // 判斷左側窗口是否要收縮
            while (right - left >= t.length()) {
                // 在這裡判斷是否找到了合法的子串
                if (valid == need.size()) return true;
                char d = s.charAt(left);
                left++;
                // 進行窗口內數據的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d)))
                        valid--;
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        // 未找到符合條件的子串
        return false;
    }

}
