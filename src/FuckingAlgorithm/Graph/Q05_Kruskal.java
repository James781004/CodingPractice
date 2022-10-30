package FuckingAlgorithm.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Q05_Kruskal {
    // https://segmentfault.com/a/1190000040999362
    // 所謂最小生成樹，就是圖中若干邊的集合（我們後文稱這個集合為 mst，最小生成樹的英文縮寫），你要保證這些邊：
    // 1、包含圖中的所有節點。
    // 2、形成的結構是樹結構（即不存在環）。
    // 3、權重和最小。
    // 前兩條其實可以很容易地利用 Union-Find 算法做到，關鍵在於第 3 點，如何保證得到的這棵生成樹是權重和最小的。
    // 這裡就用到了貪心思路：
    // 將所有邊按照權重從小到大排序，從權重最小的邊開始遍歷，
    // 如果這條邊和 mst 中的其它邊不會形成環，則這條邊是最小生成樹的一部分，將它加入 mst 集合；
    // 否則，這條邊不是最小生成樹的一部分，不要把它加入 mst 集合。

    class UF {
        // 連通分量個數
        private int count;
        // 存儲每個節點的父節點
        private int[] parent;

        // n 為圖中節點的個數
        public UF(int n) {
            this.count = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }


        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

//        public int find(int x) {
//            while (x != parent[x]) {
//                parent[x] = parent[parent[x]];
//                x = parent[x];
//            }
//            return x;
//        }

        // 返回圖中的連通分量個數
        public int count() {
            return count;
        }

        // 將節點 p 和節點 q 連通
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);

            if (rootP == rootQ)
                return;

            parent[rootQ] = rootP;
            // 兩個連通分量合並成一個連通分量
            count--;
        }

        // 判斷節點 p 和節點 q 是否連通
        public boolean connected(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            return rootP == rootQ;
        }
    }


    // https://www.cnblogs.com/cnoodle/p/14199807.html
    // 261. Graph Valid Tree
    // Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes),
    // write a function to check whether these edges make up a valid tree.

    // 對於添加的這條邊，如果該邊的兩個節點本來就在同一連通分量裡，那麼添加這條邊會產生環；
    // 反之，如果該邊的兩個節點不在同一連通分量裡，則添加這條邊不會產生環。
    public boolean validTree(int n, int[][] edges) {
        // 初始化 0...n-1 共 n 個節點
        UF uf = new UF(n);

        // 遍歷所有邊，將組成邊的兩個節點進行連接
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            // 若兩個節點已經在同一連通分量中，會產生環
            if (uf.connected(u, v)) {
                return false;
            }

            // 這條邊不會產生環，可以是樹的一部分
            uf.union(u, v);
        }

        // 要保證最後只形成了一棵樹，即只有一個連通分量
        return uf.count() == 1;
    }


    // https://blog.csdn.net/qq_21201267/article/details/107796632
    // LeetCode 1135. 最低成本聯通所有城市
    // 想象一下你是個城市基建規劃者，地圖上有 N 座城市，它們按以 1 到 N 的次序編號。
    // 給你一些可連接的選項 conections，
    // 其中每個選項 conections[i] = [city1, city2, cost] 表示將城市 city1 和城市 city2 連接所要的成本。
    // （連接是雙向的，也就是說城市 city1 和城市 city2 相連也同樣意味著城市 city2 和城市 city1 相連）。
    // 返回使得每對城市間都存在將它們連接在一起的連通路徑（可能長度為 1 的）最小成本。
    // 該最小成本應該是所用全部連接代價的綜合。如果根據已知條件無法完成該項任務，則請你返回 -1。

    public int minimumCost(int n, int[][] connections) {
        // 城市編號為 1...n，所以初始化大小為 n + 1
        UF uf = new UF(n + 1);

        // 對所有邊按照權重從小到大排序
        Arrays.sort(connections, (a, b) -> (a[2] - b[2]));

        // 記錄最小生成樹的權重之和
        int mst = 0;
        for (int[] edge : connections) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            // 若這條邊會產生環，則不能加入 mst
            if (uf.connected(u, v)) {
                continue;
            }
            // 若這條邊不會產生環，則屬於最小生成樹
            mst += weight;
            uf.union(u, v);
        }

        // 保證所有節點都被連通
        // 按理說 uf.count() == 1 說明所有節點被連通
        // 但因為節點 0 沒有被使用，所以 0 會額外佔用一個連通分量
        return uf.count() == 2 ? mst : -1;
    }


    // https://blog.csdn.net/meraki/article/details/123894814
    // LeetCode 1584.連接所有點的最小費用
    // 給你一個points 數組，表示 2D 平面上的一些點，其中 points[i] = [xi, yi] 。
    // 連接點 [xi, yi] 和點 [xj, yj] 的費用為它們之間的 曼哈頓距離 ：|xi - xj| + |yi - yj| ，其中 |val| 表示 val 的絕對值。
    // 請你返回將所有點連接的最小總費用。只有任意兩點之間 有且僅有 一條簡單路徑時，才認為所有點都已連接。


    // 最小生成樹問題：每個點就是無向加權圖中的節點，邊的權重就是曼哈頓距離，連接所有點的最小費用就是最小生成樹的權重和。
    // 所以解法思路就是先生成所有的邊以及權重，然後對這些邊執行 Kruskal 算法即可。
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        // 生成所有邊及權重
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int xi = points[i][0], yi = points[i][1];
                int xj = points[j][0], yj = points[j][1];
                // 用坐標點在 points 中的索引表示坐標點
                edges.add(new int[]{
                        i, j, Math.abs(xi - xj) + Math.abs(yi - yj)
                });
            }
        }

        // 將邊按照權重從小到大排序
        Collections.sort(edges, (a, b) -> {
            return a[2] - b[2];
        });

        // 執行 Kruskal 算法
        int mst = 0;
        UF uf = new UF(n);
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            // 若這條邊會產生環，則不能加入 mst
            if (uf.connected(u, v)) {
                continue;
            }
            // 若這條邊不會產生環，則屬於最小生成樹
            mst += weight;
            uf.union(u, v);
        }

        return mst;
    }
}
