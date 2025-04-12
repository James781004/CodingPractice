package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MinimumTotalDistance {

    // https://leetcode.cn/problems/minimum-total-distance-traveled/solutions/1951947/ji-yi-hua-sou-suo-by-endlesscheng-qctr/
    // f[i][j]表示第i~n-1個工廠處理第j~m-1個機器人的代價總和
    long[][] f;
    long inf = (long) 1e13;

    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        int m = robot.size(), n = factory.length;
        Arrays.sort(factory, (o, p) -> o[0] - p[0]);
        Collections.sort(robot);
        f = new long[n][m];
        for (int i = 0; i < n; i++)
            Arrays.fill(f[i], inf);
        return dfs(0, 0, robot, factory);
    }

    public long dfs(int i, int j, List<Integer> robot, int[][] factory) {
        int m = robot.size(), n = factory.length;
        // 如果到第i個工廠時，所有的機器人都被處理完畢
        if (j == m) {
            return 0;
        }
        // 處理最後一個工廠
        if (i == n - 1) {
            // 當前剩余未處理的機器人個數大於最後一個工廠的limit
            // 表示無法以題目的要求處理完成，返回一個無窮大
            if (m - j > factory[i][1]) {
                return inf;
            }
            // 否則，計算剩下的機器人到最後一個工廠的距離之和
            long ans = 0;
            for (int k = j; k < m; k++)
                ans += Math.abs(robot.get(k) - factory[i][0]);
            f[i][j] = ans;
            return ans;
        }
        // 如果當前狀態已經計算過就直接返回
        if (f[i][j] != inf)
            return f[i][j];
        // 當前工廠不處理任何機器人的情況
        long ans = dfs(i + 1, j, robot, factory);
        // 記錄當前工廠處理的機器人與工廠的距離總和
        long dis = 0;
        for (int k = j; k < Math.min(m, j + factory[i][1]); k++) {
            dis += Math.abs(robot.get(k) - factory[i][0]);
            long c = dfs(i + 1, k + 1, robot, factory);
            ans = Math.min(ans, c + dis);
        }
        f[i][j] = ans;
        return ans;
    }
}
