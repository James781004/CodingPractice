package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

import java.util.HashSet;
import java.util.Set;

public class MaxFrequency {

    // https://leetcode.cn/problems/maximum-frequency-after-subarray-operation/solutions/3057702/mei-ju-zhuang-tai-ji-dp-by-endlesscheng-qpt0/
    public int maxFrequency(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            set.add(x);
        }

        int ans = 0;
        for (int target : set) {
            int f0 = 0, f1 = 0, f2 = 0;
            for (int x : nums) {
                f2 = Math.max(f2, f1) + (x == k ? 1 : 0);
                f1 = Math.max(f1, f0) + (x == target ? 1 : 0);
                f0 += (x == k ? 1 : 0);
            }
            ans = Math.max(ans, Math.max(f1, f2));
        }
        return ans;
    }


    public int maxFrequency2(int[] nums, int k) {
        int f0 = 0, maxF1 = 0, f2 = 0;
        int[] f1 = new int[51];
        for (int x : nums) {
            f2 = Math.max(f2, maxF1) + (x == k ? 1 : 0);
            f1[x] = Math.max(f1[x], f0) + 1;
            f0 += (x == k ? 1 : 0);
            maxF1 = Math.max(maxF1, f1[x]);
        }
        return Math.max(maxF1, f2);
    }


    public int maxFrequency3(int[] nums, int k) {
        int f0 = 0, maxF12 = 0;
        int[] f1 = new int[51];
        for (int x : nums) {
            if (x == k) {
                maxF12++;
                f0++;
            } else {
                f1[x] = Math.max(f1[x], f0) + 1;
                maxF12 = Math.max(maxF12, f1[x]);
            }
        }
        return maxF12;
    }


}
