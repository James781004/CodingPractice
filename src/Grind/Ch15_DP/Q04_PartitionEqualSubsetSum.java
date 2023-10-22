package Grind.Ch15_DP;

public class Q04_PartitionEqualSubsetSum {
    // https://leetcode.cn/problems/partition-equal-subset-sum/solutions/553978/bang-ni-ba-0-1bei-bao-xue-ge-tong-tou-by-px33/
    public boolean canPartition(int[] nums) {
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

        // 如果使用一維dp數組，物品遍歷的for循環放在外層，遍歷背包的for循環放在內層，且內層for循環倒序遍歷！
        // https://leetcode.cn/link/?target=https%3A%2F%2Fprogrammercarl.com%2F%E8%83%8C%E5%8C%85%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%8001%E8%83%8C%E5%8C%85-2.html
        for (int i = 0; i < n; i++) {
            for (int j = target; j >= nums[i]; j--) {
                // 物品 i 的重量是 nums[i]，其價值也是 nums[i]
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }

        return dp[target] == target;
    }


    // 二維數組版本（易於理解）：
    public static boolean canPartition2(int[] nums) {
        int len = nums.length;
        // 題目已經說非空數組，可以不做非空判斷
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // 特判：如果是奇數，就不符合要求
        if ((sum % 2) != 0) {
            return false;
        }

        int target = sum / 2; // 目標背包容量

        // 創建二維狀態數組，行：物品索引，列：容量（包括 0）
        /*
        dp[i][j]表示從數組的 [0, i] 這個子區間內挑選一些正整數
          每個數只能用一次，使得這些數的和恰好等於 j。
        */
        boolean[][] dp = new boolean[len][target + 1];

        // 先填表格第 0 行，第 1 個數只能讓容積為它自己的背包恰好裝滿  （這裡的dp[][]數組的含義就是“恰好”，所以就算容積比它大的也不要）
        if (nums[0] <= target) {
            dp[0][nums[0]] = true;
        }
        // 再填表格後面幾行
        // 外層遍歷物品
        for (int i = 1; i < len; i++) {
            // 內層遍歷背包
            for (int j = 0; j <= target; j++) {
                // 直接從上一行先把結果抄下來，然後再修正
                dp[i][j] = dp[i - 1][j];

                // 如果某個物品單獨的重量恰好就等於背包的重量，那麼也是滿足dp數組的定義的
                if (nums[i] == j) {
                    dp[i][j] = true;
                    continue;
                }
                // 如果某個物品的重量小於j，那就可以看該物品是否放入背包
                // dp[i - 1][j]表示該物品不放入背包，如果在 [0, i - 1] 這個子區間內已經有一部分元素，使得它們的和為 j ，那麼 dp[i][j] = true；
                // dp[i - 1][j - nums[i]]表示該物品放入背包。如果在 [0, i - 1] 這個子區間內就得找到一部分元素，使得它們的和為 j - nums[i]。
                if (nums[i] < j) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }

        return dp[len - 1][target];
    }
}
