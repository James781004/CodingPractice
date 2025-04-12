package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Other;

import java.util.Arrays;

public class MinimumDifference {

    // https://leetcode.cn/problems/minimum-difference-between-highest-and-lowest-of-k-scores/solutions/2959286/1984-xue-sheng-fen-shu-de-zui-xiao-chai-29cr0/
    public int minimumDifference(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums); // 升序排序
        int res = Integer.MAX_VALUE;
        for (int i = 0; i + k - 1 < n; i++) { // 使用一個大小固定為 k 的滑動窗口在 nums 上進行遍歷
            res = Math.min(res, nums[i + k - 1] - nums[i]);
        }
        return res;
    }


}
