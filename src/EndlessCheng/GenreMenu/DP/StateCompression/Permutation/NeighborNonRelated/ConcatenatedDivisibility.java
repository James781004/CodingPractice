package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

import java.util.Arrays;

public class ConcatenatedDivisibility {

    // https://leetcode.cn/problems/concatenated-divisibility/solutions/3663246/quan-pai-lie-bao-sou-pythonjavacgo-by-en-l4zv/
    public int[] concatenatedDivisibility(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] pow10 = new int[n];
        for (int i = 0; i < n; i++) {
            pow10[i] = (int) Math.pow(10, Integer.toString(nums[i]).length());
        }

        int[] ans = new int[n];
        boolean[][] vis = new boolean[1 << n][k];
        if (!dfs((1 << n) - 1, 0, nums, pow10, k, vis, ans)) {
            return new int[]{};
        }
        return ans;
    }

    private boolean dfs(int s, int x, int[] nums, int[] pow10, int k, boolean[][] vis, int[] ans) {
        if (s == 0) {
            return x == 0;
        }
        if (vis[s][x]) {
            return false;
        }
        vis[s][x] = true;
        // 枚舉在 s 中的下標 i
        for (int i = 0; i < nums.length; i++) {
            if ((s & (1 << i)) > 0 && dfs(s ^ (1 << i), (x * pow10[i] + nums[i]) % k, nums, pow10, k, vis, ans)) {
                ans[nums.length - Integer.bitCount(s)] = nums[i];
                return true;
            }
        }
        return false;
    }


}
