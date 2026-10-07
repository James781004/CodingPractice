package EndlessCheng.GenreMenu.DP.Interval.Interval;

import java.util.Arrays;

public class MaxCoins {

    // https://leetcode.cn/problems/burst-balloons/solutions/2986028/ji-yi-hua-di-gui-dong-tai-gui-hua-by-tia-gxh8/
    int[] nums;
    int[][] memo;

    public int maxCoins(int[] Nums) {
        int m = Nums.length;
        nums = new int[m + 2];
        nums[0] = 1; // 首位添加1
        nums[m + 1] = 1; // 末尾添加1
        System.arraycopy(Nums, 0, nums, 1, m); // 將原始數組 Nums 復制到 nums 的中間部分
        memo = new int[m + 2][m + 2];
        for (int[] mo : memo) { // 初始化記憶化搜索的二維數組 memo，所有元素初始化為 -1
            Arrays.fill(mo, -1);
        }

        // 從 nums[1] 到 nums[m] 之間計算最大金幣數（因為首位和末尾都是1）
        return dfs(0, m + 1);
    }

    // 深度優先搜索函數，計算從 nums[i] 到 nums[j] 之間的最大金幣數
    int dfs(int i, int j) {
        if (i + 1 == j) return 0; // 如果 i > j，說明沒有元素可選，返回0
        if (memo[i][j] != -1) { // 如果 memo[i][j] 已經計算過，直接返回結果，避免重復計算
            return memo[i][j];
        }
        int res = 0; // 初始化最大金幣數
        for (int k = i + 1; k < j; k++) { // 遍歷從 i 到 j 的所有可能的 k，k 是當前選擇的金幣的面值
            // 遞歸計算左右兩側的最大金幣數，並加上當前選擇的金幣面值（nums[i-1] * nums[k] * nums[j+1]）
            res = Math.max(res, dfs(i, k) + dfs(k, j) + nums[i] * nums[k] * nums[j]);
        }
        return memo[i][j] = res; // 將計算結果存入 memo[i][j]，並返回
    }


    public int maxCoinsDP(int[] Nums) {
        int m = Nums.length; // 原始數組的長度

        // 創建一個新的數組nums，長度比Nums多2，用於在首尾添加哨兵值1
        int[] nums = new int[m + 2];
        nums[0] = 1; // 數組的首位添加哨兵值1
        nums[m + 1] = 1; // 數組的末尾添加哨兵值1

        // 將原始數組Nums的值復制到新數組nums的中間部分
        for (int i = 1; i <= m; i++) {
            nums[i] = Nums[i - 1];
        }

        // 創建一個二維動態規劃數組dp，用於存儲從nums[i]到nums[j]之間的最大金幣數
        int[][] dp = new int[m + 2][m + 2];

        // 使用動態規劃填充dp數組，注意這裡是從後往前遍歷，避免使用未計算的dp值
        for (int i = m - 1; i >= 0; i--) { // i表示當前考慮的子數組的起始位置
            for (int j = i + 2; j <= m + 1; j++) { // j表示當前考慮的子數組的結束位置
                // k是枚舉的分割點，將子數組nums[i...j]分為nums[i...k-1]和nums[k+1...j]兩部分
                for (int k = i + 1; k < j; k++) {
                    // 更新dp[i][j]的值，表示選擇nums[k]作為當前金幣時的最大金幣數
                    // 加上左右兩側子數組的最大金幣數以及當前金幣的價值（nums[i]*nums[k]*nums[j]）
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[j]);
                }
            }
        }

        // 返回dp[0][m+1]，表示從nums[0]到nums[m+1]（即整個數組）之間的最大金幣數
        return dp[0][m + 1];
    }


}
