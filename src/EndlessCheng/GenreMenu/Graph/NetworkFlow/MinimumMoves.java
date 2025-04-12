package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.*;

public class MinimumMoves {

    // https://leetcode.cn/problems/minimum-moves-to-spread-stones-over-grid/solutions/2435313/tong-yong-zuo-fa-zui-xiao-fei-yong-zui-d-iuw8/
    private static final int INF = (int) 1e9;

    public int minimumMoves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int src = m * n, dst = src + 1;

        // 建立圖的鄰接表結構，graph[i] 存放與節點 i 相連的邊
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= dst; i++) {
            graph.add(new ArrayList<>());
        }

        // 遍歷整個網格，建立流網絡
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                // 如果該位置的值大於 1，代表有多於 1 顆石頭需要移動
                if (grid[x][y] > 1) {
                    // 建立從源點 (src) 到該格子的邊，容量為 grid[x][y] - 1
                    addEdge(graph, src, x * n + y, grid[x][y] - 1, 0);
                    // 尋找所有可以放置石頭的位置（值為 0）
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j < n; j++) {
                            if (grid[i][j] == 0) {
                                // 建立從該格子到空格的邊，容量 1，費用為曼哈頓距離
                                addEdge(graph, x * n + y, i * n + j, 1, Math.abs(x - i) + Math.abs(y - j));
                            }
                        }
                    }
                } else if (grid[x][y] == 0) {
                    // 如果該格子是空的，則建立到終點 (dst) 的邊，容量為 1
                    addEdge(graph, x * n + y, dst, 1, 0);
                }
            }
        }

        // 計算最小費用最大流
        return minCostMaxFlow(graph, src, dst);
    }

    // 定義邊的結構，包括目標節點、反向邊索引、容量和費用
    private static class Edge {
        int to, rev, cap, cost;

        Edge(int to, int rev, int cap, int cost) {
            this.to = to;
            this.rev = rev;
            this.cap = cap;
            this.cost = cost;
        }
    }

    // 向圖中添加一條邊
    private void addEdge(List<List<Edge>> graph, int from, int to, int cap, int cost) {
        // 正向邊：從 from 到 to，容量 cap，費用 cost
        graph.get(from).add(new Edge(to, graph.get(to).size(), cap, cost));
        // 反向邊：從 to 到 from，容量 0，費用為負 cost
        graph.get(to).add(new Edge(from, graph.get(from).size() - 1, 0, -cost));
    }

    // 計算最小費用最大流
    private int minCostMaxFlow(List<List<Edge>> graph, int src, int dst) {
        int flow = 0, cost = 0;
        int[] dist = new int[graph.size()]; // 記錄到各節點的最短路徑費用
        boolean[] inQueue = new boolean[graph.size()]; // 標記節點是否在隊列中
        Edge[] prev = new Edge[graph.size()]; // 記錄增廣路徑上的邊

        while (true) {
            Arrays.fill(dist, INF); // 初始化所有距離為無窮大
            dist[src] = 0;
            Arrays.fill(inQueue, false);

            Queue<Integer> queue = new LinkedList<>();
            queue.offer(src);

            // 使用 SPFA（Shortest Path Faster Algorithm）尋找最短增廣路徑
            while (!queue.isEmpty()) {
                int u = queue.poll();
                inQueue[u] = false;

                for (Edge e : graph.get(u)) {
                    // 如果還有剩餘容量且可以降低總費用，則更新最短路徑
                    if (e.cap > 0 && dist[e.to] > dist[u] + e.cost) {
                        dist[e.to] = dist[u] + e.cost;
                        prev[e.to] = e;
                        if (!inQueue[e.to]) {
                            inQueue[e.to] = true;
                            queue.offer(e.to);
                        }
                    }
                }
            }

            // 如果無法到達匯點，則退出
            if (dist[dst] == INF) break;

            // 找到該增廣路徑的最小可用流量
            int aug = INF;
            for (int u = dst; u != src; u = graph.get(u).get(prev[u].rev).to) {
                aug = Math.min(aug, prev[u].cap);
            }

            // 沿著增廣路徑更新流量
            for (int u = dst; u != src; u = graph.get(u).get(prev[u].rev).to) {
                prev[u].cap -= aug;
                graph.get(u).get(prev[u].rev).cap += aug;
            }

            // 累加總流量與總費用
            flow += aug;
            cost += aug * dist[dst];
        }

        return cost; // 返回最小費用
    }
}
