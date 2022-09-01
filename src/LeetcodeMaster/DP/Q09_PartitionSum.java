package LeetcodeMaster.DP;

public class Q09_PartitionSum {
//    416. 分割等和子集
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0416.%E5%88%86%E5%89%B2%E7%AD%89%E5%92%8C%E5%AD%90%E9%9B%86.md
//
//    題目難易：中等
//
//    給定一個只包含正整數的非空數組。是否可以將這個數組分割成兩個子集，使得兩個子集的元素和相等。
//
//    注意: 每個數組中的元素不會超過 100 數組的大小不會超過 200
//
//    示例 1: 輸入: [1, 5, 11, 5] 輸出: true 解釋: 數組可以分割成 [1, 5, 5] 和 [11].
//
//    示例 2: 輸入: [1, 2, 3, 5] 輸出: false 解釋: 數組不能分割成兩個元素和相等的子集.
//
//            提示：
//
//            1 <= nums.length <= 200
//            1 <= nums[i] <= 100


    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        // 總和為奇數，不能平分
        if (sum % 2 == 1) return false;
        int target = sum / 2;

        // dp[i][j]代表可裝物品為0-i，背包容量為j的情況下，背包內容量的最大價值
        int[][] dp = new int[nums.length][target + 1];

        // 初始化,dp[0][j]的最大價值nums[0](if j > weight[i])
        // dp[i][0]均為0，不用初始化
        for (int j = nums[0]; j <= target; j++) {
            dp[0][j] = nums[0];
        }

        // 遍歷物品，遍歷背包
        // 遞推公式:
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j <= target; j++) {
                // 背包容量可以容納nums[i]，才可以選擇要不要裝進去
                if (j >= nums[i]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - nums[i]] + nums[i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[nums.length - 1][target] == target;
    }

    // 滾動數組
    public boolean canPartition2(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // 總和為奇數，不能平分
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        int[] dp = new int[target + 1];
        for (int i = 0; i < n; i++) {
            for (int j = target; j >= nums[i]; j--) {  // 每一個元素一定是不可重覆放入，所以從大到小遍歷
                // 物品 i 的重量是 nums[i]，其價值也是 nums[i]
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[target] == target;
    }

}
