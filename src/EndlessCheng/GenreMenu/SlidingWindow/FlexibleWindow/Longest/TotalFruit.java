package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.HashMap;
import java.util.Map;

public class TotalFruit {

    // https://leetcode.cn/problems/fruit-into-baskets/solutions/566275/tao-mo-ban-hua-dong-chuang-kou-qiu-zui-c-pner/
    public int totalFruit(int[] fruits) {
        if (fruits.length == 1 && fruits.length == 2) {
            return fruits.length;
        }
        // 存放滑動窗口采摘水果，窗口最大值為2
        Map<Integer, Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;
        int count = 0;
        while (right < fruits.length) {
            map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);
            while (map.size() > 2) {
                // count為水果最大數目，當map大小超過2時，滑窗需前移left;
                map.put(fruits[left], map.get(fruits[left]) - 1);
                if (map.get(fruits[left]) == 0) {
                    map.remove(fruits[left]);
                }
                left++;
            }
            count = Math.max(count, right - left + 1);
            right++;
        }
        return count;
    }
}

