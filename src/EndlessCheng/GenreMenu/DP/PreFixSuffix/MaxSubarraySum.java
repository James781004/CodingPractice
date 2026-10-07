package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.HashMap;
import java.util.Map;

public class MaxSubarraySum {

    // https://leetcode.cn/problems/maximize-subarray-sum-after-removing-all-occurrences-of-one-element/solutions/3039428/liang-chong-fang-fa-xian-duan-shu-qian-h-961z/
    private long f = Long.MIN_VALUE / 2; // 當前的最大子數組和
    private long s = 0; // 前綴和
    private final Map<Integer, Long> last = new HashMap<>(); // 記錄每個元素最後一次更新的狀態

    public long maxSubarraySum(int[] nums) {
        int n = nums.length;
        long[] pre = new long[n];
        for (int i = 0; i < n; i++) {
            pre[i] = update(nums[i]);
        }

        long ans = Long.MIN_VALUE;
        f = Long.MIN_VALUE / 2;
        s = 0;
        last.clear();
        for (int i = n - 1; i >= 0; i--) {
            long suf = update(nums[i]);
            ans = Math.max(ans, Math.max(f, Math.max(pre[i] + suf, Math.max(pre[i], suf))));
        }
        return ans;
    }

    private long update(int x) {
        long res = f; // f[i-1]
        f = Math.max(f, 0) + x; // f[i] = max(f[i-1], 0) + x
        if (last.containsKey(x)) {
            res = Math.max(res, last.get(x) + s); // s[i]
        }
        s += x; // s[i+1] = s[i] + x
        last.put(x, res - s);
        return res;
    }

}
