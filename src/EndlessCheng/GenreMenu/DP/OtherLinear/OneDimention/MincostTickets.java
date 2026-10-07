package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

public class MincostTickets {

    // https://leetcode.cn/problems/minimum-cost-for-tickets/solutions/2936177/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-tkw4/
    public int mincostTickets(int[] days, int[] costs) {
        int lastDay = days[days.length - 1];
        boolean[] isTravel = new boolean[lastDay + 1];
        for (int d : days) {
            isTravel[d] = true;
        }
        int[] memo = new int[lastDay + 1];
        return dfs(lastDay, isTravel, costs, memo);
    }

    //  表示 1 到 i 天的最小花費
    private int dfs(int i, boolean[] isTravel, int[] costs, int[] memo) {
        if (i <= 0) {
            return 0;
        }
        if (memo[i] > 0) { // 之前計算過
            return memo[i];
        }
        if (!isTravel[i]) {
            return memo[i] = dfs(i - 1, isTravel, costs, memo);
        }
        return memo[i] = Math.min(dfs(i - 1, isTravel, costs, memo) + costs[0],
                Math.min(dfs(i - 7, isTravel, costs, memo) + costs[1],
                        dfs(i - 30, isTravel, costs, memo) + costs[2]));
    }


    public int mincostTicketsDP(int[] days, int[] costs) {
        int lastDay = days[days.length - 1];
        boolean[] isTravel = new boolean[lastDay + 1];
        for (int d : days) {
            isTravel[d] = true;
        }
        int[] f = new int[lastDay + 1]; // 表示 1 到 i 天的最小花費
        for (int i = 1; i <= lastDay; i++) {
            if (!isTravel[i]) {
                f[i] = f[i - 1];
            } else {
                f[i] = Math.min(f[i - 1] + costs[0],
                        Math.min(f[Math.max(i - 7, 0)] + costs[1],
                                f[Math.max(i - 30, 0)] + costs[2]));
            }
        }
        return f[lastDay];
    }


    // 三指針優化
    public int mincostTickets2(int[] days, int[] costs) {
        int n = days.length;
        int[] f = new int[n + 1]; // 定義 f[i] 表示旅行了 i 天的最小花費
        int j = 0;
        int k = 0;
        for (int i = 0; i < n; i++) {
            int d = days[i]; // 在 days[i] 購買為期 1 天的通行證
            while (days[j] <= d - 7) { // 在第 days[j] 天購買為期 7 天的通行證
                j++;
            }
            while (days[k] <= d - 30) { // 在第 days[k] 天購買為期 30 天的通行證
                k++;
            }
            f[i + 1] = Math.min(f[i] + costs[0], Math.min(f[j] + costs[1], f[k] + costs[2]));
        }
        return f[n];
    }


}
