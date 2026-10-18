package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class InterchangeableRectangles {

    // https://leetcode.cn/problems/number-of-pairs-of-interchangeable-rectangles/solutions/992917/tong-ji-bi-zhi-de-zui-jian-fen-shu-by-en-7p1b/
    public long interchangeableRectangles(int[][] rectangles) {
        Map<String, Integer> map = new HashMap<>();
        long ans = 0;

        for (int[] arr : rectangles) { // 枚舉右
            int width = arr[0];
            int height = arr[1];
            int gcd = gcd(width, height); // 計算寬高的最大公約數

            // 使用最大公約數簡化比率
            String rate = (width / gcd) + ":" + (height / gcd);

            // 如果 map 中存在這個比率，增加可互換矩形的對數
            ans += map.getOrDefault(rate, 0);
            // 更新 map，增加當前比率的計數
            map.put(rate, map.getOrDefault(rate, 0) + 1);//維護左
        }

        return ans;
    }

    // 計算最大公約數的函數
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
