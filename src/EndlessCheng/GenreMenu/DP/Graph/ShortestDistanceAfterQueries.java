package EndlessCheng.GenreMenu.DP.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortestDistanceAfterQueries {

    // https://leetcode.cn/problems/shortest-distance-after-road-addition-queries-i/solutions/2869215/liang-chong-fang-fa-bfs-dppythonjavacgo-mgunf/
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        List<Integer>[] from = new ArrayList[n]; // from[i] 記錄額外添加的邊的終點是 i，起點列表是 from[i]
        Arrays.setAll(from, i -> new ArrayList<>());
        int[] f = new int[n]; // f[i] 為從 0 到 i 的最短路
        for (int i = 1; i < n; i++) {
            f[i] = i;
        }

        int[] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int l = queries[qi][0];
            int r = queries[qi][1];
            from[r].add(l);
            if (f[l] + 1 < f[r]) {
                f[r] = f[l] + 1;
                for (int i = r + 1; i < n; i++) {
                    f[i] = Math.min(f[i], f[i - 1] + 1);
                    for (int j : from[i]) {
                        f[i] = Math.min(f[i], f[j] + 1);
                    }
                }
            }
            ans[qi] = f[n - 1];
        }
        return ans;
    }

}
