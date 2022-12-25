package GuChengAlgorithm.Ch02_BasicAlgorithm.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class Q00_SlidingWindowModel {
    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.gb533e9b64a_0_30
    public int SlidingWindowModel(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            map.put(cur, map.getOrDefault(cur, 0) + 1);  // 1. 進：當前遍歷的i進入窗口
            while (map.size() > k) {    // 2. 出：窗口不合條件時，left持續退出窗口
                char c = s.charAt(left);
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) map.remove(c);
                left++;
            }
            res = Math.max(res, i - left + 1);  // 3. 算：窗口valid，開始計算結果
        }
        return res;
    }
}
