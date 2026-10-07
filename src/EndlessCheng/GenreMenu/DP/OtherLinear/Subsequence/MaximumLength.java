package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

public class MaximumLength {

    // https://leetcode.cn/problems/find-the-maximum-length-of-valid-subsequence-ii/solutions/2826591/deng-jie-zhuan-huan-dong-tai-gui-hua-pyt-z2fs/
    public int maximumLength(int[] nums, int k) {
        int ans = 0;
        int[][] f = new int[k][k];
        for (int x : nums) {
            x %= k;
            for (int y = 0; y < k; y++) {
                // 對於 x=nums[i]，可以在「最後兩項為 x 和 y 的子序列」的末尾添加 x，
                // 得到末尾為 x,y,x 的子序列，只看最後兩個數，就是「最後兩項為 y 和 x 的子序列」，
                // 因為添加了一個元素，子序列的長度增加 1
                f[y][x] = f[x][y] + 1;
                ans = Math.max(ans, f[y][x]);
            }
        }
        return ans;
    }


}
