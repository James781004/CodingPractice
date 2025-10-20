package EndlessCheng.GenreMenu.DP.Basic.Ladder;

import java.util.Arrays;

public class MinCostClimbingStairs {


    // https://leetcode.cn/problems/min-cost-climbing-stairs/solutions/2569116/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-j99e/
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs(n, memo, cost);
    }

    private int dfs(int i, int[] memo, int[] cost) {
        if (i <= 1) { // 遞歸邊界
            return 0;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        int res1 = dfs(i - 1, memo, cost) + cost[i - 1];
        int res2 = dfs(i - 2, memo, cost) + cost[i - 2];
        return memo[i] = Math.min(res1, res2); // 記憶化
    }

    public int minCostClimbingStairsDP(int[] cost) {
        int n = cost.length;
        int[] f = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            f[i] = Math.min(f[i - 1] + cost[i - 1], f[i - 2] + cost[i - 2]);
        }
        return f[n];
    }

    public int minCostClimbingStairsDP2(int[] cost) {
        int f0 = 0, f1 = 0;
        for (int i = 1; i < cost.length; i++) {
            int newF = Math.min(f1 + cost[i], f0 + cost[i - 1]);
            f0 = f1;
            f1 = newF;
        }
        return f1;
    }


}
