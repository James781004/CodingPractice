package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_473 {

    // https://leetcode.cn/problems/remove-zeros-in-decimal-representation/solutions/3815660/bu-yong-zi-fu-chuan-de-xie-fa-pythonjava-myg7/
    public long removeZeros(long n) {
        long ans = 0;
        long pow10 = 1;
        while (n > 0) {
            long d = n % 10;
            if (d > 0) {
                ans += d * pow10;
                pow10 *= 10;
            }
            n /= 10;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-alternating-sum-of-squares/solutions/3815653/tan-xin-pai-xu-kuai-su-xuan-ze-pythonjav-qfmr/
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            nums[i] *= nums[i];
        }
        Arrays.sort(nums);

        int m = n / 2;
        // 交替和：減去小的，加上大的
        long ans = 0;
        for (int i = 0; i < m; i++) {
            ans -= nums[i];
        }
        for (int i = m; i < n; i++) {
            ans += nums[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/stable-subarrays-with-equal-boundary-and-interior-sum/solutions/3815641/qian-zhui-he-yu-ha-xi-biao-shi-zi-bian-x-d6vf/
    private record Pair(int x, long s) {
    }

    public long countStableSubarrays(int[] capacity) {
        Map<Pair, Integer> cnt = new HashMap<>();
        long sum = capacity[0]; // 前綴和
        long ans = 0;
        for (int i = 1; i < capacity.length; i++) {
            ans += cnt.getOrDefault(new Pair(capacity[i], sum), 0);
            cnt.merge(new Pair(capacity[i - 1], capacity[i - 1] + sum), 1, Integer::sum);
            sum += capacity[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-distinct-subarrays-divisible-by-k-in-sorted-array/solutions/3815647/qian-zhui-he-yu-ha-xi-biao-bi-mian-zhong-nc4l/
    public long numGoodSubarrays(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        cnt.put(0, 1); // 初始化
        long sum = 0; // 前綴和
        int lastStart = 0; // 上一個連續相同段的起始下標
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (i > 0 && x != nums[i - 1]) {
                // 上一個連續相同段結束，可以把上一段對應的前綴和添加到 cnt
                long s = sum;
                for (int t = i - lastStart; t > 0; t--) {
                    cnt.merge((int) (s % k), 1, Integer::sum); // cnt[s % k]++
                    s -= nums[i - 1];
                }
                lastStart = i;
            }
            sum += x;
            ans += cnt.getOrDefault((int) (sum % k), 0);
        }
        return ans;
    }


}









