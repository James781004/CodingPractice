package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

import java.util.ArrayList;
import java.util.List;

public class MinimumMoves {

    // https://leetcode.cn/problems/minimum-moves-to-spread-stones-over-grid/solutions/2435313/tong-yong-zuo-fa-zui-xiao-fei-yong-zui-d-iuw8/
    public int minimumMoves(int[][] grid) {
        int n = grid.length;
        // 求石頭數>1的格子和=0的個數
        List<int[]> more = new ArrayList<>(n * n - 1);
        List<int[]> less = new ArrayList<>(n * n - 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 1) {
                    // 超過1幾個，就重復添加幾個坐標
                    for (int k = 2; k <= grid[i][j]; k++) {
                        more.add(new int[]{i, j});
                    }
                } else if (grid[i][j] == 0) {
                    less.add(new int[]{i, j});
                }
            }
        }
        // 由於總數不超過9，可以利用二進制壓縮，枚舉more的全排列
        int size = 1 << more.size();
        // dp[s]: 當前需要選擇的more下標集合移動到對應的less集合所需的最少代價
        int[] dp = new int[size];

        for (int s = 1; s < size; s++) {
            // 當前選擇more對應的的less下標
            int i = more.size() - Integer.bitCount(s);
            int minCost = Integer.MAX_VALUE;
            for (int j = 0; j < more.size(); j++) {
                if ((s >> j & 1) == 1) {
                    int[] from = more.get(j);
                    int[] to = less.get(i);
                    int distance = Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
                    minCost = Math.min(minCost, distance + dp[s ^ (1 << j)]);
                }
            }
            dp[s] = minCost;
        }
        return dp[size - 1];
    }

}
