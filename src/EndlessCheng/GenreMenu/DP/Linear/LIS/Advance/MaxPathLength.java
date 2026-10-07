package EndlessCheng.GenreMenu.DP.Linear.LIS.Advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaxPathLength {

    // https://leetcode.cn/problems/length-of-the-longest-increasing-path/solutions/2917590/pai-xu-lispythonjavacgo-by-endlesscheng-803g/
    public int maxPathLength(int[][] coordinates, int k) {
        int kx = coordinates[k][0];
        int ky = coordinates[k][1];
        Arrays.sort(coordinates, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);

        List<Integer> g = new ArrayList<>();
        for (int[] p : coordinates) {
            int x = p[0];
            int y = p[1];
            if (x < kx && y < ky || x > kx && y > ky) {
                int j = Collections.binarySearch(g, y); // g 沒有重復元素，可以用 binarySearch
                if (j < 0) {
                    j = -j - 1;
                }
                if (j < g.size()) {
                    g.set(j, y);
                } else {
                    g.add(y);
                }
            }
        }
        return g.size() + 1; // 算上 coordinates[k]
    }


}
