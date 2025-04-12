package EndlessCheng.GenreMenu.SlidingWindow.ThreePointers;

import java.util.HashSet;

public class ArithmeticTriplets {

    // https://leetcode.cn/problems/number-of-arithmetic-triplets/solutions/1728743/by-endlesscheng-mpxt/
    public int arithmeticTriplets(int[] nums, int diff) {
        int ans = 0;
        var set = new HashSet<Integer>();
        for (int x : nums) set.add(x);
        for (int x : nums)
            if (set.contains(x - diff) && set.contains(x + diff))
                ++ans;
        return ans;
    }


    public int arithmeticTriplets2(int[] nums, int diff) {
        int ans = 0, i = 0, j = 1;
        for (int x : nums) { // x = nums[k]
            // 移動 j 直到 nums[j]+diff≥x
            while (nums[j] + diff < x)
                ++j;

            // 指針 j 已經超過範圍，去找下一個 x
            if (nums[j] + diff > x)
                continue;

            // nums[j] + diff == x 的情況下，開始尋找指針 i
            // nums[j] + diff*2 < x，則移動 i 直到 nums[i]+2⋅diff≥x
            while (nums[i] + diff * 2 < x)
                ++i;

            // 如果 nums[i]+2⋅diff=x，則找到了一對等差三元組
            if (nums[i] + diff * 2 == x)
                ++ans;
        }
        return ans;
    }


}
