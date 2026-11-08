package EndlessCheng.GenreMenu.DataStructure.EnumerateDiagonal.Basic;

import java.util.Arrays;

public class NumberOfRightTriangles {

    // https://leetcode.cn/problems/right-triangles/solutions/2758892/cheng-fa-yuan-li-pythonjavacgo-by-endles-7469/
    public long numberOfRightTriangles(int[][] grid) {
        int n = grid[0].length;
        int[] colSum = new int[n];
        Arrays.fill(colSum, -1); // 提前減一
        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                colSum[j] += row[j];
            }
        }

        long ans = 0;
        for (int[] row : grid) {
            int rowSum = -1; // 提前減一
            for (int x : row) {
                rowSum += x;
            }
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 1) {
                    ans += (long) rowSum * colSum[j];
                }
            }
        }
        return ans;
    }

}
