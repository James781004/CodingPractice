package EndlessCheng.GenreMenu.SlidingWindow.ThreePointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MaxFrequency {

    // https://leetcode.cn/problems/maximum-frequency-of-an-element-after-performing-operations-ii/solutions/2983355/liang-chong-fang-fa-chai-fen-hua-dong-ch-7buy/
    public int maxFrequency(int[] nums, int k, int numOperations) {
        Arrays.sort(nums);

        int n = nums.length;
        int ans = 0;
        int cnt = 0;
        int left = 0;
        int right = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            cnt++;
            // 循環直到連續相同段的末尾，這樣可以統計出 x 的出現次數
            if (i < n - 1 && x == nums[i + 1]) {
                continue;
            }
            while (nums[left] < x - k) {
                left++;
            }
            while (right < n && nums[right] <= x + k) {
                right++;
            }
            ans = Math.max(ans, Math.min(right - left, cnt + numOperations));
            cnt = 0;
        }

        if (ans >= numOperations) {
            return ans;
        }

        left = 0;
        for (right = 0; right < n; right++) {
            int x = nums[right];
            while (nums[left] < x - k * 2) {
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return Math.min(ans, numOperations); // 最後和 numOperations 取最小值
    }


    public int maxFrequency2(int[] nums, int k, int numOperations) {
        Map<Integer, Integer> cnt = new HashMap<>();
        Map<Integer, Integer> diff = new TreeMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum); // cnt[x]++
            diff.putIfAbsent(x, 0); // 把 x 插入 diff，以保證下面能遍歷到 x
            // 把 [x-k, x+k] 中的每個整數的出現次數都加一
            diff.merge(x - k, 1, Integer::sum); // diff[x-k]++
            diff.merge(x + k + 1, -1, Integer::sum); // diff[x+k+1]--
        }

        int ans = 0;
        int sumD = 0;
        for (Map.Entry<Integer, Integer> e : diff.entrySet()) {
            sumD += e.getValue();
            ans = Math.max(ans, Math.min(sumD, cnt.getOrDefault(e.getKey(), 0) + numOperations));
        }
        return ans;
    }


}
