package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class MaxNonDecreasingLength {

    // https://leetcode.cn/problems/longest-non-decreasing-subarray-from-two-arrays/solutions/2336749/dong-tai-gui-hua-cong-ji-yi-hua-sou-suo-hxalb/
    public int maxNonDecreasingLength(int[] nums1, int[] nums2) {
        int n = nums1.length;
        if (n == 1) return 1;
        int[][] dp = new int[n][2];
        dp[0][0] = dp[0][1] = 1;
        int ans = 0;
        for (int i = 1; i < n; i++) {
            // 給定一個初值，可以理解為以i處元素結尾的連續子數組的長度為1
            // 這樣可以保證在i處元素都小於nums1[i-1]和nums2[i-1]時有默認值
            dp[i][0] = dp[i][1] = 1;
            // 1 當nums1[i] >= nums1[i-1]時，此時以nums1[i]結尾的連續子數組的長度就是以nums1[i-1]結尾的連續子數組長度+1
            if (nums1[i] >= nums1[i - 1]) dp[i][0] = Math.max(dp[i][0], dp[i - 1][0] + 1);
            // 2 當nums1[i] >= nums2[i-1]時，此時以nums1[i]結尾的連續子數組的長度就是以nums2[i-1]結尾的連續子數組長度+1
            if (nums1[i] >= nums2[i - 1]) dp[i][0] = Math.max(dp[i][0], dp[i - 1][1] + 1);
            // 3 當nums2[i] >= nums1[i-1]時，此時以nums2[i]結尾的連續子數組的長度就是以nums1[i-1]結尾的連續子數組長度+1
            if (nums2[i] >= nums1[i - 1]) dp[i][1] = Math.max(dp[i][1], dp[i - 1][0] + 1);
            // 4 當nums2[i] >= nums2[i-1]時，此時以nums2[i]結尾的連續子數組的長度就是以nums2[i-1]結尾的連續子數組長度+1
            if (nums2[i] >= nums2[i - 1]) dp[i][1] = Math.max(dp[i][1], dp[i - 1][1] + 1);
            // 比較連續子數組的最大長度
            ans = Math.max(ans, Math.max(dp[i][0], dp[i][1]));
        }

        return ans;
    }

}
