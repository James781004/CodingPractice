package LeetcodeMaster.DP;

public class Q03_MinCostClimbingStairs {
//    746. 使用最小花費爬樓梯
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0746.%E4%BD%BF%E7%94%A8%E6%9C%80%E5%B0%8F%E8%8A%B1%E8%B4%B9%E7%88%AC%E6%A5%BC%E6%A2%AF.md
//
//    數組的每個下標作為一個階梯，第 i 個階梯對應著一個非負數的體力花費值 cost[i]（下標從 0 開始）。
//
//    每當你爬上一個階梯你都要花費對應的體力值，一旦支付了相應的體力值，你就可以選擇向上爬一個階梯或者爬兩個階梯。
//
//    請你找出達到樓層頂部的最低花費。在開始時，你可以選擇從下標為 0 或 1 的元素作為初始階梯。
//
//    示例 1：
//
//    輸入：cost = [10, 15, 20] 輸出：15 解釋：最低花費是從 cost[1] 開始，然後走兩步即可到階梯頂，一共花費 15 。  示例 2：
//
//    輸入：cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1] 輸出：6 解釋：最低花費方式是從 cost[0] 開始，逐個經過那些 1 ，跳過 cost[3] ，一共花費 6 。
//
//    提示：
//
//    cost 的長度範圍是 [2, 1000]。
//    cost[i] 將會是一個整型數據，範圍為 [0, 999] 。

    public int minCostClimbingStairs(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }
        if (cost.length == 1) {
            return cost[0];
        }
        int[] dp = new int[cost.length];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        // 最後一步，如果是由倒數第二步爬，則最後一步的體力花費可以不用算
        return Math.min(dp[cost.length - 1], dp[cost.length - 2]);
    }


    // 優化空間
    public int minCostClimbingStairs2(int[] cost) {
        int a = cost[0];
        int b = cost[1];
        int c;
        for (int i = 2; i < cost.length; i++) {
            c = Math.min(a, b) + cost[i];
            a = b; // 記錄一下前兩位
            b = c;
        }
        return Math.min(a, b);
    }
}
