package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.Arrays;
import java.util.List;

public class ConnectTwoGroups {

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

    // dfs(i,j) 表示第一組的 0,1,…,i 和第二組的 0,1,…,m−1 相連，
    // 且第二組的集合 j 未被連接時，最小成本是多少
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

}
