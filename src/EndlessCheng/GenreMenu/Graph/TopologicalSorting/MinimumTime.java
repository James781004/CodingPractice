package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumTime {

    // https://leetcode.cn/problems/parallel-courses-iii/solutions/1063928/tuo-bu-pai-xu-dong-tai-gui-hua-by-endles-dph6/
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        var deg = new int[n];
        for (var r : relations) {
            int x = r[0] - 1, y = r[1] - 1;
            g[x].add(y);
            deg[y]++;
        }

        var q = new ArrayDeque<Integer>();
        for (int i = 0; i < n; i++)
            if (deg[i] == 0) // 沒有先修課
                q.add(i);
        var f = new int[n];
        int ans = 0;
        while (!q.isEmpty()) {
            int x = q.poll(); // x 出隊，意味著 x 的所有先修課都上完了
            f[x] += time[x]; // 加上當前課程的時間，就得到了最終的 f[x]
            ans = Math.max(ans, f[x]);
            for (int y : g[x]) { // 遍歷 x 的鄰居 y
                f[y] = Math.max(f[y], f[x]); // 更新 f[y] 的所有先修課程耗時的最大值
                if (--deg[y] == 0) // y 的先修課已上完
                    q.add(y);
            }
        }
        return ans;
    }


}
