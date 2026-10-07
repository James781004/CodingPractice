package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.HashSet;
import java.util.Set;

public class DifferenceOfDistinctValues {

    // https://leetcode.cn/problems/difference-of-number-of-distinct-values-on-diagonals/solutions/2287367/cong-o503-dao-o502-by-endlesscheng-5wp4/
    public int[][] differenceOfDistinctValues(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] ans = new int[m][n];
        Set<Integer> set = new HashSet<>();

        // 第一排在右上，最後一排在左下
        // 每排從左上到右下
        // 令 k=i-j+n，那麼右上角 k=1，左下角 k=m+n-1
        for (int k = 1; k < m + n; k++) {
            // 核心：計算 j 的最小值和最大值
            int minJ = Math.max(n - k, 0); // i=0 的時候，j=n-k，但不能是負數
            int maxJ = Math.min(m + n - 1 - k, n - 1); // i=m-1 的時候，j=m+n-1-k，但不能超過 n-1

            set.clear();
            for (int j = minJ; j <= maxJ; j++) {
                int i = k + j - n;
                ans[i][j] = set.size(); // topLeft[i][j] == set.size()
                set.add(grid[i][j]);
            }

            set.clear();
            for (int j = maxJ; j >= minJ; j--) {
                int i = k + j - n;
                ans[i][j] = Math.abs(ans[i][j] - set.size()); // bottomRight[i][j] == set.size()
                set.add(grid[i][j]);
            }
        }
        return ans;
    }

}
