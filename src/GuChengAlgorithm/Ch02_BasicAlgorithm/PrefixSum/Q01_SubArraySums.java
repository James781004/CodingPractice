package GuChengAlgorithm.Ch02_BasicAlgorithm.PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class Q01_SubArraySums {
    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_0_36
    public int subArraySum(int[] nums, int k) {
        int count = 0;
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;

        // prefix sum
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i + 1];
        }

        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                if (sum[end] - sum[start] == k) count++;  // 求區間和等於k的答案
            }
        }

        return count;
    }


    public int subArraySum2(int[] nums, int k) {
        // preSum2 - preSum1 = target，所以我們要找的是map裡面是否存在preSum - target
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0, res = 0;
        map.put(0, 1);  // (preSum, frequency)，初始化先放一個preSum = 0的
        for (int num : nums) {
            sum += num;
            if (map.containsKey(sum - k)) res += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_1_9
    // 核心思路就是當相同餘數再次出現的時候，兩次出現餘數i, j之間的sub array一定是能夠被整除的
    // [4, 5, 0, -2, -3, 1]
    //
    // [5] 餘數0
    // [5, 0] 餘數0
    //[ -2,-3] 餘數0
    //
    // [1, 2, 3, 4]  k = 5
    // 1,  餘數1
    // 3,  餘數3
    // 6,  餘數1 (餘數1再次出現，表示中間有區間和正好等於k，並且可以被整除的區間)
    //     得知[2,3]可以被整除
    public int subArraysDivByK(int[] A, int K) {
        // preSum2 - preSum1 = target，所以我們要找的是map裡面是否存在preSum - target
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);  // (preSum, frequency)，初始化先放一個preSum = 0的
        int prefix = 0, res = 0;
        for (int a : A) {
            prefix = (prefix + a % K + K) % K;  // 這裡是為了避免a為負數的狀況
            res += map.getOrDefault(prefix, 0);
            map.put(prefix, map.getOrDefault(prefix, 0) + 1);
        }
        return res;
    }
}
