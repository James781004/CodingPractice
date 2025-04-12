package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.*;

public class MaxScoreGrid {

    // https://leetcode.cn/problems/select-cells-in-grid-with-maximum-score/solutions/2899994/zhi-yu-zhuang-ya-dppythonjavacgo-by-endl-x27y/
    public int maxScore(List<List<Integer>> grid) {
        int rows = grid.size(); // 獲取矩陣的行數
        if (rows == 0) {
            return 0; // 如果矩陣為空，則返回 0 分
        }
        int cols = grid.get(0).size(); // 獲取矩陣的列數

        // 獲取矩陣中所有不同的數值
        Set<Integer> uniqueValuesSet = new HashSet<>();
        for (List<Integer> row : grid) {
            uniqueValuesSet.addAll(row); // 將每一行的數值添加到集合中
        }
        List<Integer> uniqueValues = new ArrayList<>(uniqueValuesSet); // 將集合轉換為列表
        // 建立數值到索引的映射，方便在圖中表示不同的數值節點
        Map<Integer, Integer> valueToIndex = new HashMap<>();
        for (int i = 0; i < uniqueValues.size(); ++i) {
            valueToIndex.put(uniqueValues.get(i), i); // 將每個不同的數值映射到一個唯一的索引
        }
        int numUniqueValues = uniqueValues.size(); // 獲取不同數值的總數

        // 網路流圖的節點總數：源點 (1) + 行數 (rows) + 不同數值數量 (numUniqueValues) + 匯點 (1)
        int n = 1 + rows + numUniqueValues + 1;
        S = 0; // 將源點的編號設置為 0
        T = n - 1; // 將匯點的編號設置為 n-1
        h = new int[n]; // 初始化鄰接表頭數組
        Arrays.fill(h, -1); // 將鄰接表頭的所有元素初始化為 -1，表示初始時沒有邊
        pre = new int[n]; // 初始化前驅節點數組
        // 估計邊的數量，每行最多連接到所有不同數值，加上源點到行的邊，加上數值到匯點的邊
        edges = new Edge[2 * rows * cols + 2 * rows + 2 * numUniqueValues];
        mf = new long[n]; // 初始化最大流量數組
        d = new long[n]; // 初始化距離數組
        vis = new boolean[n]; // 初始化訪問標記數組
        idx = 0; // 初始化邊的索引為 0

        // 從源點連接到每一行，容量為 1，費用為 0
        // 這表示每一行最多只能選擇一個單元格
        for (int i = 0; i < rows; ++i) {
            add(S, 1 + i, 1, 0); // 添加從源點 0 到表示第 i 行的節點 (1+i) 的邊，容量為 1，費用為 0
        }

        // 從每一行的節點連接到對應數值的節點，容量為 1，費用為負的單元格數值
        // 費用為負數是因為我們想要最大化得分，而最小費用最大流算法是尋找最小費用
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                int value = grid.get(i).get(j); // 獲取當前單元格的數值
                int valueIndex = valueToIndex.get(value); // 獲取該數值對應的索引
                // 行的節點編號從 1 開始，數值的節點編號從 1 + rows 開始
                add(1 + i, 1 + rows + valueIndex, 1, -value); // 添加從表示第 i 行的節點到表示數值 value 的節點的邊，容量為 1，費用為 -value
            }
        }

        // 從每個數值的節點連接到匯點，容量為 1，費用為 0
        // 這表示每個數值只能被選擇一次
        for (int i = 0; i < numUniqueValues; ++i) {
            add(1 + rows + i, T, 1, 0); // 添加從表示第 i 個不同數值的節點到匯點 T 的邊，容量為 1，費用為 0
        }

        // 執行最小費用最大流算法
        minCostMaxFlow(n);

        // 最小費用是我們設定的負得分，所以取負數得到最大得分
        return (int) -cost;
    }


    int S, T; // 源點和匯點的編號
    int[] h; // 鄰接表頭，用於快速找到每個節點的第一條邊
    int[] pre; // 前驅節點，用於在找到增廣路徑後回溯路徑
    Edge[] edges; // 邊的數組，存儲圖中的所有邊
    long[] mf; // 最大流量，記錄從源點到每個節點的當前增廣路徑上的最小剩餘容量
    long[] d; // 距離，記錄從源點到每個節點的當前最短路徑的費用
    boolean[] vis; // 訪問標記，用於在 SPFA 演算法中標記節點是否已在佇列中
    long inf = Long.MAX_VALUE; // 定義一個表示無窮大的值，用於初始化距離
    long flow; // 記錄計算得到的總流量
    long cost; // 記錄計算得到的總費用
    int idx; // 用於給邊分配唯一的索引，方便在邊數組中存取

    class Edge {
        int v, ne; // v: 邊的終點（指向哪個節點）, ne: 指向與當前節點相鄰的下一條邊的索引
        long c, w; // c: 邊的容量（最多允許多少流量通過）, w: 邊的權重（在這裡代表費用）

        public Edge(int v, long c, long w, int ne) {
            this.v = v;
            this.c = c;
            this.w = w;
            this.ne = ne;
        }
    }

    // 添加邊的方法，用於構建網路流圖
    void add(int a, int b, long c, long w) {
        edges[idx] = new Edge(b, c, w, h[a]); // 創建一條從節點 a 到節點 b 的邊，容量為 c，權重為 w，並將其插入到節點 a 的鄰接表頭
        h[a] = idx++; // 更新節點 a 的鄰接表頭，並將邊的索引 idx 加一
        edges[idx] = new Edge(a, 0, -w, h[b]); // 創建一條從節點 b 到節點 a 的反向邊，容量為 0，權重為 -w
        h[b] = idx++; // 更新節點 b 的鄰接表頭，並將邊的索引 idx 加一
    }

    // 使用 SPFA (Shortest Path Faster Algorithm) 算法尋找從源點到匯點的費用最小的路徑
    boolean spfa(int n) {
        Arrays.fill(d, inf); // 初始化距離數組 d，將所有節點到源點的距離設置為無窮大
        Arrays.fill(mf, 0); // 初始化最大流量數組 mf
        Arrays.fill(vis, false); // 初始化訪問標記數組 vis 為 false
        Deque<Integer> q = new ArrayDeque<>(); // 創建一個雙端佇列用於 SPFA 算法

        q.offer(S); // 將源點 S 加入佇列
        d[S] = 0; // 源點到自身的距離為 0
        mf[S] = inf; // 從源點出發的初始流量設置為無窮大
        vis[S] = true; // 標記源點為已訪問

        while (!q.isEmpty()) { // 只要佇列不為空，就繼續尋找最短路徑
            int u = q.poll(); // 從佇列中取出一個節點 u
            vis[u] = false; // 將節點 u 標記為未訪問

            // 遍歷節點 u 的所有鄰邊
            for (int i = h[u]; i != -1; i = edges[i].ne) {
                int v = edges[i].v; // 獲取邊指向的節點 v
                long cap = edges[i].c; // 獲取邊的容量 cap
                long weight = edges[i].w; // 獲取邊的權重 weight

                // 如果邊的容量大於 0 且通過這條邊可以獲得更短的路徑
                if (cap > 0 && d[v] > d[u] + weight) {
                    d[v] = d[u] + weight; // 更新節點 v 到源點的距離
                    mf[v] = Math.min(mf[u], cap); // 更新到達節點 v 的最大流量，取決於到達 u 的最大流量和當前邊的容量
                    pre[v] = i; // 記錄到達節點 v 的前一條邊的索引 i
                    // 如果節點 v 還未被訪問，則將其加入佇列
                    if (!vis[v]) {
                        vis[v] = true;
                        q.offer(v);
                    }
                }
            }
        }
        // 如果從源點到匯點 T 的最大流量大於 0，表示找到了一條增廣路徑
        return mf[T] > 0;
    }

    // 最小費用最大流算法的實現
    void minCostMaxFlow(int n) {
        flow = 0; // 初始化總流量為 0
        cost = 0; // 初始化總費用為 0
        while (spfa(n)) { // 當還能找到從源點到匯點的增廣路徑時
            long f = mf[T]; // 獲取當前增廣路徑上的最小剩餘容量
            flow += f; // 將總流量加上當前增廣路徑的流量
            // 從匯點 T 回溯到源點 S，更新路徑上的邊的容量和費用
            for (int i = T; i != S; i = edges[pre[i] ^ 1].v) {
                edges[pre[i]].c -= f; // 將正向邊的容量減去已使用的流量
                edges[pre[i] ^ 1].c += f; // 將反向邊的容量加上已使用的流量
                cost += f * edges[pre[i]].w; // 將總費用加上當前路徑的費用
            }
        }
    }

}
