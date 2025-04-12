package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuardCastle {

    // https://leetcode.cn/problems/7rLGCR/solutions/715282/java-zui-xiao-ge-by-bu-gou-gg-bondde-liu-wrfr/
    class Solution {
        int INF = 100000; // 定義一個表示無窮大的整數值，用於表示邊的無限容量
        int dir[][] = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 定義一個方向數組，表示上下左右四個移動方向

        public int guardCastle(String[] grid) {
            char A[][] = to(grid); // 將輸入的字符串數組轉換為二維字符數組 A，方便存取
            int N = A.length * A[0].length; // 計算網格中總的單元格數量

            // 創建一個圖，用於表示網路流。圖的大小為 4*N + 10，其中 N 是單元格數量。
            // 這裡的 4*N 是因為每個單元格會被拆成兩個節點（入點和出點），再加上源點、匯點和特殊節點。
            List<Edge>[] graph = MaxFlowDinic.createGraph(4 * N + 10);
            int source = N * 4 + 1; // 定義源點的編號
            int dest = N * 4 + 2; // 定義匯點的編號
            int special = N * 4 + 3; // 定義一個特殊節點，用於處理瞬移點 'P'

            // 遍歷二維字符數組 A，處理每個單元格
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[0].length; j++) {
                    if (A[i][j] == '#') continue; // 如果當前單元格是障礙物 '#'，則跳過
                    int id1 = i * A[0].length + j; // 計算當前單元格的一維編號 (入點)
                    int id2 = id1 + N; // 計算當前單元格的出點編號
                    //connect itself
                    if (A[i][j] == '.') {
                        // 如果當前單元格是平地 '.'，則添加一條從入點到出點的邊，容量為 1。
                        // 這表示放置一個障礙物在這個平地上以阻止惡魔通過的成本為 1。
                        MaxFlowDinic.addEdge(graph, id1, id2, 1);
                    } else {
                        // 如果當前單元格是 'S'、'C' 或 'P'，則添加一條從入點到出點的邊，容量為 INF（無窮大）。
                        // 這表示這些特殊單元格不能直接被阻止通過。
                        MaxFlowDinic.addEdge(graph, id1, id2, INF);
                    }

                    // 處理特殊單元格的連接
                    if (A[i][j] == 'S') {
                        // 如果當前單元格是出生點 'S'，則從其出點連接到匯點，容量為 INF。
                        // 這表示惡魔可以從任何出生點到達匯點（如果沒有被阻擋）。
                        MaxFlowDinic.addEdge(graph, id2, dest, INF);

                    } else if (A[i][j] == 'C') {
                        // 如果當前單元格是城堡 'C'，則從源點連接到其入點，容量為 INF。
                        // 這表示惡魔可以從源點到達任何城堡（如果沒有被阻擋）。
                        MaxFlowDinic.addEdge(graph, source, id1, INF);
                    } else if (A[i][j] == 'P') {
                        // 如果當前單元格是瞬移點 'P'，則從其出點連接到特殊節點，容量為 INF。
                        MaxFlowDinic.addEdge(graph, id2, special, INF);
                        // 同時從特殊節點連接回其入點，容量為 INF。
                        // 這樣建模允許惡魔從一個 'P' 點傳送到另一個 'P' 點。
                        MaxFlowDinic.addEdge(graph, special, id1, INF);
                    }

                    // 處理相鄰單元格之間的連接
                    for (int p[] : dir) {
                        int x = i + p[0]; // 計算相鄰單元格的行索引
                        int y = j + p[1]; // 計算相鄰單元格的列索引
                        // 檢查相鄰單元格是否在網格邊界內
                        if (x < 0 || x >= A.length || y < 0 || y >= A[0].length) continue;
                        int id3 = x * A[0].length + y; // 計算相鄰單元格的一維編號 (入點)
                        // 從當前單元格的出點連接到相鄰單元格的入點，容量為 INF。
                        // 這表示惡魔可以在相鄰的非障礙物單元格之間自由移動。
                        MaxFlowDinic.addEdge(graph, id2, id3, INF);
                    }
                }
            }

            // 運行最大流算法，計算從源點到匯點的最大流量
            int flow = MaxFlowDinic.maxFlow(graph, source, dest);
            // 如果最大流量大於單元格總數 N，則表示無法阻止惡魔到達城堡（可能存在特殊情況），返回 -1。
            // 這個判斷可能需要根據具體的建模方式來確定。
            if (flow > N) return -1;
            // 返回計算得到的最大流量，這個值表示最少需要放置的障礙物數量。
            return flow;
        }

        // 將輸入的字符串數組轉換為二維字符數組
        public char[][] to(String grid[]) {
            char A[][] = new char[grid.length][grid[0].length()];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length(); j++) {
                    A[i][j] = grid[i].charAt(j);
                }
            }
            return A;
        }
    }

    // 表示圖中的一條邊
    static class Edge {
        int t, rev, cap, f; // t: 邊指向的節點, rev: 反向邊在鄰接表中的索引, cap: 邊的容量, f: 邊的當前流量

        public Edge(int t, int rev, int cap) {
            this.t = t;
            this.rev = rev;
            this.cap = cap;
        }
    }

    // 實現 Dinic 最大流算法的類
    class MaxFlowDinic {
        // 創建一個包含指定節點數的空圖
        public static List<Edge>[] createGraph(int nodes) {
            List<Edge>[] graph = new List[nodes];
            for (int i = 0; i < nodes; i++)
                graph[i] = new ArrayList<>();
            return graph;
        }

        // 向圖中添加一條從節點 s 到節點 t，容量為 cap 的邊
        public static void addEdge(List<Edge>[] graph, int s, int t, int cap) {
            graph[s].add(new Edge(t, graph[t].size(), cap)); // 添加正向邊
            graph[t].add(new Edge(s, graph[s].size() - 1, 0)); // 添加反向邊，初始容量為 0
        }

        // 使用 BFS 構建層次圖，用於 Dinic 算法
        static boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
            Arrays.fill(dist, -1); // 初始化距離數組為 -1
            dist[src] = 0; // 源點的距離為 0
            int[] Q = new int[graph.length]; // 用於 BFS 的佇列
            int sizeQ = 0;
            Q[sizeQ++] = src; // 將源點加入佇列
            for (int i = 0; i < sizeQ; i++) {
                int u = Q[i]; // 從佇列中取出一個節點 u
                for (Edge e : graph[u]) {
                    // 如果節點 e.t 還未被訪問且邊 e 的剩餘容量大於 0
                    if (dist[e.t] < 0 && e.f < e.cap) {
                        dist[e.t] = dist[u] + 1; // 設置節點 e.t 的距離為 u 的距離加 1
                        Q[sizeQ++] = e.t; // 將節點 e.t 加入佇列
                    }
                }
            }
            // 如果匯點的距離大於等於 0，表示可以到達匯點，返回 true
            return dist[dest] >= 0;
        }

        // 使用 DFS 在層次圖中尋找增廣路徑
        static int dinicDfs(List<Edge>[] graph, int[] ptr, int[] dist, int dest, int u, int f) {
            if (u == dest) // 如果到達匯點，則返回當前路徑的流量
                return f;
            for (; ptr[u] < graph[u].size(); ++ptr[u]) {
                Edge e = graph[u].get(ptr[u]);
                // 如果節點 e.t 的層次比節點 u 的層次深一層且邊 e 的剩餘容量大於 0
                if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
                    // 遞迴調用 dinicDfs 尋找增廣路徑
                    int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
                    if (df > 0) {
                        e.f += df; // 更新正向邊的流量
                        graph[e.t].get(e.rev).f -= df; // 更新反向邊的流量
                        return df;
                    }
                }
            }
            return 0; // 如果找不到增廣路徑，則返回 0
        }

        // Dinic 最大流算法的主函數
        public static int maxFlow(List<Edge>[] graph, int src, int dest) {
            int flow = 0; // 初始化最大流量為 0
            int[] dist = new int[graph.length]; // 存儲每個節點的層次
            // 只要可以構建層次圖（即還存在從源點到匯點的路徑）
            while (dinicBfs(graph, src, dest, dist)) {
                int[] ptr = new int[graph.length]; // 記錄每個節點當前考慮的邊的索引
                // 不斷尋找增廣路徑，直到找不到為止
                while (true) {
                    int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
                    if (df == 0) // 如果找不到增廣路徑，則跳出內層循環
                        break;
                    flow += df; // 將找到的增廣路徑的流量加到總流量中
                }
            }
            return flow; // 返回計算得到的最大流量
        }
    }
}
