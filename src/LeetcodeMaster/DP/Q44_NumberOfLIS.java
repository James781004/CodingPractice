package LeetcodeMaster.DP;

public class Q44_NumberOfLIS {
//    673.最長遞增子序列的個數
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0673.%E6%9C%80%E9%95%BF%E9%80%92%E5%A2%9E%E5%AD%90%E5%BA%8F%E5%88%97%E7%9A%84%E4%B8%AA%E6%95%B0.md
//
//    給定一個未排序的整數數組，找到最長遞增子序列的個數。
//
//    示例 1:
//
//    輸入: [1,3,5,4,7]
//    輸出: 2
//    解釋: 有兩個最長遞增子序列，分別是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
//    示例 2:
//
//    輸入: [2,2,2,2,2]
//    輸出: 5
//    解釋: 最長遞增子序列的長度是1，並且存在5個子序列的長度為1，因此輸出5。


    public int findNumberOfLIS(int[] nums) {
        if (nums.length <= 1) return nums.length;

        int[] dp = new int[nums.length]; // dp[i]：到nums[i]為止的最長遞增子序列長度
        int[] count = new int[nums.length]; // count[i]：到nums[i]為止的最長遞增子序列個數
        for (int i = 0; i < nums.length; i++) { // 初始化狀態
            dp[i] = 1; // dp = [1] * n：代表最長遞增子序列的長度至少為1
            count[i] = 1; // count = [1] * n：代表最長遞增子序列的個數至少為1
        }

        int maxCount = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) { // 出現了遞增序列，原則上需要比較長度: dp[i] = Math.max(dp[i], dp[j] + 1);
                    if (dp[j] + 1 > dp[i]) { // 第 j 個數字為前一個數字的子序列是否更更長
                        // 枚舉區間 [0,i) 的所有數 nums[j]，如果滿足 nums[j] < nums[i]，
                        // 說明 nums[i] 可以接在 nums[j] 後面形成上升子序列，
                        // 此時使用 f[j] 更新f[i]，即有 f[i] = f[j] + 1
                        dp[i] = dp[j] + 1;

                        // 重置count[i]，因為目前的最長遞增子序列長度增加了，但數量沒有增加，還在同一個子序列之中
                        count[i] = count[j];
                    } else if (dp[j] + 1 == dp[i]) { // 最長遞增子序列的長度並沒有增加，但是出現了長度一樣的情況
                        // 一旦相等，到nums[i]為止的最長遞增子序列個數就應該增加了。
                        // 所以最長遞增子序列數量增加 count[i] += count[j]
                        count[i] += count[j];  // 數量增加 count[i] += count[j]
                    }
                }
                if (dp[i] > maxCount) maxCount = dp[i];  // 記錄最長長度
            }
        }

        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (maxCount == dp[i]) result += count[i];  // 累加
        }
        return result;
    }
}
