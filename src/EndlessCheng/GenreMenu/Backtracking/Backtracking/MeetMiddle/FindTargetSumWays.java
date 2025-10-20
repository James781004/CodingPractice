package EndlessCheng.GenreMenu.Backtracking.Backtracking.MeetMiddle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FindTargetSumWays {

    // https://leetcode.cn/problems/target-sum/solutions/2119041/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-s1cx/
    public int findTargetSumWays(int[] nums, int target) {
        int s = 0;
        for (int x : nums) {
            s += x;
        }

        s -= Math.abs(target);
        if (s < 0 || s % 2 == 1) {
            return 0;
        }

        int m = s / 2;
        int k = nums.length / 2;
        Map<Integer, Integer> cnt1 = subsets(Arrays.copyOfRange(nums, 0, k));
        Map<Integer, Integer> cnt2 = subsets(Arrays.copyOfRange(nums, k, nums.length));

        int ans = 0;
        for (Map.Entry<Integer, Integer> e : cnt1.entrySet()) {
            int x = e.getKey();
            int c1 = e.getValue();
            ans += c1 * cnt2.getOrDefault(m - x, 0);
        }
        return ans;
    }

    // 78. 子集（二進制枚舉寫法）
    private Map<Integer, Integer> subsets(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < (1 << n); i++) {
            int s = 0;
            for (int j = 0; j < n; j++) {
                if ((i >> j & 1) == 1) {
                    s += nums[j];
                }
            }
            cnt.merge(s, 1, Integer::sum); // cnt[s]++
        }
        return cnt;
    }


}
