package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MaxStudents {

    // https://leetcode.cn/problems/maximum-students-taking-exam/solutions/89661/er-fen-tu-zui-da-du-li-ji-by-lightcml/
    // 定義邊的結構
    static class Edge {
        int to; // 目標節點
        int next; // 下一條邊的索引
        int val; // 邊的容量（流量）

        public Edge() {
        }

        public Edge(int to, int next, int val) {
            this.to = to;
            this.next = next;
            this.val = val;
        }
    }

    public int tot = 0; // 邊的總數（索引計數器）
    public int[] heads = new int[110]; // 存儲每個節點的第一條邊
    public Edge[] E = new Edge[10010]; // 邊的數組
    public int n, m, S, T; // n 為行數，m 為列數，S 為源點，T 為匯點
    public Queue<Integer> queue = new LinkedList<>(); // BFS 佇列
    public int[] depth = new int[110]; // 記錄層數

    /**
     * 添加一條有向邊（包括正向和反向邊）
     */
    public void addEdge(int x, int y, int v) {
        E[++tot] = new Edge(y, heads[x], v); // 正向邊
        heads[x] = tot;

        E[++tot] = new Edge(x, heads[y], 0); // 反向邊，初始容量為 0
        heads[y] = tot;
    }

    /**
     * 將二維座標轉換為對應的節點編號
     */
    public int getp(int x, int y) {
        return x * m + y + 1;
    }

    /**
     * 使用 BFS 建立分層網路
     *
     * @return 是否存在增廣路
     */
    public boolean bfs() {
        Arrays.fill(depth, -1);
        queue.add(S);
        depth[S] = 0;

        while (!queue.isEmpty()) {
            int x = queue.poll();
            for (int i = heads[x]; i > 0; i = E[i].next) {
                if (E[i].val > 0 && depth[E[i].to] == -1) {
                    depth[E[i].to] = depth[x] + 1;
                    queue.add(E[i].to);
                }
            }
        }
        return depth[T] != -1;
    }

    /**
     * 使用 DFS 搜索增廣路並進行流量推送
     */
    public int dfs(int x, int flow) {
        if (x == T || flow == 0) return flow;
        int w = 0;
        for (int i = heads[x]; i != 0; i = E[i].next) {
            if (E[i].val > 0 && depth[E[i].to] == depth[x] + 1) {
                int v = dfs(E[i].to, Math.min(flow - w, E[i].val));
                E[i].val -= v;
                E[i ^ 1].val += v;
                w += v;
            }
        }
        if (w == 0) depth[x] = -1;
        return w;
    }

    /**
     * Dinic 算法求最大流
     */
    public int Dinic() {
        int sum = 0;
        while (bfs()) {
            sum += dfs(S, Integer.MAX_VALUE);
        }
        return sum;
    }

    /**
     * 計算最多能坐下的學生數量
     */
    public int maxStudents(char[][] seats) {
        for (int i = 0; i < E.length; i++) {
            E[i] = new Edge();
        }
        tot = 1; // 初始化邊的索引
        n = seats.length;
        m = seats[0].length;
        S = 0;
        T = n * m + 1;
        int cnt = 0; // 記錄可用座位數

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (seats[i][j] == '.') {
                    ++cnt;
                    int x = i * m + j + 1;

                    if ((j & 1) == 1) addEdge(S, x, 1); // 奇數列連接源點
                    else addEdge(x, T, 1); // 偶數列連接匯點

                    // 連接相鄰座位，確保沒有相鄰座位的學生作弊
                    if (j - 1 >= 0 && seats[i][j - 1] == '.') {
                        if ((j & 1) == 1) addEdge(x, getp(i, j - 1), 1);
                        else addEdge(getp(i, j - 1), x, 1);
                    }
                    if (j + 1 < m && seats[i][j + 1] == '.') {
                        if ((j & 1) == 1) addEdge(x, getp(i, j + 1), 1);
                        else addEdge(getp(i, j + 1), x, 1);
                    }
                    if (i != 0 && j + 1 < m && seats[i - 1][j + 1] == '.') {
                        if ((j & 1) == 1) addEdge(x, getp(i - 1, j + 1), 1);
                        else addEdge(getp(i - 1, j + 1), x, 1);
                    }
                    if (i != 0 && j != 0 && seats[i - 1][j - 1] == '.') {
                        if ((j & 1) == 1) addEdge(x, getp(i - 1, j - 1), 1);
                        else addEdge(getp(i - 1, j - 1), x, 1);
                    }
                }
            }
        }
        return cnt - Dinic();
    }


}
