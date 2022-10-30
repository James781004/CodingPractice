package FuckingAlgorithm.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Q06_Prim {
    //    https://labuladong.github.io/algo/2/22/55/
    class Prim {
        // 核心數據結構，存儲「橫切邊」的優先級隊列
        private PriorityQueue<int[]> pq;

        // 類似 visited 數組的作用，記錄哪些節點已經成為最小生成樹的一部分
        private boolean[] inMST;

        // 記錄最小生成樹的權重和
        private int weightSum = 0;

        // graph 是用鄰接表表示的一幅圖，
        // graph[s] 記錄節點 s 所有相鄰的邊，
        // 三元組 int[]{from, to, weight} 表示一條邊
        private List<int[]>[] graph;

        // 使用 BFS 算法思想 和 visited 布爾數組避免成環，來保證選出來的邊最終形成的一定是一棵樹。
        public Prim(List<int[]>[] graph) {
            this.graph = graph;
            this.pq = new PriorityQueue<>((a, b) -> {
                // 按照邊的權重從小到大排序
                return a[2] - b[2];
            });

            // 圖中有 n 個節點
            int n = graph.length;
            this.inMST = new boolean[n];

            // 隨便從一個點開始切分都可以，從節點 0 開始
            inMST[0] = true;
            cut(0);

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
                weightSum += weight;
                inMST[to] = true;
                // 節點 to 加入後，進行新一輪切分，會產生更多橫切邊
                cut(to);
            }
        }

        // 將 s 的橫切邊加入優先隊列
        private void cut(int s) {
            // 遍歷 s 的鄰邊
            for (int[] edge : graph[s]) {
                int to = edge[1];
                if (inMST[to]) {
                    // 相鄰接點 to 已經在最小生成樹中，跳過
                    // 否則這條邊會產生環
                    continue;
                }
                // 加入橫切邊隊列
                pq.offer(edge);
            }
        }

        // 最小生成樹的權重和
        public int weightSum() {
            return weightSum;
        }

        // 判斷最小生成樹是否包含圖中的所有節點
        public boolean allConnected() {
            for (int i = 0; i < inMST.length; i++) {
                if (!inMST[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    // https://blog.csdn.net/qq_21201267/article/details/107796632
    // LeetCode 1135. 最低成本聯通所有城市
    // 想象一下你是個城市基建規劃者，地圖上有 N 座城市，它們按以 1 到 N 的次序編號。
    // 給你一些可連接的選項 conections，
    // 其中每個選項 conections[i] = [city1, city2, cost] 表示將城市 city1 和城市 city2 連接所要的成本。
    // （連接是雙向的，也就是說城市 city1 和城市 city2 相連也同樣意味著城市 city2 和城市 city1 相連）。
    // 返回使得每對城市間都存在將它們連接在一起的連通路徑（可能長度為 1 的）最小成本。
    // 該最小成本應該是所用全部連接代價的綜合。如果根據已知條件無法完成該項任務，則請你返回 -1。

    class minimumCost {
        public int minimumCost(int n, int[][] connections) {
            // 轉化成無向圖鄰接表的形式
            List<int[]>[] graph = buildGraph(n, connections);
            // 執行 Prim 算法
            Prim prim = new Prim(graph);

            if (!prim.allConnected()) {
                // 最小生成樹無法覆蓋所有節點
                return -1;
            }

            return prim.weightSum();
        }

        private List<int[]>[] buildGraph(int n, int[][] connections) {
            // 圖中共有 n 個節點
            List<int[]>[] graph = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new LinkedList<>();
            }

            for (int[] conn : connections) {
                // 題目給的節點編號是從 1 開始的，
                // 但我們實現的 Prim 算法需要從 0 開始編號
                int u = conn[0] - 1;
                int v = conn[1] - 1;
                int weight = conn[2];
                // 「無向圖」其實就是「雙向圖」
                // 一條邊表示為 int[]{from, to, weight}
                graph[u].add(new int[]{u, v, weight});
                graph[v].add(new int[]{v, u, weight});
            }
            return graph;
        }
    }


    // https://blog.csdn.net/meraki/article/details/123894814
    // LeetCode 1584.連接所有點的最小費用
    // 給你一個points 數組，表示 2D 平面上的一些點，其中 points[i] = [xi, yi] 。
    // 連接點 [xi, yi] 和點 [xj, yj] 的費用為它們之間的 曼哈頓距離 ：|xi - xj| + |yi - yj| ，其中 |val| 表示 val 的絕對值。
    // 請你返回將所有點連接的最小總費用。只有任意兩點之間 有且僅有 一條簡單路徑時，才認為所有點都已連接。

    class minCostConnectPoints {
        public int minCostConnectPoints(int[][] points) {
            int n = points.length;
            List<int[]>[] graph = buildGraph(n, points);
            return new Prim(graph).weightSum();
        }

        // 構造無向圖
        private List<int[]>[] buildGraph(int n, int[][] points) {
            List<int[]>[] graph = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new LinkedList<>();
            }

            // 生成所有邊及權重
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int xi = points[i][0], yi = points[i][1];
                    int xj = points[j][0], yj = points[j][1];
                    int weight = Math.abs(xi - xj) + Math.abs(yi - yj);
                    // 用 points 中的索引表示坐標點
                    graph[i].add(new int[]{i, j, weight});
                    graph[j].add(new int[]{j, i, weight});
                }
            }
            return graph;
        }
    }


}
