package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.HashMap;
import java.util.Map;

public class Q12_PreFixSum {
    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g110eabfeb53_0_159
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) return new int[]{map.get(diff), i};
            map.put(nums[i], i);
        }
        return null;
    }


    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g110eabfeb53_0_165
    class SubArraySum {
        public int subArraySum(int[] nums, int k) {
            int count = 0;
            int[] sum = new int[nums.length + 1];
            sum[0] = 0;
            for (int i = 1; i <= nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i - 1];
            }

            for (int start = 0; start < nums.length; start++) {
                for (int end = start + 1; end <= nums.length; end++) {
                    if (sum[end] - sum[start] == k) count++;
                }
            }

            return count;
        }

        public int subArraySum2(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            int sum = 0, res = 0;
            map.put(0, 1);  // <PreFixSum, frequency>
            for (int num : nums) {
                sum += num;
                // preSumA - preSumB = target，這邊就要找preSumA - target是否存在於map
                if (map.containsKey(sum - target)) res += map.get(sum - target);
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
            return res;
        }
    }
}
