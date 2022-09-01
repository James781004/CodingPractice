package LeetcodeMaster.DP;

public class Q32_LengthOfCommonSubarray {
//    718. 最長公共子數組
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0718.%E6%9C%80%E9%95%BF%E9%87%8D%E5%A4%8D%E5%AD%90%E6%95%B0%E7%BB%84.md
//
//    給兩個整數數組 A 和 B ，返回兩個數組中公共的、長度最長的子數組的長度。
//
//    示例：
//
//    輸入： A: [1,2,3,2,1] B: [3,2,1,4,7] 輸出：3 解釋： 長度最長的公共子數組是 [3, 2, 1] 。
//
//    提示：
//
//            1 <= len(A), len(B) <= 1000
//            0 <= A[i], B[i] < 100


    public int findLength(int[] nums1, int[] nums2) {
        int res = 0;
        // dp[i][j] ：以下標i - 1為結尾的A，和以下標j - 1為結尾的B，最長重複子數組長度為dp[i][j]。
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i < nums1.length + 1; i++) {
            for (int j = 1; j < nums2.length + 1; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    res = Math.max(res, dp[i][j]);
                }
            }
        }
        return res;
    }


    // 滾動數組
    public int findLength1(int[] nums1, int[] nums2) {
        int[] dp = new int[nums2.length + 1];
        int res = 0;
        for (int i = 0; i <= nums1.length; i++) {
            for (int j = nums2.length; j > 0; j--) { // 遍歷B數組的時候，從後向前遍歷，這樣避免重複覆蓋。
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[j] = dp[j - 1] + 1;
                } else {
                    dp[j] = 0; // 注意不相等的時候要有賦0的操作
                }
                res = Math.max(res, dp[j]);
            }
        }

        return res;
    }
}
