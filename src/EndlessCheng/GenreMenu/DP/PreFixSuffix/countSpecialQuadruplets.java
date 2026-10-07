package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.HashMap;
import java.util.Map;

public class countSpecialQuadruplets {

    // https://leetcode.cn/problems/count-special-quadruplets/solutions/1179031/tong-ji-te-shu-si-yuan-zu-by-leetcode-so-50e2/
    public int countQuadruplets(int[] nums) {
        int n = nums.length;
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int b = n - 3; b >= 1; --b) {
            for (int d = b + 2; d < n; ++d) {
                cnt.put(nums[d] - nums[b + 1], cnt.getOrDefault(nums[d] - nums[b + 1], 0) + 1);
            }
            for (int a = 0; a < b; ++a) {
                ans += cnt.getOrDefault(nums[a] + nums[b], 0);
            }
        }
        return ans;
    }

}
