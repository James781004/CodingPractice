package EndlessCheng.GenreMenu.DP.Enhance.Other;

public class MaxPoints {

    // https://leetcode.cn/problems/maximum-number-of-points-with-cost/solutions/882211/dp-you-hua-ji-qiao-chai-xiang-qian-hou-z-5vvc/
    public long maxPoints(int[][] points) {
        int m = points.length, n = points[0].length;
        long[] curr = new long[n], prev = new long[n];
        for (int[] point : points) {
            long max = 0;
            // 左鄰居最大值右移要減一，預處理當前位置對應下一行的最大值。
            for (int j = 0; j < n; j++) {
                max = Math.max(max - 1, prev[j]);
                curr[j] = max;
            }
            // 右側更新
            for (int j = n - 1; j >= 0; j--) {
                max = Math.max(max - 1, prev[j]);
                curr[j] = Math.max(max, curr[j]) + point[j];
            }
            prev = curr;
        }
        long ans = -1;
        for (long x : prev) ans = Math.max(ans, x);
        return ans;
    }

}
