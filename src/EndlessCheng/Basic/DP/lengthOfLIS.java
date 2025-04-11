package EndlessCheng.Basic.DP;

import java.util.ArrayList;
import java.util.List;

public class lengthOfLIS {

    // https://leetcode.cn/problems/longest-increasing-subsequence/solutions/2147040/jiao-ni-yi-bu-bu-si-kao-dpfu-o1-kong-jia-4zma/
    private int[] nums;
    private int[] memo;

    public int lengthOfLISMemo(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        memo = new int[n]; // 本題可以初始化成 0，表示沒有計算過
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dfs(i));
        }
        return ans;
    }

    private int dfs(int i) {
        if (memo[i] > 0) { // 之前計算過
            return memo[i];
        }
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                memo[i] = Math.max(memo[i], dfs(j));
            }
        }
        return ++memo[i];
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length, ans = 0;
        int[] f = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    f[i] = Math.max(f[i], f[j]);
                }
            }
            ans = Math.max(ans, ++f[i]);
        }
        return ans;
    }

    // 貪心 + 二分查找
    public int lengthOfLIS2(int[] nums) {
        List<Integer> g = new ArrayList<>();
        for (int x : nums) {
            int j = lowerBound(g, x);
            if (j == g.size()) {
                g.add(x); // >=x 的 g[j] 不存在
            } else {
                g.set(j, x);
            }
        }
        return g.size();
    }

    // 開區間寫法
    private int lowerBound(List<Integer> g, int target) {
        int left = -1, right = g.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (g.get(mid) < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right; // 或者 left+1
    }


}
