package EndlessCheng.GenreMenu.DataStructure.EnumerateDiagonal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortMatrix {

    // https://leetcode.cn/problems/sort-matrix-by-diagonals/solutions/3068709/mo-ban-mei-ju-dui-jiao-xian-pythonjavacg-pjxp/
    public int[][] sortMatrix(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 第一排在右上，最後一排在左下
        // 每排從左上到右下
        // 令 k=i-j+n，那麼右上角 k=1，左下角 k=m+n-1
        for (int k = 1; k < m + n; k++) {
            // 核心：計算 j 的最小值和最大值
            int minJ = Math.max(n - k, 0); // i=0 的時候，j=n-k，但不能是負數
            int maxJ = Math.min(m + n - 1 - k, n - 1); // i=m-1 的時候，j=m+n-1-k，但不能超過 n-1
            List<Integer> a = new ArrayList<>(maxJ - minJ + 1); // 預分配空間
            for (int j = minJ; j <= maxJ; j++) {
                a.add(grid[k + j - n][j]); // 根據 k 的定義得 i=k+j-n
            }
            a.sort(minJ > 0 ? null : Comparator.reverseOrder());
            for (int j = minJ; j <= maxJ; j++) {
                grid[k + j - n][j] = a.get(j - minJ);
            }
        }
        return grid;
    }

}
