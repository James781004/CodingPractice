package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

import java.util.Arrays;

public class CanDistribute {

    // https://leetcode.cn/problems/distribute-repeating-integers/solutions/3745468/zhuang-tai-ya-suo-ji-yi-hua-sou-suo-zhua-3idy/

    /**
     * 主方法：判斷是否可以將數字分配給顧客
     *
     * @param nums     可用的數字數組
     * @param quantity 每個顧客的需求量
     * @return 是否可以滿足所有顧客需求
     */
    public static boolean canDistribute(int[] nums, int[] quantity) {
        // 統計每種數字的數量
        Arrays.sort(nums);
        int n = 1; // 不同數字的種類數
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] != nums[i]) {
                n++;
            }
        }

        // 統計每種數字的具體數量
        int[] cnt = new int[n];
        int count = 1;
        for (int i = 1, j = 0; i < nums.length; i++) {
            if (nums[i - 1] != nums[i]) {
                cnt[j++] = count;
                count = 1;
            } else {
                count++;
            }
        }
        cnt[n - 1] = count;

        int m = quantity.length; // 顧客數量

        // 預處理所有子集狀態的需求總量
        int[] sum = new int[1 << m];
        for (int i = 0; i < quantity.length; i++) {
            int demand = quantity[i];
            int mask = 1 << i;
            for (int j = 0; j < mask; j++) {
                sum[mask | j] = sum[j] + demand; // 累加子集需求
            }
        }

        // 初始化DP數組，0表示未計算
        int[][] dp = new int[1 << m][n];

        // 從全部需求狀態(1<<m)-1和第0種數字開始
        return dfs(cnt, sum, (1 << m) - 1, 0, dp);
    }

    /**
     * 記憶化搜索
     *
     * @param cnt    每種數字的數量
     * @param sum    子集需求總和數組
     * @param status 當前需求狀態(位掩碼)
     * @param index  當前處理的數字種類索引
     * @param dp     記憶化數組
     * @return 是否可分配
     */
    private static boolean dfs(int[] cnt, int[] sum, int status, int index, int[][] dp) {
        // 所有需求都已滿足
        if (status == 0) {
            return true;
        }
        // 數字已用完但還有未滿足需求
        if (index == cnt.length) {
            return false;
        }
        // 檢查是否已經計算過
        if (dp[status][index] != 0) {
            return dp[status][index] == 1;
        }

        boolean canDistribute = false;
        int available = cnt[index]; // 當前數字的可用數量

        // 核心枚舉：枚舉status的所有非空子集
        for (int subset = status; subset > 0; subset = (subset - 1) & status) {
            // 如果當前數字能滿足這個子集的需求
            if (sum[subset] <= available) {
                // 遞歸處理剩余需求
                if (dfs(cnt, sum, status ^ subset, index + 1, dp)) {
                    canDistribute = true;
                    break;
                }
            }
        }

        // 嘗試不使用當前數字
        if (!canDistribute) {
            canDistribute = dfs(cnt, sum, status, index + 1, dp);
        }

        // 記錄結果並返回
        dp[status][index] = canDistribute ? 1 : -1;
        return canDistribute;
    }

}
