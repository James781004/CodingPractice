package EndlessCheng.GenreMenu.DP.StateCompression.SumOverSubsets;

import java.util.Arrays;
import java.util.List;

public class GoodSubsetofBinaryMatrix {

    // https://leetcode.cn/problems/find-a-good-subset-of-the-matrix/solutions/2305490/xiang-xi-fen-xi-wei-shi-yao-zhi-duo-kao-mbl6a/
    public List<Integer> goodSubsetofBinaryMatrix(int[][] grid) {
        int n = grid[0].length;
        int[] f = new int[1 << n];
        Arrays.fill(f, -1);
        for (int i = 0; i < grid.length; i++) {
            int mask = 0;
            for (int j = 0; j < n; j++) {
                mask |= grid[i][j] << j;
            }
            if (mask == 0) {
                return List.of(i);
            }
            f[mask] = i;
        }

        int u = (1 << n) - 1;
        for (int b = 0; b < n; b++) {
            for (int s = 1; s < u; s++) {
                s |= 1 << b;
                f[s] = Math.max(f[s], f[s ^ (1 << b)]);
                int i = f[s];
                if (i < 0) {
                    continue;
                }
                int j = f[u ^ s];
                if (j >= 0) {
                    return i < j ? List.of(i, j) : List.of(j, i);
                }
            }
        }
        return List.of();
    }


}
