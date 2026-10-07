package EndlessCheng.GenreMenu.DP.OtherLinear.Square;

import java.util.Arrays;
import java.util.List;

public class MaxScore {

    // https://leetcode.cn/problems/maximum-difference-score-in-a-grid/solutions/2774823/nao-jin-ji-zhuan-wan-dppythonjavacgo-by-swux7/
    public int maxScore(List<List<Integer>> grid) {
        int ans = Integer.MIN_VALUE;
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] f = new int[m + 1][n + 1]; // 定義 f[i+1][j+1] 表示左上角在 (0,0)，右下角在 (i,j) 的子矩陣的最小值
        Arrays.fill(f[0], Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            f[i + 1][0] = Integer.MAX_VALUE;
            List<Integer> row = grid.get(i);
            for (int j = 0; j < n; j++) {
                int mn = Math.min(f[i + 1][j], f[i][j + 1]);
                int x = row.get(j);
                ans = Math.max(ans, x - mn);
                f[i + 1][j + 1] = Math.min(mn, x);
            }
        }
        return ans;
    }


    public int maxScore2(List<List<Integer>> grid) {
        int ans = Integer.MIN_VALUE;
        int n = grid.get(0).size();
        int[] colMin = new int[n];
        Arrays.fill(colMin, Integer.MAX_VALUE);
        for (List<Integer> row : grid) {
            int preMin = Integer.MAX_VALUE; // colMin[0..j] 的最小值
            for (int j = 0; j < n; j++) {
                int x = row.get(j);
                ans = Math.max(ans, x - Math.min(preMin, colMin[j]));
                colMin[j] = Math.min(colMin[j], x);
                preMin = Math.min(preMin, colMin[j]);
            }
        }
        return ans;
    }


}
