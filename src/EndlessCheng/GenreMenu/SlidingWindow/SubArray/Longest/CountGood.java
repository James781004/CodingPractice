package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Longest;

import java.util.HashMap;
import java.util.Map;

public class CountGood {

    // https://leetcode.cn/problems/count-the-number-of-good-subarrays/solutions/2062761/shuang-zhi-zhen-by-endlesscheng-lkd9/
    public long countGood(int[] nums, int k) {
        long ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>(); // 用一個哈希表 cnt 維護子數組內的每個元素的出現次數
        int pairs = 0; // 相同數對的個數
        int left = 0;
        for (int x : nums) { // 從小到大枚舉子數組右端點 right，右端點移動後，pairs 增加了 cnt[nums[right]]
            pairs += cnt.merge(x, 1, Integer::sum) - 1; // pairs += cnt[x]++
            while (pairs >= k) { // 子數組符合要求，則右移左端點 left，然後把 pairs 減少
                pairs -= cnt.merge(nums[left], -1, Integer::sum); // pairs -= --cnt[nums[left]]
                left++;
            }
            ans += left; // 左端點在 0,1,2,⋯,left−1 的所有子數組都是合法的，這一共有 left 個
        }
        return ans;
    }


}
