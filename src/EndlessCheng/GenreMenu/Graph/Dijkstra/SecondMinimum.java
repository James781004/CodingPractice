package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class SecondMinimum {

    // https://leetcode.cn/problems/second-minimum-time-to-reach-destination/solutions/1051494/bfs-qiu-ci-duan-lu-fu-xiang-xi-zhu-shi-b-igf7/
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (var a : edges) {
            int x = a[0] - 1, y = a[1] - 1;
            g[x].add(y);
            g[y].add(x);
        }
        int[][] dis = new int[n][2];
        for (var a : dis) Arrays.fill(a, Integer.MAX_VALUE);
        dis[0][0] = 0;
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        q.offer(new int[]{0, 0}); // [最短路, 次短路]
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], t = cur[1];
            if (t > dis[x][1])
                continue;
            int cnt = t / change;
            for (var a : g[x]) {
                int d = 0; // 到達節點 w 的時間
                if (cnt % 2 == 1)
                    d = (cnt + 1) * change + time;
                else
                    d = t + time;
                if (d < dis[a][0]) { // d 比最短路小，就更新最短路
                    dis[a][0] = d;
                    q.offer(new int[]{a, d});
                } else if (d > dis[a][0] && d < dis[a][1]) { // d 比最短路大又比次短路小，就更新次短路
                    dis[a][1] = d;
                    q.offer(new int[]{a, d});
                }
            }
        }
        return dis[n - 1][1];
    }
}
