package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

import java.util.Arrays;

public class MinimumTimeRequired {

    // https://leetcode.cn/problems/find-minimum-time-to-finish-all-jobs/solutions/554095/zhuang-ya-dp-jing-dian-tao-lu-xin-shou-j-3w7r/
    public int minimumTimeRequired(int[] jobs, int k) {
        int n = jobs.length;
        int[] time = new int[1 << n];
        // 預處理每一個集合的完成時間
        for (int i = 0; i < 1 << n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i >> j & 1) == 1) time[i] += jobs[j];
            }
        }

        // f[state][i]表示當前集合為state, 且用到前i個工人的最小的最大工作時間
        int[][] f = new int[1 << n][k + 1];
        // 由於求最小值，初始化成一個大數
        for (int i = 0; i < 1 << n; i++) Arrays.fill(f[i], (int) 1e9);
        // 當前集合為0, 且用0個工人為合理狀態，且完成時間為0
        f[0][0] = 0;
        // 枚舉用了多少個工人
        for (int u = 1; u <= k; u++) {
            // 枚舉集合
            for (int state = 1; state < 1 << n; state++) {
                // 枚舉子集
                for (int sub = state; sub > 0; sub = (sub - 1) & state) {
                    // 先求出在state這個狀態下所有劃分出的所有子集與其差集的最大值
                    int t = Math.max(f[state - sub][u - 1], time[sub]);
                    // 求所有劃分方案中的最小值
                    f[state][u] = Math.min(f[state][u], t);
                }
            }
        }
        return f[(1 << n) - 1][k];
    }

}
