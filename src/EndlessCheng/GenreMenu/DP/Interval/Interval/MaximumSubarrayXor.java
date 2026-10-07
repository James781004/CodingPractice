package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class MaximumSubarrayXor {

    // https://leetcode.cn/problems/maximum-xor-score-subarray-queries/solutions/2899932/qu-jian-dp-tao-qu-jian-dppythonjavacgo-b-w4be/
    public int[] maximumSubarrayXor(int[] nums, int[][] queries) {
        int n = nums.length;
        int[][] f = new int[n][n]; // f[i][j] 表示下標從 i 到 j 的子數組的「數組的異或值」
        int[][] mx = new int[n][n]; // mx[i][j] 表示下標從 i 到 j 的子數組中的所有子數組的 f 值的最大值
        for (int i = n - 1; i >= 0; i--) {
            f[i][i] = nums[i];
            mx[i][i] = nums[i];
            for (int j = i + 1; j < n; j++) {
                f[i][j] = f[i][j - 1] ^ f[i + 1][j];
                mx[i][j] = Math.max(f[i][j], Math.max(mx[i + 1][j], mx[i][j - 1]));
            }
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = mx[queries[i][0]][queries[i][1]];
        }
        return ans;
    }


    public int[] maximumSubarrayXor2(int[] f, int[][] queries) {
        int n = f.length;
        int[][] mx = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            mx[i][i] = f[i];
            for (int j = i + 1; j < n; j++) {
                f[j] ^= f[j - 1];
                mx[i][j] = Math.max(f[j], Math.max(mx[i + 1][j], mx[i][j - 1]));
            }
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = mx[queries[i][0]][queries[i][1]];
        }
        return ans;
    }


}
