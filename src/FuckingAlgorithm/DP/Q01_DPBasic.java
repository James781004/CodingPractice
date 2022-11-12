package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q01_DPBasic {
//    https://leetcode.cn/problems/fibonacci-number/
//    509. 斐波那契數
//    斐波那契數 （通常用 F(n) 表示）形成的序列稱為 斐波那契數列 。該數列由 0 和 1 開始，後面的每一項數字都是前面兩項數字的和。也就是：
//
//    F(0) = 0，F(1) = 1
//    F(n) = F(n - 1) + F(n - 2)，其中 n > 1
//    給定 n ，請計算 F(n) 。

    // 暴力遞歸
    int fib1(int N) {
        if (N == 1 || N == 2) return 1;
        return fib1(N - 1) + fib1(N - 2);
    }


    // 備忘錄的遞歸解法
    int fib2(int N) {
        // 備忘錄全初始化為 0
        int[] memo = new int[N + 1];
        // 進行帶備忘錄的遞歸
        return helper(memo, N);
    }

    int helper(int[] memo, int n) {
        if (n == 0 || n == 1) return n;
        if (memo[n] != 0) return memo[n];
        memo[n] = helper(memo, n - 1) + helper(memo, n - 2);
        return memo[n];
    }

    // helper 數組的迭代（遞推）解法
    int fib3(int N) {
        if (N == 0) return 0;
        int[] dp = new int[N + 1];
        // base case
        dp[0] = 0;
        dp[1] = 1;
        // 狀態轉移
        for (int i = 2; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[N];
    }

    // 進一步優化，把空間復雜度降為 O(1)
    int fib4(int n) {
        if (n == 0 || n == 1) {
            // base case
            return n;
        }

        // 分別代表 helper[i - 1] 和 helper[i - 2]
        int dp_i_1 = 1, dp_i_2 = 0;
        for (int i = 2; i <= n; i++) {
            // helper[i] = helper[i - 1] + helper[i - 2];
            int dp_i = dp_i_1 + dp_i_2;
            // 滾動更新
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }
        return dp_i_1;
    }


//    https://leetcode.cn/problems/coin-change/
//    322. 零錢兌換
//    給你一個整數數組 coins ，表示不同面額的硬幣；以及一個整數 amount ，表示總金額。
//
//    計算並返回可以湊成總金額所需的 最少的硬幣個數 。如果沒有任何一種硬幣組合能組成總金額，返回 -1 。
//
//    你可以認為每種硬幣的數量是無限的。

    // 1、暴力遞歸
    int coinChange1(int[] coins, int amount) {
        // 題目要求的最終結果是 helper(amount)
        return coinHelper1(coins, amount);
    }

    // 定義：要湊出金額 n，至少要 helper(coins, n) 個硬幣
    int coinHelper1(int[] coins, int amount) {
        // base case
        if (amount == 0) return 0;
        if (amount < 0) return -1;

        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int sub = coinHelper1(coins, amount - coin);
            // 子問題無解則跳過
            if (sub == -1) continue;
            // 在子問題中選擇最優解，然後加一
            res = Math.min(res, sub + 1);
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }


    // 2、帶備忘錄的遞歸
    int coinChange(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        // 備忘錄初始化為一個不會被取到的特殊值，代表還未被計算
        Arrays.fill(memo, -666);

        return coinHelper2(coins, amount, memo);
    }

    private int coinHelper2(int[] coins, int amount, int[] memo) {
        // base case
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        if (memo[amount] != -666) return memo[amount];

        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 計算子問題的結果
            int subProblem = coinHelper2(coins, amount - coin, memo);
            // 子問題無解則跳過
            if (subProblem == -1) continue;
            // 在子問題中選擇最優解，然後加一
            res = Math.min(res, subProblem + 1);
        }
        // 把計算結果存入備忘錄
        memo[amount] = (res == Integer.MAX_VALUE) ? -1 : res;
        return memo[amount];
    }


    // 3、helper 數組的迭代解法
    int coinChange3(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // 數組大小為 amount + 1，初始值也為 amount + 1
        Arrays.fill(dp, amount + 1);

        // base case
        dp[0] = 0;
        // 外層 for 循環在遍歷所有狀態的所有取值
        for (int i = 0; i < dp.length; i++) {
            // 內層 for 循環在求所有選擇的最小值
            for (int coin : coins) {
                // 子問題無解，跳過
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }
}
