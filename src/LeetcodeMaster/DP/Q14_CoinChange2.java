package LeetcodeMaster.DP;

public class Q14_CoinChange2 {
//    518. 零錢兌換 II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0518.%E9%9B%B6%E9%92%B1%E5%85%91%E6%8D%A2II.md
//
//    難度：中等
//
//    給定不同面額的硬幣和一個總金額。寫出函數來計算可以湊成總金額的硬幣組合數。假設每一種面額的硬幣有無限個。
//
//    示例 1:
//
//    輸入: amount = 5, coins = [1, 2, 5] 輸出: 4 解釋: 有四種方式可以湊成總金額: 5=5 5=2+2+1 5=2+1+1+1 5=1+1+1+1+1
//
//    示例 2: 輸入: amount = 3, coins = [2] 輸出: 0 解釋: 只用面額2的硬幣不能湊成總金額3。
//
//    示例 3: 輸入: amount = 10, coins = [10] 輸出: 1
//
//    注意，你可以假設：
//
//            0 <= amount (總金額) <= 5000
//            1 <= coin (硬幣面額) <= 5000
//    硬幣種類不超過 500 種
//    結果符合 32 位符號整數

    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1; // 初始化,0大小的背包, 當然是不裝任何東西了, 就是1種方法

        // 注意遍歷順序，求組合數就是外側遍歷物品，內側遍歷背包容量
        for (int i = 0; i < coins.length; i++) {  // 遍歷物品
            for (int j = coins[i]; j <= amount; j++) { // 遍歷背包容量，從coins[i]開始，以避免重複計算
                dp[j] += dp[j - coins[i]]; // 求組合數，不同排列也是為一種組合
            }
        }
        return dp[amount];
    }

}
