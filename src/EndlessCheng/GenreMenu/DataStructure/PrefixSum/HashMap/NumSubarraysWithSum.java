package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;
import java.util.Map;

public class NumSubarraysWithSum {

    // https://leetcode.cn/problems/binary-subarrays-with-sum/solutions/3986900/qia-hao-xing-hua-dong-chuang-kou-pythonj-3i6o/
    public int numSubarraysWithSum(int[] nums, int goal) {
        // 要有goal個1，即求子數組和為goal的數量
        // 維護前綴和
        int n = nums.length;
        int sum = 0;
        int ans = 0;

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            ans += map.getOrDefault(sum - goal, 0);
            map.merge(sum, 1, Integer::sum);
        }
        return ans;
    }
}
