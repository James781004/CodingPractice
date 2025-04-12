package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MaximumANDSumArray {

    // https://leetcode.cn/problems/maximum-and-sum-of-array/solutions/2612521/zui-xiao-fei-yong-zui-da-liu-by-gjnas4mk-x2ad/
    public int maximumANDSum(int[] nums, int numSlots) {
        // 計算源點和匯點的編號
        S = nums.length + numSlots + 1;
        T = S + 1;
        int n = nums.length;
        // 初始化鄰接表頭陣列
        h = new int[100005];
        // 初始化前驅節點陣列
        pre = new int[100005];
        // 初始化邊的陣列
        edges = new Edge[100005 << 1];
        // 將每個數字連接到源點
        for (int i = 0; i < n; ++i) {
            add(S, i, 1, 0); // 從源點到數字 i，容量為 1，費用為 0
        }
        // 將每個數字連接到每個槽位
        for (int i = 0; i < n; ++i) {
            for (int j = 1; j <= numSlots; ++j) {
                add(i, n + j - 1, inf, -(nums[i] & j)); // 從數字 i 到槽位 j，容量為無限大，費用為負的與運算結果
            }
        }
        // 將每個槽位連接到匯點
        for (int i = 0; i < numSlots; ++i) {
            add(n + i, T, 2, 0); // 從槽位 i 到匯點，容量為 2，費用為 0
        }
        // 執行最小費用最大流演算法
        EK();
        // 返回總費用的負值，即為最大與和
        return (int) -cost;
    }

    // 表示圖中的一條邊
    class Edge {
        int v, ne; // v: 這條邊指向的節點, ne: 指向與當前節點相鄰的下一條邊的索引
        long c, w; // c: 這條邊的容量, w: 這條邊的權重或費用

        public Edge(int v, long c, long w, int ne) {
            this.v = v;
            this.c = c;
            this.w = w;
            this.ne = ne;
        }
    }

    // 定義源點和匯點的編號
    int S, T;
    // 鄰接表頭陣列，用於快速找到每個節點的第一條邊
    int[] h;
    // 前驅節點陣列，用於在找到增廣路徑後回溯路徑
    int[] pre;
    // 邊的陣列，存儲圖中的所有邊
    Edge[] edges;
    // 最大流量陣列，記錄從源點到每個節點的當前增廣路徑上的最小剩餘容量
    long[] mf;
    // 距離陣列，記錄從源點到每個節點的當前最短路徑的費用
    long[] d;
    // 訪問標記陣列，用於在 SPFA 演算法中標記節點是否已在佇列中
    boolean[] vis;
    // 表示無窮大的容量
    long inf = Integer.MAX_VALUE;
    // 記錄總的流量
    long flow;
    // 記錄總的費用
    long cost;
    // 用於給邊分配唯一的索引
    int idx = 1;

    // 向圖中新增一條邊的方法
    void add(int a, int b, long c, long d) {
        // 創建一條從 a 到 b 的邊，容量為 c，費用為 d，並將其插入到節點 a 的鄰接表頭
        edges[++idx] = new Edge(b, c, d, h[a]);
        h[a] = idx;
        // 同時創建一條從 b 到 a 的反向邊，容量為 0，費用為 -d
        edges[++idx] = new Edge(a, 0, -d, h[b]);
        h[b] = idx;
    }

    // 使用 SPFA 演算法尋找從源點到匯點的費用最小的路徑
    boolean spfa() {
        // 初始化距離陣列為無窮大
        d = new long[100005];
        Arrays.fill(d, inf);
        // 初始化最大流量陣列
        mf = new long[100005];
        // 初始化訪問標記陣列為 false
        vis = new boolean[100005];
        // 創建一個雙端佇列用於 SPFA
        Deque<Integer> que = new ArrayDeque<Integer>();
        // 將源點加入佇列，設置距離為 0，最大流量為無窮大，並標記為已訪問
        que.offer(S);
        d[S] = 0;
        mf[S] = inf;
        vis[S] = true;
        // 只要佇列不為空，就繼續尋找最短路徑
        while (!que.isEmpty()) {
            int u = que.poll(); // 從佇列中取出一個節點
            vis[u] = false; // 將其標記為未訪問
            // 遍歷節點 u 的所有鄰邊
            for (int i = h[u]; i != 0; i = edges[i].ne) {
                long c = edges[i].c, w = edges[i].w; // 獲取邊的容量和費用
                if (c == 0) continue; // 如果容量為 0，表示不能通過，跳過
                int v = edges[i].v; // 獲取邊指向的節點
                // 如果找到更短的路徑
                if (d[v] > d[u] + w) {
                    d[v] = d[u] + w; // 更新距離
                    mf[v] = Math.min(mf[u], c); // 更新最大流量
                    pre[v] = i; // 記錄前驅邊
                    // 如果節點 v 還未被訪問，則加入佇列
                    if (!vis[v]) {
                        que.offer(v);
                        vis[v] = true;
                    }
                }
            }
        }
        // 如果從源點到匯點的流量大於 0，表示找到了一條增廣路徑
        return mf[T] > 0;
    }

    // Edmonds-Karp 演算法計算最小費用最大流
    void EK() {
        // 只要能找到增廣路徑，就繼續執行
        while (spfa()) {
            // 從匯點回溯到源點，更新路徑上的邊的容量和費用
            for (int v = T; v != S; ) {
                int i = pre[v];
                edges[i].c -= mf[T]; // 正向邊容量減去增廣路徑上的最小流量
                edges[i ^ 1].c += mf[T]; // 反向邊容量加上增廣路徑上的最小流量
                v = edges[i ^ 1].v; // 移動到前一個節點
            }
            flow += mf[T]; // 累加總流量
            cost += mf[T] * d[T]; // 累加總費用
        }
    }


}
