package LeetcodeMaster.DP;

public class Q21_HouseRob {
//    198.打家劫舍
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0198.%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8D.md
//
//    你是一個專業的小偷，計劃偷竊沿街的房屋。每間房內都藏有一定的現金，影響你偷竊的唯一制約因素就是相鄰的房屋裝有相互連通的防盜系統，如果兩間相鄰的房屋在同一晚上被小偷闖入，系統會自動報警。
//
//    給定一個代表每個房屋存放金額的非負整數數組，計算你 不觸動警報裝置的情況下 ，一夜之內能夠偷竊到的最高金額。
//
//    示例 1： 輸入：[1,2,3,1] 輸出：4 解釋：偷竊 1 號房屋 (金額 = 1) ，然後偷竊 3 號房屋 (金額 = 3)。   偷竊到的最高金額 = 1 + 3 = 4 。
//
//    示例 2： 輸入：[2,7,9,3,1] 輸出：12 解釋：偷竊 1 號房屋 (金額 = 2), 偷竊 3 號房屋 (金額 = 9)，接著偷竊 5 號房屋 (金額 = 1)。   偷竊到的最高金額 = 2 + 9 + 1 = 12 。
//
//    提示：
//
//            0 <= nums.length <= 100
//            0 <= nums[i] <= 400


    public int rob(int[] nums) {
        if (nums == null) return 0;
        if (nums.length == 1) return nums[0];


        int[] dp = new int[nums.length]; // dp[i]：考慮下標i（包括i）以內的房屋，最多可以偷竊的金額為dp[i]。
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            // 決定dp[i]的因素就是第i房間偷還是不偷。
            // 如果偷第i房間，那麽dp[i] = dp[i - 2] + nums[i]，
            // 即：第i-1房一定是不考慮的，找出 下標i-2（包括i-2）以內的房屋，最多可以偷竊的金額為dp[i-2] 加上第i房間偷到的錢。
            // 如果不偷第i房間，那麽dp[i] = dp[i - 1]，即考慮i-1房
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[nums.length - 1];
    }
}
