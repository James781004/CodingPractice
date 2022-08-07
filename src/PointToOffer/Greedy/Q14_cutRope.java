package PointToOffer.Greedy;

public class Q14_cutRope {

    public int cutRope(int target) {
        if (target == 2 || target == 3)
            return target - 1;
        int res = 1;

        // 如果把整数n分为两部分，那么这两部分的值相差越小乘积越大
        while (target > 4) {
            //如果target大于4，我们不停的让他减去3
            target = target - 3;
            //计算每段的乘积
            res = res * 3;
        }
        return target * res;
    }

    public int cutRopeDP(int target) {
        // 定义一个数组dp，其中dp[i]表示的是长度为i的绳子能得到的最大乘积。
        // 我们先把长度为i的绳子拆成两部分，一部分是j，另一部分是i-j，那么会有下面4种情况
        // 1，j和i-j都不能再拆了: dp[i]=j*(i-j);
        // 2，j能拆，i-j不能拆: dp[i]=dp[j]*(i-j);
        // 3，j不能拆，i-j能拆: dp[i]=j*dp[i-j];
        // 4，j和i-j都能拆: dp[i]=dp[j]*dp[i-j];
        // 取上面4种情况的最大值即可。我们把它整理一下，得到递推公式如下:
        // dp[i] = max(dp[i], (max(j, dp[j])) * (max(i - j, dp[i - j])));

        int[] dp = new int[target + 1];
        dp[1] = 1;
        for (int i = 2; i <= target; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i], (Math.max(j, dp[j])) * (Math.max(i - j, dp[i - j])));
            }
        }
        return dp[target];
    }
}
