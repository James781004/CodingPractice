package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.Arrays;

public class ThreeSumClosest {

    // https://leetcode.cn/problems/3sum-closest/solutions/6959/hua-jie-suan-fa-16-zui-jie-jin-de-san-shu-zhi-he-b/
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length; i++) {
            int start = i + 1, end = nums.length - 1;
            while (start < end) {
                int sum = nums[start] + nums[end] + nums[i];
                if (Math.abs(target - sum) < Math.abs(target - ans))
                    ans = sum;
                if (sum > target)
                    end--;
                else if (sum < target)
                    start++;
                else
                    return ans;
            }
        }
        return ans;
    }


}
