package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifiedGraphEdges {

    // https://leetcode.cn/problems/modify-graph-edge-weights/solutions/2278296/xiang-xi-fen-xi-liang-ci-dijkstrachou-mi-gv1m/
    public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {
        List<int[]> g[] = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int x = edges[i][0], y = edges[i][1];
            g[x].add(new int[]{y, i});
            g[y].add(new int[]{x, i}); // 建圖，額外記錄邊的編號
        }

        var dis = new int[n][2];
        for (int i = 0; i < n; i++)
            if (i != source)
                dis[i][0] = dis[i][1] = Integer.MAX_VALUE / 2;

        dijkstra(g, edges, destination, dis, 0, 0);
        int delta = target - dis[destination][0];
        if (delta < 0) // -1 全改為 1 時，最短路比 target 還大
            return new int[][]{};

        dijkstra(g, edges, destination, dis, delta, 1);
        if (dis[destination][1] < target) // 最短路無法再變大，無法達到 target
            return new int[][]{};

        for (int[] e : edges)
            if (e[2] == -1) // 剩余沒修改的邊全部改成 1
                e[2] = 1;
        return edges;
    }

    // 朴素 Dijkstra 算法
    // 這裡 k 表示第一次/第二次
    private void dijkstra(List<int[]> g[], int[][] edges, int destination, int[][] dis, int delta, int k) {
        int n = g.length;
        boolean[] vis = new boolean[n];
        for (; ; ) {
            // 找到當前最短路，去更新它的鄰居的最短路
            // 根據數學歸納法，dis[x][k] 一定是最短路長度
            int x = -1;
            for (int i = 0; i < n; ++i)
                if (!vis[i] && (x < 0 || dis[i][k] < dis[x][k]))
                    x = i;
            if (x == destination) // 起點 source 到終點 destination 的最短路已確定
                return;
            vis[x] = true; // 標記，在後續的循環中無需反復更新 x 到其余點的最短路長度
            for (int[] e : g[x]) {
                int y = e[0], eid = e[1];
                int wt = edges[eid][2];
                if (wt == -1)
                    wt = 1; // -1 改成 1
                if (k == 1 && edges[eid][2] == -1) {
                    // 第二次 Dijkstra，改成 w
                    int w = delta + dis[y][0] - dis[x][1];
                    if (w > wt)
                        edges[eid][2] = wt = w; // 直接在 edges 上修改
                }
                // 更新最短路
                dis[y][k] = Math.min(dis[y][k], dis[x][k] + wt);
            }
        }
    }

}
