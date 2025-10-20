package EndlessCheng.GenreMenu.DP.Knapsack.Complete;

import java.util.ArrayList;
import java.util.List;

public class FindCoins {

    // https://leetcode.cn/problems/inverse-coin-change/solutions/3705647/wan-quan-bei-bao-pythonjavacgo-by-endles-y6oq/
    public List<Integer> findCoins(int[] numWays) {
        int n = numWays.length;
        int[] f = new int[n + 1];
        f[0] = 1;
        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int ways = numWays[i - 1];
            if (ways == f[i]) {
                continue;
            }
            if (ways - 1 != f[i]) {
                return List.of();
            }
            ans.add(i);
            // 現在得到了一個大小為 i 的物品，用 i 計算完全背包（空間優化寫法）
            for (int j = i; j <= n; j++) {
                f[j] += f[j - i];
            }
        }
        return ans;
    }


}
