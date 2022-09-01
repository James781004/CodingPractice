package LeetcodeMaster.DP;

public class Q22_HouseRob2 {
//    213.打家劫舍II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0213.%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8DII.md
//
//    你是一個專業的小偷，計劃偷竊沿街的房屋，每間房內都藏有一定的現金。這個地方所有的房屋都 圍成一圈 ，這意味著第一個房屋和最後一個房屋是緊挨著的。同時，相鄰的房屋裝有相互連通的防盜系統，如果兩間相鄰的房屋在同一晚上被小偷闖入，系統會自動報警 。
//
//    給定一個代表每個房屋存放金額的非負整數數組，計算你 在不觸動警報裝置的情況下 ，能夠偷竊到的最高金額。
//
//    示例 1：
//
//    輸入：nums = [2,3,2] 輸出：3 解釋：你不能先偷竊 1 號房屋（金額 = 2），然後偷竊 3 號房屋（金額 = 2）, 因為他們是相鄰的。
//
//    示例 2： 輸入：nums = [1,2,3,1] 輸出：4 解釋：你可以先偷竊 1 號房屋（金額 = 1），然後偷竊 3 號房屋（金額 = 3）。偷竊到的最高金額 = 1 + 3 = 4 。
//
//    示例 3： 輸入：nums = [0] 輸出：0
//
//    提示：
//
//            1 <= nums.length <= 100
//            0 <= nums[i] <= 1000


    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int len = nums.length;
        if (len == 1) return nums[0];

        // 情況一：考慮不包含首尾元素
        // 情況二：考慮包含首元素，不包含尾元素
        // 情況三：考慮包含尾元素，不包含首元素
        // 情況二 和 情況三 都包含了情況一了，所以只考慮情況二和情況三就可以了。
        return Math.max(robAction(nums, 0, len - 1), robAction(nums, 1, len));
    }

    // LC 198 滾動數組優化後
    private int robAction(int[] nums, int start, int end) {
        int step1 = 0, step2 = 0, curMax = 0;
        for (int i = start; i < end; i++) {
            step2 = curMax;
            curMax = Math.max(step2, step1 + nums[i]);
            step1 = step2;
        }
        return curMax;
    }
}
