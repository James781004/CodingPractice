package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

public class MinSessions {

    // https://leetcode.cn/problems/minimum-number-of-work-sessions-to-finish-the-tasks/solutions/964986/zi-ji-dong-tai-gui-hua-by-endlesscheng-wtua/
    public int minSessions(int[] tasks, int sessionTime) {
        int n = tasks.length;
        int size = 1 << n;
        int[] sum = new int[size];
        // 預處理所有子集的子集和，復雜度 O(1+2+4+...+2^(n-1)) = O(2^n)
        for (int i = 0; i < n; i++) {
            for (int j = 0, s = 1 << i; j < s; j++) {
                // 刷表法:前i個數的貢獻=前i-1個數的貢獻+第i個數的貢獻
                sum[j | s] = sum[j] + tasks[i];
            }
        }
        // dp[s]:集合s的任務所需的最少工作時間段
        int[] dp = new int[size];
        for (int s = 1; s < size; s++) {
            dp[s] = n;
            for (int sub = s; sub > 0; sub = (sub - 1) & s) {
                // 枚舉任務子集，找到耗時小於sessionTime的子集
                if (sum[sub] <= sessionTime) {
                    dp[s] = Math.min(dp[s], dp[s ^ sub] + 1);
                }
            }
        }
        return dp[size - 1];
    }

}
