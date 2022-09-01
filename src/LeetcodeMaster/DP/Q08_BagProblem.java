package LeetcodeMaster.DP;

public class Q08_BagProblem {
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E8%83%8C%E5%8C%85%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%8001%E8%83%8C%E5%8C%85-1.md
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E8%83%8C%E5%8C%85%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%8001%E8%83%8C%E5%8C%85-2.md


    public static void main(String[] args) {
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagsize = 4;
        testweightbagproblem(weight, value, bagsize);
    }

    public static void testweightbagproblem(int[] weight, int[] value, int bagsize) {
        int wlen = weight.length, value0 = 0;

        // 定義dp數組：dp[i][j]表示背包容量為j時，前i個物品能獲得的最大價值
        int[][] dp = new int[wlen + 1][bagsize + 1];

        // 初始化：背包容量為0時，能獲得的價值都為0
        for (int i = 0; i <= wlen; i++) {
            dp[i][0] = value0;
        }

        // 遍歷順序：先遍歷物品，再遍歷背包容量
        for (int i = 1; i <= wlen; i++) {
            for (int j = 1; j <= bagsize; j++) {
                if (j < weight[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        // 打印dp數組
        for (int i = 0; i <= wlen; i++) {
            for (int j = 0; j <= bagsize; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.print("\n");
        }
    }


    // 滾動數組
    public static void testWeightBagProblem2(int[] weight, int[] value, int bagWeight) {
        int wLen = weight.length;

        // 定義dp數組：dp[j]表示背包容量為j時，能獲得的最大價值
        int[] dp = new int[bagWeight + 1];

        // 遍歷順序：先遍歷物品，再遍歷背包容量
        for (int i = 0; i < wLen; i++) {
            for (int j = bagWeight; j >= weight[i]; j--) { // 倒序是為了確保物品只進入一次，不重複進入背包
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        // 打印dp數組
        for (int j = 0; j <= bagWeight; j++) {
            System.out.print(dp[j] + " ");
        }
    }
}
