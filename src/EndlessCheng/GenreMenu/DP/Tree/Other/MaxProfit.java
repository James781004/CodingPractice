package EndlessCheng.GenreMenu.DP.Tree.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxProfit {

    // https://leetcode.cn/problems/maximum-profit-from-trading-stocks-with-discounts/solutions/3685504/shu-shang-bei-bao-zhuang-tai-ji-dppython-2q7b/
    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : hierarchy) {
            g[e[0] - 1].add(e[1] - 1);
        }

        int[][] f0 = dfs(0, g, present, future, budget);
        return f0[budget][0];
    }

    private int[][] dfs(int x, List<Integer>[] g, int[] present, int[] future, int budget) {
        // 計算從 x 的所有兒子子樹 y 中，能得到的最大利潤之和（x 不買，x 買）
        int[][] subF = new int[budget + 1][2];
        for (int y : g[x]) {
            int[][] fy = dfs(y, g, present, future, budget);
            for (int j = budget; j >= 0; j--) {
                // 枚舉子樹 y 的預算為 jy
                // 當作一個體積為 jy，價值為 fy[jy][k] 的物品
                for (int jy = 0; jy <= j; jy++) {
                    for (int k = 0; k < 2; k++) { // k=0 表示 x 不買，k=1 表示 x 買
                        subF[j][k] = Math.max(subF[j][k], subF[j - jy][k] + fy[jy][k]);
                    }
                }
            }
        }

        // 計算從子樹 x 中，能得到的最大利潤之和（x 父節點不買，x 父節點買）
        int[][] f = new int[budget + 1][2];
        for (int j = 0; j <= budget; j++) {
            for (int k = 0; k < 2; k++) { // k=0 表示 x 父節點不買，k=1 表示 x 父節點買
                int cost = present[x] / (k + 1);
                if (j >= cost) {
                    // 不買 x，轉移來源是 subF[j][0]
                    // 買 x，轉移來源為 subF[j-cost][1]，因為對於子樹來說，父節點一定買
                    f[j][k] = Math.max(subF[j][0], subF[j - cost][1] + future[x] - cost);
                } else { // 只能不買 x
                    f[j][k] = subF[j][0];
                }
            }
        }
        return f;
    }


}
