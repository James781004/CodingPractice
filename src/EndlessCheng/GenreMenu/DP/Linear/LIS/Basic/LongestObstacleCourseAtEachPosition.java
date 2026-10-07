package EndlessCheng.GenreMenu.DP.Linear.LIS.Basic;

public class LongestObstacleCourseAtEachPosition {


    // https://leetcode.cn/problems/find-the-longest-valid-obstacle-course-at-each-position/solutions/922738/ji-suan-mei-ge-qian-zhui-de-zui-chang-sh-d2vd/
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int n = obstacles.length;
        int[] dp = new int[n];
        int[] tails = new int[n];
        int end = 0;
        for (int i = 0; i < n; i++) {
            int idx = upperBound(tails, end, obstacles[i]);
            tails[idx] = obstacles[i];
            if (idx == end) {
                end++;
            }
            dp[i] = idx + 1;
        }
        return dp;
    }

    private int upperBound(int[] nums, int end, int target) {
        int left = 0;
        int right = end;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

}
