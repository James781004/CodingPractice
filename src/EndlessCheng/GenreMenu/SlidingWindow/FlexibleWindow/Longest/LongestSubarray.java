package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.ArrayList;
import java.util.List;

public class LongestSubarray {

    // https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/solutions/2830475/javapython3chua-dong-chuang-kou-suo-yin-o4eyl/
    public int longestSubarray(int[] nums) {
        List<Integer> zeroIdxs = new ArrayList<>();    // 記錄數組中0元素的索引
        int n = nums.length;
        zeroIdxs.add(-1);   // -1表示左邊界
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) zeroIdxs.add(i);
        }
        zeroIdxs.add(n);   // n表示右邊界
        if (zeroIdxs.size() <= 3) return n - 1;   // 數組中0的個數不超過1個，直接刪除唯一0或任意元素，整個數組都為連續1
        int m = zeroIdxs.size();
        int res = 0;
        for (int i = 2; i < m; i++) {
            // 枚舉每個滑動窗口右邊界i，左邊界就為i - 2，保證(i,j)之間有1個0
            // (i, j)本來有j - i - 1個數，還要刪掉一個0，長度即為 j - i - 2
            res = Math.max(res, zeroIdxs.get(i) - zeroIdxs.get(i - 2) - 2);
        }
        return res;
    }


}
