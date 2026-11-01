package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.TreeSet;

public class ContainsNearbyAlmostDuplicate {

    // https://leetcode.cn/problems/contains-duplicate-iii/solutions/726905/gong-shui-san-xie-yi-ti-shuang-jie-hua-d-dlnv/
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int n = nums.length;
        TreeSet<Long> ts = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            Long u = nums[i] * 1L;
            // 從 ts 中找到小於等於 u 的最大值（小於等於 u 的最接近 u 的數）
            Long l = ts.floor(u);
            // 從 ts 中找到大於等於 u 的最小值（大於等於 u 的最接近 u 的數）
            Long r = ts.ceiling(u);
            if (l != null && u - l <= t) return true;
            if (r != null && r - u <= t) return true;
            // 將當前數加到 ts 中，並移除下標范圍不在 [max(0, i - k), i) 的數（維持滑動窗口大小為 k）
            ts.add(u);
            if (i >= k) ts.remove(nums[i - k] * 1L);
        }
        return false;
    }

}
