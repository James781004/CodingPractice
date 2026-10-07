package EndlessCheng.GenreMenu.DP.GameTheory;

import java.util.Arrays;

public class CanIWin {

    // https://leetcode.cn/problems/can-i-win/solutions/1510035/by-ac_oier-0ed9/
    public static boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (maxChoosableInteger * (maxChoosableInteger + 1) / 2 < desiredTotal) return false;
        int[] dp = new int[1 << maxChoosableInteger];
        Arrays.fill(dp, -1);
        return dfs(desiredTotal, 0, maxChoosableInteger, dp) == 1;
    }

    public static int dfs(int total, int state, int max, int[] dp) {
        if (dp[state] != -1) return dp[state];
        for (int i = 0; i < max; i++) {
            if (((state >> i) & 1) == 1) continue;
            if (total <= i + 1) return dp[state] = 1; // 如果此時有數大於需要的和，則取該數獲得必勝
            // 如果對手出現必敗狀態，則將必敗狀態留給對手；
            if (dfs(total - i - 1, state | (1 << i), max, dp) == 0) return dp[state] = 1;
        }
        return dp[state] = 0;
    }

}
