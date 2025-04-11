package EndlessCheng.Basic.DP;

import java.util.Arrays;
import java.util.List;

public class connectTwoGroups {

    // https://leetcode.cn/problems/minimum-cost-to-connect-two-groups-of-points/solutions/2314687/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-djxq/
    private List<List<Integer>> cost;
    private int[] minCost;
    private int[][] memo;

    public int connectTwoGroups(List<List<Integer>> cost) {
        this.cost = cost;
        int n = cost.size(), m = cost.get(0).size();
        minCost = new int[m];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        for (int j = 0; j < m; j++)
            for (var c : cost)
                minCost[j] = Math.min(minCost[j], c.get(j));

        memo = new int[n][1 << m];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        return dfs(n - 1, (1 << m) - 1);
    }

    private int dfs(int i, int j) {
        if (i < 0) {
            int res = 0;
            for (int k = 0; k < minCost.length; k++)
                if ((j >> k & 1) == 1) // 第二組的點 k 未連接
                    res += minCost[k]; // 去第一組找個成本最小的點連接
            return res;
        }
        if (memo[i][j] != -1) return memo[i][j]; // 之前算過了
        int res = Integer.MAX_VALUE;
        for (int k = 0; k < minCost.length; k++) // 第一組的點 i 與第二組的點 k
            res = Math.min(res, dfs(i - 1, j & ~(1 << k)) + cost.get(i).get(k));
        return memo[i][j] = res; // 記憶化
    }

    public int connectTwoGroupsDP(List<List<Integer>> cost) {
        int n = cost.size(), m = cost.get(0).size();
        var minCost = new int[m];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        for (int j = 0; j < m; j++)
            for (var c : cost)
                minCost[j] = Math.min(minCost[j], c.get(j));

        var f = new int[n + 1][1 << m];
        for (int j = 0; j < 1 << m; j++)
            for (int k = 0; k < m; k++)
                if ((j >> k & 1) == 1) // 第二組的點 k 未連接
                    f[0][j] += minCost[k]; // 去第一組找個成本最小的點連接

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1 << m; j++) {
                int res = Integer.MAX_VALUE;
                for (int k = 0; k < m; k++) // 第一組的點 i 與第二組的點 k
                    res = Math.min(res, f[i][j & ~(1 << k)] + cost.get(i).get(k));
                f[i + 1][j] = res;
            }
        }
        return f[n][(1 << m) - 1];
    }


}
