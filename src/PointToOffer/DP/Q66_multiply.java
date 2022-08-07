package PointToOffer.DP;

public class Q66_multiply {

    // 构建一维数组dp， dp[i] 表示A[0]到A[i]所对应的乘积
    // 状态初始化，temp相当于保存左下角的乘积
    // 从dp[2]开始动态规划更新
    // 遇到A[i] = 1 则跳过计算，乘积结果也不变
    // 实现 dp = dp * A[i];
    // 拼接两部分乘积，保存下一部分的乘积
    public int[] multiply(int[] A) {
        if (A == null || A.length == 0) {
            return new int[0];
        }
        // dp[i] 表示A[0]到A[i]所对应的乘积
        int[] dp = new int[A.length];
        // 状态初始化
        dp[0] = A[1];
        dp[1] = A[0];
        // temp 相当于保存左下角的乘积
        int temp = dp[0] * dp[1];
        // 从dp[2]开始动态规划更新
        for (int i = 2; i < A.length; i++) {
            // 1 则跳过计算，乘积结果也不变
            if (A[i] != 1) {
                // 实现 dp = dp * A[i];
                // 相当于在表格分区中，dp[j]对A[i]列累乘
                for (int j = 0; j < i; j++) {
                    dp[j] *= A[i];
                }
            }
            // 拼接乘积左下和右上部分的乘积
            dp[i] = temp;
            // temp 相当于左下角的乘积
            temp *= A[i];
        }
        return dp;
    }
}
