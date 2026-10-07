package EndlessCheng.GenreMenu.DP.StateCompression.Other;

import java.util.Arrays;

public class Makesquare {

    // https://leetcode.cn/problems/matchsticks-to-square/solutions/939330/ya-suo-dp-by-tianbushengwo-my0j/
    public boolean makesquare(int[] matchsticks) {
        int sum = 0, len = matchsticks.length, size = 1 << len;
        Arrays.sort(matchsticks);
        for (int matchstick : matchsticks) {
            sum += matchstick;
        }
        int target = sum / 4;
        if (sum % 4 != 0 || matchsticks[len - 1] > sum / 4) {
            return false;
        }
        boolean[] dp = new boolean[size];
        int[] currentSum = new int[size];
        dp[0] = true;
        for (int i = 0; i < size; i++) {
            if (!dp[i]) {
                continue;
            }
            for (int j = 0; j < len; j++) {
                if ((i & (1 << j)) != 0) {
                    continue;
                }
                int next = i | (1 << j);
                if (dp[next]) {
                    continue;
                }
                if ((currentSum[i] % target) + matchsticks[j] <= target) {
                    currentSum[next] = currentSum[i] + matchsticks[j];
                    dp[next] = true;
                } else {
                    break;
                }
            }

        }
        // 左移的優先級比-要高
        return dp[size - 1];
    }


}
