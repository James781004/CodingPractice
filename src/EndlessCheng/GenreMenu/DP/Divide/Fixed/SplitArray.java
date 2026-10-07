package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class SplitArray {

    // https://leetcode.cn/problems/split-array-largest-sum/solutions/2613046/er-fen-da-an-fu-ti-dan-pythonjavacgojsru-n5la/
    int[] nums;
    int k;
    int[][] memo;
    int n;
    int[] pre;

    public int splitArray(int[] nums, int k) {
        this.n = nums.length;
        this.k = k;
        this.nums = nums;
        this.memo = new int[k + 1][n];
        for (var arr : memo) {
            Arrays.fill(arr, -1);
        }
        pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }
        return dfs(k, n - 1);
    }

    private int dfs(int i, int j) {
        if (i == 0) {
            if (j == -1) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }
        if (memo[i][j] != -1) return memo[i][j];
        int sum = 0;
        int res = Integer.MAX_VALUE;
        for (int l = j; l >= i - 1; l--) {
            sum = pre[j + 1] - pre[l];
            res = Math.min(res, Math.max(sum, dfs(i - 1, l - 1)));
        }
        return memo[i][j] = res;
    }

}
