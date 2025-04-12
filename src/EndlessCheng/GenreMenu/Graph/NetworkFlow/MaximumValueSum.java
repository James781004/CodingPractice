package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MaximumValueSum {

    // https://leetcode.cn/problems/maximum-value-sum-by-placing-three-rooks-ii/solutions/2884186/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-gc48/

    int S, T; // 源點和匯點
    int[] h; // 鄰接表頭
    int[] pre; // 前驅節點
    Edge[] edges; // 邊的數組
    long[] mf; // 增廣路徑上的最小剩餘容量
    long[] d; // 節點到源點的最短距離
    boolean[] vis; // 標記節點是否在佇列中
    long inf = Long.MAX_VALUE; // 無窮大
    long flow; // 最大流
    long cost; // 最小費用
    int idx; // 邊的索引

    class Edge {
        int v, ne; // v: 邊的終點, ne: 下一條邊的索引
        long c, w; // c: 邊的容量, w: 邊的權重（費用）

        public Edge(int v, long c, long w, int ne) {
            this.v = v;
            this.c = c;
            this.w = w;
            this.ne = ne;
        }
    }

    // 添加邊的方法
    void add(int a, int b, long c, long w) {
        edges[idx] = new Edge(b, c, w, h[a]); // 創建一條從節點 a 到節點 b 的邊，容量為 c，權重為 w，並將其插入到節點 a 的鄰接表頭
        h[a] = idx++; // 更新節點 a 的鄰接表頭，並將邊的索引 idx 加一
        edges[idx] = new Edge(a, 0, -w, h[b]); // 創建一條從節點 b 到節點 a 的反向邊，容量為 0，權重為 -w
        h[b] = idx++; // 更新節點 b 的鄰接表頭，並將邊的索引 idx 加一
    }

    // 使用 SPFA 算法尋找源點到匯點的費用最小的路徑
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

    // 最小費用最大流算法的實現，可以指定目標流量
    void minCostMaxFlow(int n, int targetFlow) {
        flow = 0; // 初始化總流量為 0
        cost = 0; // 初始化總費用為 0
        while (flow < targetFlow && spfa(n)) { // 當總流量小於目標流量且還能找到增廣路徑時
            long f = mf[T]; // 獲取當前增廣路徑上的最小剩餘容量
            long pushedFlow = Math.min((long) targetFlow - flow, f); // 計算實際可以推送的流量，不能超過目標流量
            flow += pushedFlow; // 將總流量加上實際推送的流量
            // 從匯點 T 回溯到源點 S，更新路徑上的邊的容量和費用
            for (int i = T; i != S; i = edges[pre[i] ^ 1].v) {
                edges[pre[i]].c -= pushedFlow; // 將正向邊的容量減去已使用的流量
                edges[pre[i] ^ 1].c += pushedFlow; // 將反向邊的容量加上已使用的流量
                cost += pushedFlow * edges[pre[i]].w; // 將總費用加上當前路徑的費用
            }
        }
    }

    public long maximumValueSum(int[][] board) {
        int m = board.length; // 獲取棋盤的行數
        if (m == 0) return 0; // 如果棋盤為空，則返回 0
        int n = board[0].length; // 獲取棋盤的列數

        // 節點總數：源點 + 行數 + 列數 + 匯點
        int numNodes = 1 + m + n + 1;
        S = 0; // 源點編號為 0
        T = numNodes - 1; // 匯點編號為 numNodes - 1
        h = new int[numNodes];
        Arrays.fill(h, -1); // 初始化鄰接表頭為 -1
        pre = new int[numNodes];
        // 邊的數量估計：每行最多連接到所有列，加上源點到行的邊，加上列到匯點的邊
        edges = new Edge[2 * m * n + 2 * m + 2 * n];
        mf = new long[numNodes];
        d = new long[numNodes];
        vis = new boolean[numNodes];
        idx = 0;

        // 從源點連接到每一行，容量為 1，費用為 0
        // 表示每一行最多只能放置一個車
        for (int i = 0; i < m; ++i) {
            add(S, 1 + i, 1, 0); // 添加從源點 0 到表示第 i 行的節點 (1+i) 的邊，容量為 1，費用為 0
        }

        // 從每一行的節點連接到每一列的節點，容量為 1，費用為負的格子價值
        // 費用為負數是因為我們想要最大化總價值
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                // 行的節點編號從 1 開始，列的節點編號從 1 + m 開始
                add(1 + i, 1 + m + j, 1, -board[i][j]); // 添加從表示第 i 行的節點到表示第 j 列的節點 (1+m+j) 的邊，容量為 1，費用為 -board[i][j]
            }
        }

        // 從每一列的節點連接到匯點，容量為 1，費用為 0
        // 表示每一列最多只能放置一個車
        for (int j = 0; j < n; ++j) {
            add(1 + m + j, T, 1, 0); // 添加從表示第 j 列的節點 (1+m+j) 到匯點 T 的邊，容量為 1，費用為 0
        }

        // 執行最小費用最大流算法，目標流量為 3 (放置三個車)
        minCostMaxFlow(numNodes, 3);

        // 最小費用的相反數就是最大總價值
        return -cost;
    }


    // O(mn) 前後綴分解
    public long maximumValueSum2(int[][] board) {
        int n = board.length, m = board[0].length;
        // pre[i][0][0]記錄到第i行為止，第一大的數；pre[i][0][1]記錄第i行為止，第一大的數的列號，後面的後綴依此類推
        int[][][] pre = new int[n + 1][3][2];
        int[][][] suf = new int[n + 1][3][2];
        int[][] max3 = new int[3][2];
        for (int[] t : max3) {
            t[0] = Integer.MIN_VALUE;
        }
        // 計算前綴
        for (int i = 0; i < n - 2; i++) {
            update(board[i], max3);
            for (int j = 0; j < 3; j++) {
                pre[i][j][0] = max3[j][0];
                pre[i][j][1] = max3[j][1];
            }
        }
        // 重置臨時數組
        max3 = new int[3][2];
        for (int[] t : max3) {
            t[0] = Integer.MIN_VALUE;
        }
        // 計算後綴
        for (int i = n - 1; i > 1; i--) {
            update(board[i], max3);
            for (int j = 0; j < 3; j++) {
                suf[i][j][0] = max3[j][0];
                suf[i][j][1] = max3[j][1];
            }
        }

        long res = Long.MIN_VALUE;
        // 枚舉中間的車的行數
        for (int i = 1; i < n - 1; i++) {
            // 枚舉中間的車的列數
            for (int j = 0; j < m; j++) {
                // 枚舉第一個車前i - 1行中能達到的前三大的數
                for (int[] a : pre[i - 1]) {
                    // 枚舉第三個車後i + 1行中能達到的前三大的數
                    for (int[] b : suf[i + 1]) {
                        // 如果三個車不存在相同的列，則可以進行更新
                        if (a[1] != j && b[1] != j && a[1] != b[1]) {
                            res = Math.max(res, (long) a[0] + board[i][j] + b[0]);
                            // 因為都是從最大的開始更新的，如果能更新，就不用看更小的了
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }

    public void update(int[] row, int[][] max3) {
        for (int i = 0; i < row.length; i++) {
            int x = row[i];
            // 如果比最大的大，則更新
            if (x > max3[0][0]) {
                // 如果和最大的列號不同，則可以更新到第二大（把原來最大的更新為第二大）
                if (max3[0][1] != i) {
                    // 如果和第三大的列號也不同，則三個都可以被更新（把原來第二大的更新為第三大）
                    if (max3[1][1] != i) {
                        max3[2] = max3[1];
                    }
                    max3[1] = max3[0];
                }
                max3[0] = new int[]{x, i};
            }
            // 如果無法更新最大，則看能否更新第二大和第三大
            else if (x > max3[1][0] && i != max3[0][1]) {
                if (max3[1][1] != i) {
                    max3[2] = max3[1];
                }
                max3[1] = new int[]{x, i};
            }
            // 如果最大和第二大更新不了，則看能夠更新第三大
            else if (x > max3[2][0] && i != max3[0][1] && i != max3[1][1]) {
                max3[2] = new int[]{x, i};
            }
        }
    }
}
