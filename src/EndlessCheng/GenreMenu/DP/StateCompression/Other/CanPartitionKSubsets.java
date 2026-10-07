package EndlessCheng.GenreMenu.DP.StateCompression.Other;

import java.util.Arrays;

public class CanPartitionKSubsets {

    // https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/solutions/1835705/by-lcbin-92ll/
    private int[] f;
    private int[] nums;
    private int n;
    private int s;

    public boolean canPartitionKSubsets(int[] nums, int k) {
        for (int v : nums) {
            s += v;
        }
        if (s % k != 0) {
            return false;
        }
        s /= k;
        Arrays.sort(nums);
        this.nums = nums;
        n = nums.length;
        f = new int[1 << n];
        return dfs(0, 0);
    }

    private boolean dfs(int state, int t) {
        if (state == (1 << n) - 1) {
            return true;
        }
        if (f[state] != 0) {
            return f[state] == 1;
        }
        for (int i = 0; i < n; ++i) {
            // 對於第 i 個數，若 ((state >> i) & 1) 等於 0，說明第 i 個元素未被劃分
            if (((state >> i) & 1) == 1) {
                continue;
            }

            // 若 t+nums[i]>s，說明第 i 個元素不能被添加到當前子集中
            // 由於我們對 nums 數組進行升序排列，因此數組 nums 從位置 i 開始的所有元素都不能被添加到當前子集，
            // 直接返回 false
            if (t + nums[i] > s) {
                break;
            }

            // 將第 i 個元素添加到當前子集中，狀態變為 state | (1 << i)，然後繼續對未劃分的元素進行搜索
            // 若 t+nums[i]=s，說明恰好可以得到一個和為 s 的子集，
            // 下一步將 t 歸零（可以通過 (t + nums[i]) % s 實現），並繼續劃分下一個子集
            if (dfs(state | 1 << i, (t + nums[i]) % s)) {
                f[state] = 1;
                return true;
            }
        }
        f[state] = -1;
        return false;
    }


}
