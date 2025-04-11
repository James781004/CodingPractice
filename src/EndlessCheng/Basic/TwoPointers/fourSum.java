package EndlessCheng.Basic.TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fourSum {

    // https://leetcode.cn/problems/4sum/solutions/2344514/ji-zhi-you-hua-ji-yu-san-shu-zhi-he-de-z-1f0b/
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        for (int a = 0; a < n - 3; a++) { // 枚舉第一個數
            long x = nums[a]; // 使用 long 避免溢出
            if (a > 0 && x == nums[a - 1]) continue; // 跳過重復數字
            if (x + nums[a + 1] + nums[a + 2] + nums[a + 3] > target) break; // 優化一
            if (x + nums[n - 3] + nums[n - 2] + nums[n - 1] < target) continue; // 優化二
            for (int b = a + 1; b < n - 2; b++) { // 枚舉第二個數
                long y = nums[b];
                if (b > a + 1 && y == nums[b - 1]) continue; // 跳過重復數字
                if (x + y + nums[b + 1] + nums[b + 2] > target) break; // 優化一
                if (x + y + nums[n - 2] + nums[n - 1] < target) continue; // 優化二
                int c = b + 1, d = n - 1;
                while (c < d) { // 雙指針枚舉第三個數和第四個數
                    long s = x + y + nums[c] + nums[d]; // 四數之和
                    if (s > target) d--;
                    else if (s < target) c++;
                    else { // s == target
                        ans.add(List.of((int) x, (int) y, nums[c], nums[d]));
                        for (c++; c < d && nums[c] == nums[c - 1]; c++) ; // 跳過重復數字
                        for (d--; d > c && nums[d] == nums[d + 1]; d--) ; // 跳過重復數字
                    }
                }
            }
        }
        return ans;
    }

}
