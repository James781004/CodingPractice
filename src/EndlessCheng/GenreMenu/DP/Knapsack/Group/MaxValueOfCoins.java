package EndlessCheng.GenreMenu.DP.Knapsack.Group;

import java.util.List;

public class MaxValueOfCoins {

    // https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/solutions/1371872/zhuan-hua-cheng-fen-zu-bei-bao-pythongoc-3xnk/
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int n = piles.size();
        int[][] memo = new int[n][k + 1];
        return dfs(n - 1, k, piles, memo);
    }

    // 從 piles[0] 到 piles[i] 中，選體積之和至多為 j 的物品時，物品價值之和的最大值
    private int dfs(int i, int j, List<List<Integer>> piles, int[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != 0) { // 之前計算過
            return memo[i][j];
        }
        // 不選這一組中的任何物品
        int res = dfs(i - 1, j, piles, memo);
        // 枚舉選哪個
        int v = 0;
        for (int w = 0; w < Math.min(j, piles.get(i).size()); w++) {
            v += piles.get(i).get(w);
            // w 從 0 開始，物品體積為 w+1
            res = Math.max(res, dfs(i - 1, j - w - 1, piles, memo) + v);
        }
        return memo[i][j] = res;
    }


    public int maxValueOfCoinsDP(List<List<Integer>> piles, int k) {
        int[][] f = new int[piles.size() + 1][k + 1];
        for (int i = 0; i < piles.size(); i++) {
            List<Integer> pile = piles.get(i);
            for (int j = 0; j <= k; j++) {
                // 不選這一組中的任何物品
                f[i + 1][j] = f[i][j];
                // 枚舉選哪個
                int v = 0;
                for (int w = 0; w < Math.min(j, pile.size()); w++) {
                    v += pile.get(w);
                    // w 從 0 開始，物品體積為 w+1
                    f[i + 1][j] = Math.max(f[i + 1][j], f[i][j - w - 1] + v);
                }
            }
        }
        return f[piles.size()][k];
    }


    public int maxValueOfCoinsDP2(List<List<Integer>> piles, int k) {
        int[] f = new int[k + 1];
        int sumN = 0;
        for (List<Integer> Pile : piles) {
            Integer[] pile = Pile.toArray(Integer[]::new); // 轉成數組處理更快更方便
            int n = pile.length;
            for (int i = 1; i < n; i++) {
                pile[i] += pile[i - 1]; // 提前計算 pile 的前綴和
            }
            sumN = Math.min(sumN + n, k);
            for (int j = sumN; j > 0; j--) { // 優化：j 從前 i 個棧的大小之和開始枚舉
                for (int w = 0; w < Math.min(n, j); w++) {
                    f[j] = Math.max(f[j], f[j - w - 1] + pile[w]); // w 從 0 開始，物品體積為 w+1
                }
            }
        }
        return f[k];
    }


}
