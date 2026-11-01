package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.HashMap;

public class FourSumCount {

    // https://leetcode.cn/problems/4sum-ii/solutions/3038338/on2-jian-ji-xie-fa-pythonjavacgojsrust-b-paps/
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums1) {
            for (int y : nums2) {
                cnt.merge(x + y, 1, Integer::sum); // cnt[x+y]++
            }
        }

        int ans = 0;
        for (int x : nums3) {
            for (int y : nums4) {
                ans += cnt.getOrDefault(-x - y, 0);
            }
        }
        return ans;
    }

}
