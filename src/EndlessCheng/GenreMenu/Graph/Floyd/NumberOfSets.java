package EndlessCheng.GenreMenu.Graph.Floyd;

import java.util.Arrays;

public class NumberOfSets {

    // https://leetcode.cn/problems/number-of-possible-sets-of-closing-branches/solutions/2560722/er-jin-zhi-mei-ju-floydgao-xiao-xie-fa-f-t7ou/
    public int numberOfSets(int n, int maxDistance, int[][] roads) {
        int[][] g = new int[n][n];
        for (int[] row : g) {
            Arrays.fill(row, Integer.MAX_VALUE / 2);
        }
        for (int[] e : roads) {
            int x = e[0];
            int y = e[1];
            int wt = e[2];
            g[x][y] = Math.min(g[x][y], wt);
            g[y][x] = Math.min(g[y][x], wt);
        }

        int ans = 1; // s=0 一定滿足要求
        int[][][] f = new int[1 << n][n][n]; // f[S][i][j] 表示從 i 到 j 的最短路長度，並且這條最短路的中間節點編號在集合 S 中
        for (int[][] matrix : f) {
            for (int[] row : matrix) {
                Arrays.fill(row, Integer.MAX_VALUE / 2); // 防止加法溢出
            }
        }
        f[0] = g;
        for (int s = 1; s < (1 << n); s++) {
            int k = Integer.numberOfTrailingZeros(s);
            int t = s ^ (1 << k);
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // 不影響 i 到 j 的最短路：f[S][i][j]=f[T][i][j]
                    // 影響 i 到 j 的最短路，也就是從 i 到 k 再到 j 更短：[S][i][j]=f[T][i][k]+f[T][k][j]
                    f[s][i][j] = Math.min(f[t][i][j], f[t][i][k] + f[t][k][j]);
                    if (ok && j < i && (s >> i & 1) != 0 && (s >> j & 1) != 0 && f[s][i][j] > maxDistance) {
                        ok = false;
                    }
                }
            }
            ans += ok ? 1 : 0;
        }
        return ans;
    }

}
