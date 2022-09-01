package LeetcodeMaster.DP;

public class Q10_LastStone2 {
//    1049. 最後一塊石頭的重量 II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1049.%E6%9C%80%E5%90%8E%E4%B8%80%E5%9D%97%E7%9F%B3%E5%A4%B4%E7%9A%84%E9%87%8D%E9%87%8FII.md
//
//    題目難度：中等
//
//    有一堆石頭，每塊石頭的重量都是正整數。
//
//    每一回合，從中選出任意兩塊石頭，然後將它們一起粉碎。假設石頭的重量分別為 x 和 y，且 x <= y。那麼粉碎的可能結果如下：
//
//    如果 x == y，那麼兩塊石頭都會被完全粉碎；
//    如果 x != y，那麼重量為 x 的石頭將會完全粉碎，而重量為 y 的石頭新重量為 y-x。
//    最後，最多只會剩下一塊石頭。返回此石頭最小的可能重量。如果沒有石頭剩下，就返回 0。
//
//    示例： 輸入：[2,7,4,1,8,1] 輸出：1 解釋： 組合 2 和 4，得到 2，所以數組轉化為 [2,7,1,8,1]，
//    組合 7 和 8，得到 1，所以數組轉化為 [2,1,1,1]， 組合 2 和 1，得到 1，所以數組轉化為 [1,1,1]，
//    組合 1 和 1，得到 0，所以數組轉化為 [1]，這就是最優值。
//
//    提示：
//
//            1 <= stones.length <= 30
//            1 <= stones[i] <= 1000


    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int s : stones) {
            sum += s;
        }

        int target = sum / 2;

        // 初始化，dp[i][j]為可以放0-i物品，背包容量為j的情況下背包中的最大價值
        int[][] dp = new int[stones.length][target + 1];

        // dp[i][0]默認初始化為0
        // dp[0][j]取決於stones[0]
        for (int j = stones[0]; j <= target; j++) { // 注意是等於
            dp[0][j] = stones[0];
        }

        for (int i = 1; i < stones.length; i++) {
            for (int j = 1; j <= target; j++) { // 注意是等於
                if (j >= stones[i]) {
                    // 不放:dp[i - 1][j]
                    // 放:dp[i - 1][j - stones[i]] + stones[i]
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - stones[i]] + stones[i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 在計算target的時候，target = sum / 2 因為是向下取整，所以sum - dp[target] 一定是大於等於dp[target]的。
        // 那麽相撞之後剩下的最小石頭重量就是 (sum - dp[target]) - dp[target]。
        System.out.println(dp[stones.length - 1][target]);
        return (sum - dp[stones.length - 1][target]) - dp[stones.length - 1][target];
    }

    // 滾動數組
    public int lastStoneWeightII2(int[] stones) {
        int sum = 0;
        for (int i : stones) {
            sum += i;
        }
        int target = sum >> 1;
        // 初始化dp數組
        int[] dp = new int[target + 1];
        for (int i = 0; i < stones.length; i++) {
            // 采用倒序以避免相同物品重複進入背包
            for (int j = target; j >= stones[i]; j--) {
                // 兩種情況，要麽放，要麽不放
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }

        // 在計算target的時候，target = sum / 2 因為是向下取整，所以sum - dp[target] 一定是大於等於dp[target]的。
        // 那麽相撞之後剩下的最小石頭重量就是 (sum - dp[target]) - dp[target]。
        return sum - 2 * dp[target];
    }
}
