package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Longest;

import java.util.HashMap;
import java.util.HashSet;

public class CountCompleteSubarrays {

    // https://leetcode.cn/problems/count-complete-subarrays-in-an-array/solutions/2364671/on-hua-dong-chuang-kou-by-endlesscheng-9ztb/
    public int countCompleteSubarrays(int[] nums) {
        var set = new HashSet<Integer>();
        for (int x : nums) set.add(x);
        int m = set.size();
        var cnt = new HashMap<Integer, Integer>();
        int ans = 0, left = 0;
        for (int v : nums) { // 枚舉子數組右端點 v=nums[i]
            cnt.merge(v, 1, Integer::sum);
            while (cnt.size() == m) {
                int x = nums[left++];
                if (cnt.merge(x, -1, Integer::sum) == 0)
                    cnt.remove(x);
            }
            ans += left; // 子數組左端點 < left 的都是合法的
        }
        return ans;
    }


}
