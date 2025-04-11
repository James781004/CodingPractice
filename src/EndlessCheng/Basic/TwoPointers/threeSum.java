package EndlessCheng.Basic.TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class threeSum {
    // https://leetcode.cn/problems/3sum/
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n - 2; ++i) {
            int x = nums[i];
            if (i > 0 && x == nums[i - 1]) continue; // 跳過重復數字
            if (x + nums[i + 1] + nums[i + 2] > 0) break; // 優化一
            if (x + nums[n - 2] + nums[n - 1] < 0) continue; // 優化二
            int j = i + 1, k = n - 1;
            while (j < k) {
                int s = x + nums[j] + nums[k];
                if (s > 0) --k;
                else if (s < 0) ++j;
                else {
                    ans.add(List.of(x, nums[j], nums[k]));
                    for (++j; j < k && nums[j] == nums[j - 1]; ++j) ; // 跳過重復數字
                    for (--k; k > j && nums[k] == nums[k + 1]; --k) ; // 跳過重復數字
                }
            }
        }
        return ans;
    }

}
