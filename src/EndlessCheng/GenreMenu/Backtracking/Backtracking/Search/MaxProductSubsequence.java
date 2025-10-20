package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MaxProductSubsequence {

    // https://leetcode.cn/problems/maximum-product-of-subsequences-with-an-alternating-sum-equal-to-k/solutions/3641716/bao-li-sou-suo-by-endlesscheng-j3bl/
    private int ans = -1;

    public int maxProduct(int[] nums, int k, int limit) {
        int sum = Arrays.stream(nums).sum();
        if (sum < Math.abs(k)) { // |k| 太大
            return -1;
        }

        Set<Long> vis = new HashSet<>();
        dfs(0, 0, 1, false, true, nums, k, limit, sum, vis);
        return ans;
    }

    private void dfs(int i, int s, int m, boolean odd, boolean empty, int[] nums, int k, int limit, int bias, Set<Long> vis) {
        if (ans == limit || m > limit && ans >= 0) { // 無法讓 ans 變得更大
            return;
        }

        if (i == nums.length) {
            if (!empty && s == k && m <= limit) { // 合法子序列
                ans = Math.max(ans, m); // 用合法子序列的元素積更新答案的最大值
            }
            return;
        }

        long mask = (long) i << 32 | (s + bias) << 15 | m << 2 | (odd ? 1 : 0) << 1 | (empty ? 1 : 0);
        if (!vis.add(mask)) { // mask 在 vis 中
            return;
        }

        // 不選 x
        dfs(i + 1, s, m, odd, empty, nums, k, limit, bias, vis);

        // 選 x
        int x = nums[i];
        dfs(i + 1, s + (odd ? -x : x), Math.min(m * x, limit + 1), !odd, false, nums, k, limit, bias, vis);
    }


}
