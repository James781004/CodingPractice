package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.Arrays;

public class NumberOfPairs {

    // https://leetcode.cn/problems/find-the-number-of-ways-to-place-people-ii/solutions/2630655/on2-you-ya-mei-ju-by-endlesscheng-z86d/
    public int numberOfPairs(int[][] points) {
        // x 升序，y 降序
        Arrays.sort(points, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        int ans = 0;
        for (int i = 0; i < points.length; i++) {
            int y1 = points[i][1];
            int maxY = Integer.MIN_VALUE;
            for (int j = i + 1; j < points.length && maxY < y1; j++) {
                int y2 = points[j][1];
                if (y2 <= y1 && y2 > maxY) {
                    maxY = y2;
                    ans++;
                }
            }
        }
        return ans;
    }


}
