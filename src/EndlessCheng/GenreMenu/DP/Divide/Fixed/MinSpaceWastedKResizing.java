package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class MinSpaceWastedKResizing {

    // https://leetcode.cn/problems/minimum-total-space-wasted-with-k-resizing-operations/solutions/921978/python-ji-yi-hua-di-gui-er-wei-dp-by-qub-u72h/
    // 可以變換k次，也就是可以將原數組分成k+1個區間，每個區間必須取區間最大值
    // （認定每個區間內，不能變化，所以必須取區間最大）
    public int minSpaceWastedKResizing(int[] nums, int k) {
        int n = nums.length;
        int sum = 0;

        // 原題裡每個區間都要求和（區間最大值-每個值），
        // 其實拆開算，就是區間最大值*區間長度再減去區間和
        int[][] premax = new int[n][n];
        for (int i = 0; i < n; i++) {
            int m = 0;
            for (int j = i; j < n; j++) {
                m = Math.max(m, nums[j]);
                premax[i][j] = m * (j + 1 - i);
            }
            sum += nums[i];
        }

        int[][] dp = new int[n][k + 2];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], 0x3f3f3f3f);

        // 遞歸的時候統計我們最小能取到的最大值的各區間和即可
        for (int i = 0; i < n; i++)
            for (int j = 1; j <= k + 1; j++)
                for (int l = 0; l <= i; l++)
                    dp[i][j] = Math.min(dp[i][j], (l == 0 ? 0 : dp[l - 1][j - 1]) + premax[l][i]);
        return dp[n - 1][k + 1] - sum;
    }


}
