package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class MaximumScore {

    // https://leetcode.cn/problems/maximize-cyclic-partition-score/solutions/3827101/zhao-dao-zui-jia-duan-huan-wei-zhi-zhuan-k2ip/
    public long maximumScore(int[] nums, int k) {
        int n = nums.length;
        int maxI = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[maxI]) {
                maxI = i;
            }
        }

        long ans1 = maximumProfit(nums, maxI, maxI + n, k); // nums[maxI] 是第一個數
        long ans2 = maximumProfit(nums, maxI + 1, maxI + 1 + n, k); // nums[maxI] 是最後一個數
        return Math.max(ans1, ans2);
    }

    // 3573. 買賣股票的最佳時機 V
    private long maximumProfit(int[] prices, int l, int r, int k) {
        int n = prices.length;
        long[][] f = new long[k + 2][3];
        for (int j = 1; j <= k + 1; j++) {
            f[j][1] = Long.MIN_VALUE / 2; // 防止溢出
        }
        f[0][0] = Long.MIN_VALUE / 2;
        for (int i = l; i < r; i++) {
            int p = prices[i % n];
            for (int j = k + 1; j > 0; j--) {
                f[j][0] = Math.max(f[j][0], Math.max(f[j][1] + p, f[j][2] - p));
                f[j][1] = Math.max(f[j][1], f[j - 1][0] - p);
                f[j][2] = Math.max(f[j][2], f[j - 1][0] + p);
            }
        }
        return f[k + 1][0];
    }


}
