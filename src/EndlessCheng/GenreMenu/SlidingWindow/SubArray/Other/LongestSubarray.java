package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Other;

import java.util.TreeMap;

public class LongestSubarray {

    // https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/solutions/612773/he-gua-de-shu-ju-jie-gou-hua-dong-chuang-v46j/
    public int longestSubarray(int[] nums, int limit) {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        int left = 0, right = 0;
        int res = 0;
        while (right < nums.length) {
            m.put(nums[right], m.getOrDefault(nums[right], 0) + 1);
            while (m.lastKey() - m.firstKey() > limit) {
                m.put(nums[left], m.get(nums[left]) - 1);
                if (m.get(nums[left]) == 0) {
                    m.remove(nums[left]);
                }
                left++;
            }
            res = Math.max(res, right - left + 1);
            right++;
        }
        return res;
    }


}
