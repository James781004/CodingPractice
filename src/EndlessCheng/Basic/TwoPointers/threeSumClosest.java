package EndlessCheng.Basic.TwoPointers;

import java.util.Arrays;

public class threeSumClosest {

    // https://leetcode.cn/problems/3sum-closest/solutions/2337801/ji-zhi-you-hua-ji-yu-san-shu-zhi-he-de-z-qgqi/
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = 0, n = nums.length;
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < n - 2; i++) {
            int x = nums[i];
            if (i > 0 && x == nums[i - 1]) {
                continue; // 優化三
            }

            // 優化一
            int s = x + nums[i + 1] + nums[i + 2];
            if (s > target) { // 後面無論怎麼選，選出的三個數的和不會比 s 還小
                if (s - target < minDiff) {
                    ans = s; // 由於下面直接 break，這裡無需更新 minDiff
                }
                break;
            }

            // 優化二
            s = x + nums[n - 2] + nums[n - 1];
            if (s < target) { // x 加上後面任意兩個數都不超過 s，所以下面的雙指針就不需要跑了
                if (target - s < minDiff) {
                    minDiff = target - s;
                    ans = s;
                }
                continue;
            }

            // 雙指針
            int j = i + 1, k = n - 1;
            while (j < k) {
                s = x + nums[j] + nums[k];
                if (s == target) {
                    return target;
                }
                if (s > target) {
                    if (s - target < minDiff) { // s 與 target 更近
                        minDiff = s - target;
                        ans = s;
                    }
                    k--;
                } else { // s < target
                    if (target - s < minDiff) { // s 與 target 更近
                        minDiff = target - s;
                        ans = s;
                    }
                    j++;
                }
            }
        }
        return ans;
    }
}
