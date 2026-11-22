package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;
import java.util.Map;

public class MaximumSubarraySum {

    // https://leetcode.cn/problems/maximum-good-subarray-sum/solutions/2630644/qian-zhui-he-ha-xi-biao-fu-ti-dan-python-z61d/
    public long maximumSubarraySum(int[] nums, int k) {
        Map<Integer, Long> minS = new HashMap<>();
        long sum = 0;
        long ans = Long.MIN_VALUE;
        for (int x : nums) {
            Long s = minS.get(x - k);
            if (s != null) {
                ans = Math.max(ans, sum + x - s);
            }

            s = minS.get(x + k);
            if (s != null) {
                ans = Math.max(ans, sum + x - s);
            }

            minS.merge(x, sum, Math::min); // minS[x] = Math.min(minS[x], sum)
            sum += x;
        }
        return ans == Long.MIN_VALUE ? 0 : ans;
    }

}
