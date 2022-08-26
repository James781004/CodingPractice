package LeetcodeMaster.Greedy;

public class Q02_WiggleArray {
//    376. 擺動序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0376.%E6%91%86%E5%8A%A8%E5%BA%8F%E5%88%97.md
//
//    如果連續數字之間的差嚴格地在正數和負數之間交替，則數字序列稱為擺動序列。第一個差（如果存在的話）可能是正數或負數。少於兩個元素的序列也是擺動序列。
//
//    例如， [1,7,4,9,2,5] 是一個擺動序列，因為差值 (6,-3,5,-7,3) 是正負交替出現的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是擺動序列，第一個序列是因為它的前兩個差值都是正數，第二個序列是因為它的最後一個差值為零。
//
//    給定一個整數序列，返回作為擺動序列的最長子序列的長度。 通過從原始序列中刪除一些（也可以不刪除）元素來獲得子序列，剩下的元素保持其原始順序。
//
//    示例 1:
//
//    輸入: [1,7,4,9,2,5]
//    輸出: 6
//    解釋: 整個序列均為擺動序列。
//    示例 2:
//
//    輸入: [1,17,5,10,13,15,10,5,16,8]
//    輸出: 7
//    解釋: 這個序列包含幾個長度為 7 擺動序列，其中一個可為[1,17,10,13,10,16,8]。
//    示例 3:
//
//    輸入: [1,2,3,4,5,6,7,8,9]
//    輸出: 2

    public int wiggleMaxLength(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        // 當前差值
        int curDiff = 0;
        // 上一個差值
        int preDiff = 0;
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            // 得到當前差值
            curDiff = nums[i] - nums[i - 1];
            // 如果當前差值和上一個差值為一正一負
            // 等於0的情況表示初始時的preDiff
            if ((curDiff > 0 && preDiff <= 0) || (curDiff < 0 && preDiff >= 0)) {
                count++;
                preDiff = curDiff;
            }
        }
        return count;
    }


    // DP
    public int wiggleMaxLengthDP(int[] nums) {
        // 0 i 作為波峰的最大長度
        // 1 i 作為波谷的最大長度
        int dp[][] = new int[nums.length][2];

        dp[0][0] = dp[0][1] = 1;
        for (int i = 1; i < nums.length; i++) {
            // i 自己可以成為波峰或者波谷
            dp[i][0] = dp[i][1] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    // i 是波谷
                    dp[i][1] = Math.max(dp[i][1], dp[j][0] + 1);
                }

                if (nums[j] < nums[i]) {
                    // i 是波峰
                    dp[i][0] = Math.max(dp[i][0], dp[j][1] + 1);
                }
            }
        }
        return Math.max(dp[nums.length - 1][0], dp[nums.length - 1][1]);
    }
}
