package EndlessCheng.GenreMenu.DP.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinTimeMaxPower {

    // https://leetcode.cn/problems/minimum-time-to-reach-target-with-limited-power/solutions/3988936/dijkstra-suan-fa-fen-ceng-tu-zui-duan-lu-t9yx/
    public long[] minTimeMaxPower(int n, int[][] edges, int power, int[] cost, int source, int target) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            g[e[0]].add(new int[]{e[1], e[2]});
        }

        long[][] f = new long[power + 1][n];
        for (int i = 0; i <= power; i++) {
            Arrays.fill(f[i], Long.MAX_VALUE);
        }
        f[power][source] = 0;
        long minDis = Long.MAX_VALUE;
        int maxRem = -1;

        for (int rem = power; rem >= 0; rem--) {
            if (f[rem][target] < minDis) {
                minDis = f[rem][target];
                maxRem = rem;
            }
            for (int x = 0; x < n; x++) {
                long v = f[rem][x];
                if (v == Long.MAX_VALUE || rem < cost[x]) {
                    continue;
                }
                int nxtRem = rem - cost[x];
                for (int[] e : g[x]) {
                    f[nxtRem][e[0]] = Math.min(f[nxtRem][e[0]], v + e[1]); // 刷表法
                }
            }
        }

        return maxRem < 0 ? new long[]{-1, -1} : new long[]{minDis, maxRem};
    }

}
