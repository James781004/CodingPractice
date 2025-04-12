package EndlessCheng.GenreMenu.Graph.MinimumSpanningTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MinCostConnectPoints {

    // https://leetcode.cn/problems/min-cost-to-connect-all-points/solutions/2145799/1584-lian-jie-suo-you-dian-de-zui-xiao-f-i12e/
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        // 生成所有邊及權重
        ArrayList<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // ⭐注意j從i+1開始，不要重頭開始，避免重復
            for (int j = i + 1; j < n; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                int dis = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                // 用坐標點在 points 中的索引表示坐標點
                edges.add(new int[]{i, j, dis});
            }
        }

        // 將邊按照權重從小到大排序
        Collections.sort(edges, (a, b) -> (a[2] - b[2]));

        // 執行 Kruskal 算法
        UF uf = new UF(n);
        int minWeight = 0;
        for (int[] edge : edges) {
            int i = edge[0];
            int j = edge[1];
            // 若這條邊會產生環，則不能加入 mst
            if (uf.isConnected(i, j)) {
                continue;
            }
            // 若這條邊不會產生環，則屬於最小生成樹
            uf.union(i, j);
            minWeight += edge[2];
        }

        return minWeight;
    }

    class UF {
        private int count;
        private int[] parent;

        public UF(int n) {
            parent = new int[n];
            count = 0;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public void union(int p, int q) {
            int rootP = findRoot(p);
            int rootQ = findRoot(q);
            parent[rootP] = rootQ;
            // 兩個連通分量合並成一個連通分量，count數量減一
            count--;
        }

        public boolean isConnected(int p, int q) {
            int rootP = findRoot(p);
            int rootQ = findRoot(q);
            return rootP == rootQ;
        }

        public int findRoot(int x) {
            if (parent[x] != x) {
                parent[x] = findRoot(parent[x]);
            }
            return parent[x];
        }

        public int getCount() {
            return count;
        }
    }


    public int minCostConnectPointsPrim(int[][] points) {
        // 轉化成無向圖鄰接表的形式
        List<int[]>[] graph = buildGraph(points);
        // 執行 Prim 算法
        Prim prim = new Prim(graph);
        return prim.minWeightSum;
    }

    // 轉化成無向圖鄰接表的形式
    public List<int[]>[] buildGraph(int[][] points) {
        // 圖中共有 n 個節點
        int n = points.length;
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i == j) {
                    continue;
                }
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                int weight = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                // 用 points 中的索引表示坐標點
                // 無向圖其實就是雙向圖
                // 一條邊表示為 int[]{from, to, weight}
                graph[i].add(new int[]{i, j, weight});
                graph[j].add(new int[]{j, i, weight});
            }
        }
        return graph;
    }

    class Prim {
        // 核心數據結構，存儲橫切邊的優先級隊列
        // 按照邊的權重從小到大排序
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> (a[2] - b[2]));
        // 類似 visited 數組的作用，記錄哪些節點已經成為最小生成樹的一部分
        boolean[] inMST;
        // 記錄最小生成樹的權重和
        int minWeightSum = 0;
        // graph 是用鄰接表表示的一幅圖，
        // graph[s] 記錄節點 s 所有相鄰的邊，
        // 三元組 int[]{from, to, weight} 表示一條邊
        List<int[]>[] graph;

        public Prim(List<int[]>[] graph) {
            this.graph = graph;
            inMST = new boolean[graph.length];
            // 隨便從一個點開始切分都可以，從節點 0 開始
            cut(0);
            inMST[0] = true;
            // 不斷進行切分，向最小生成樹中添加邊
            while (!pq.isEmpty()) {
                int[] edge = pq.poll();
                int to = edge[1];
                int weight = edge[2];
                if (inMST[to]) {
                    // 節點 to 已經在最小生成樹中，跳過
                    // 否則這條邊會產生環
                    continue;
                }
                // 將邊 edge 加入最小生成樹
                // 節點 to 加入後，進行新一輪切分，會產生更多橫切邊
                cut(to);
                inMST[to] = true;
                minWeightSum += weight;
            }
        }

        // 將 s 的橫切邊加入優先隊列
        public void cut(int v) {
            List<int[]> edges = graph[v];
            // 遍歷 s 的鄰邊
            for (int[] edge : edges) {
                if (inMST[edge[1]]) {
                    // 相鄰接點 to 已經在最小生成樹中，跳過
                    // 否則這條邊會產生環
                    continue;
                }
                // 加入橫切邊隊列
                pq.add(edge);
            }
        }
    }

}
