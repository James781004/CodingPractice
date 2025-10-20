package EndlessCheng.GenreMenu.DP.Grid.Basic;

import java.util.Arrays;
import java.util.List;

public class MinimumTotal {

    // https://leetcode.cn/problems/triangle/solutions/2997752/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-42r2/
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, Integer.MIN_VALUE); // Integer.MIN_VALUE 表示沒有計算過
        }
        return dfs(triangle, 0, 0, memo);
    }

    private int dfs(List<List<Integer>> triangle, int i, int j, int[][] memo) {
        if (i == triangle.size() - 1) {
            return triangle.get(i).get(j);
        }
        if (memo[i][j] != Integer.MIN_VALUE) {  // 之前計算過
            return memo[i][j];
        }
        return memo[i][j] = Math.min(dfs(triangle, i + 1, j, memo),
                dfs(triangle, i + 1, j + 1, memo)) + triangle.get(i).get(j);
    }


    public int minimumTotalDP(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] f = new int[n][n];
        for (int j = 0; j < n; j++) {
            f[n - 1][j] = triangle.get(n - 1).get(j);
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                f[i][j] = Math.min(f[i + 1][j], f[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return f[0][0];
    }


    public int minimumTotalDP2(List<List<Integer>> f) {
        for (int i = f.size() - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                f.get(i).set(j, f.get(i).get(j) + Math.min(f.get(i + 1).get(j), f.get(i + 1).get(j + 1)));
            }
        }
        return f.get(0).get(0);
    }

}
