package EndlessCheng.GenreMenu.MonoStack.Rectangle;

import java.util.ArrayDeque;
import java.util.Deque;

public class NumSubmat {

    // https://leetcode.cn/problems/count-submatrices-with-all-ones/solutions/3005108/1504-tong-ji-quan-1-zi-ju-xing-by-storms-3aix/
    public int numSubmat(int[][] mat) {
        int submatrices = 0;
        int m = mat.length, n = mat[0].length;
        int[] heights = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    heights[j] = 0;
                } else {
                    heights[j]++;
                }
                int height = Integer.MAX_VALUE;
                for (int k = j; k >= 0 && height > 0; k--) {
                    height = Math.min(height, heights[k]);
                    submatrices += height;
                }
            }
        }
        return submatrices;
    }


    public int numSubmat2(int[][] mat) {
        int submatrices = 0;
        int m = mat.length, n = mat[0].length;
        int[] heights = new int[n];
        for (int i = 0; i < m; i++) {
            int currSubmatrices = 0;
            Deque<int[]> stack = new ArrayDeque<int[]>();
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    heights[j] = 0;
                } else {
                    heights[j]++;
                }
                int width = 1;
                int height = heights[j];
                while (!stack.isEmpty() && stack.peek()[1] >= height) {
                    int[] prev = stack.pop();
                    currSubmatrices -= prev[0] * (prev[1] - height);
                    width += prev[0];
                }
                currSubmatrices += height;
                submatrices += currSubmatrices;
                stack.push(new int[]{width, height});
            }
        }
        return submatrices;
    }


}
