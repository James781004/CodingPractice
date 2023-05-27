package WeeklyContest;

import java.util.*;

class Week_346 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2696.Minimum%20String%20Length%20After%20Removing%20Substrings/README.md
    public int minLength(String s) {
        Deque<Character> stk = new ArrayDeque<>();
        stk.push(' ');
        for (char c : s.toCharArray()) {
            if ((c == 'B' && stk.peek() == 'A') || (c == 'D' && stk.peek() == 'C')) {
                stk.pop();
            } else {
                stk.push(c);
            }
        }
        return stk.size() - 1;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2697.Lexicographically%20Smallest%20Palindrome/README.md
    public String makeSmallestPalindrome(String s) {
        char[] cs = s.toCharArray();
        for (int i = 0, j = cs.length - 1; i < j; i++, j--) {
            if (cs[i] != cs[j]) {
                cs[i] = cs[j] = cs[i] < cs[j] ? cs[i] : cs[j];
            }
        }
        return String.valueOf(cs);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2698.Find%20the%20Punishment%20Number%20of%20an%20Integer/README.md
    public int punishmentNumber(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            int x = i * i;
            if (check(x + "", 0, i)) {
                ans += x;
            }
        }
        return ans;
    }


    // i * i 十進制表示的字符串進行分割，然後判斷是否滿足題目要求
    private boolean check(String s, int i, int x) {
        int m = s.length();
        if (i >= m) {  // 遞歸終點，看看x是否已經切割完畢
            return x == 0;
        }

        int y = 0;
        for (int j = i; j < m; j++) { // 枚舉所有子串
            y = y * 10 + (s.charAt(j) - '0'); // 切割片段 y 對應的整數值
            if (y > x) { // y 已經大於剩餘的 x，不可能成為答案，剪枝
                break;
            }

            // x切割y之後繼續下一層遞歸
            if (check(s, j + 1, x - y)) {
                return true;
            }
        }
        return false;
    }


    // https://www.bilibili.com/video/BV1Qm4y1t7cx/
    // https://leetcode.cn/problems/modify-graph-edge-weights/solution/xiang-xi-fen-xi-liang-ci-dijkstrachou-mi-gv1m/
    public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {
        List<int[]> g[] = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int x = edges[i][0], y = edges[i][1];
            g[x].add(new int[]{y, i});
            g[y].add(new int[]{x, i}); // 建圖，額外記錄邊的編號
        }

        int[][] dis = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i != source) {
                dis[i][0] = dis[i][1] = Integer.MAX_VALUE;
            }
        }

        dijkstra(g, edges, destination, dis, 0, 0);

        int delta = target - dis[destination][0];
        if (delta < 0) return new int[][]{}; // -1 全改為 1 時，最短路比 target 還大，無解

        dijkstra(g, edges, destination, dis, delta, 1);
        if (dis[destination][1] < target) return new int[][]{};  // 最短路無法再變大，無法達到 target


        for (int[] e : edges) {
            if (e[2] == -1) e[2] = 1;// 剩余沒修改的邊全部改成 1
        }

        return edges;
    }

    // 樸素 Dijkstra 算法
    // 這裡 k 表示第一次/第二次
    private void dijkstra(List<int[]> g[], int[][] edges, int destination, int[][] dis, int delta, int k) {
        int n = g.length;
        boolean[] vis = new boolean[n];
        for (; ; ) {
            // 找到當前最短路，去更新它的鄰居的最短路
            // 根據數學歸納法，dis[x][k] 一定是最短路長度
            int x = -1;
            for (int i = 0; i < n; i++) {
                if (!vis[i] && (x < 0 || dis[i][k] < dis[x][k])) {
                    x = i;
                }
            }

            if (x == destination) return; // 起點 source 到終點 destination 的最短路已確定
            vis[x] = true; // 標記，在後續的循環中無需反復更新 x 到其余點的最短路長度
            for (int[] e : g[x]) {
                int y = e[0], eid = e[1];
                int wt = edges[eid][2];
                if (wt == -1) wt = 1; // -1 改成 1
                if (k == 1 && edges[eid][2] == -1) {
                    // 第二次 Dijkstra，改成 w
                    int w = delta + dis[y][0] - dis[x][1];
                    if (w > wt) {
                        edges[eid][2] = wt = w; // 直接在 edges 上修改
                    }
                }
                // 更新最短路
                dis[y][k] = Math.min(dis[y][k], dis[x][k] + wt);
            }
        }
    }


    // 比較容易理解的版本
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2699.Modify%20Graph%20Edge%20Weights/README.md

    private final int inf = 2000000000;

    public int[][] modifiedGraphEdges2(int n, int[][] edges, int source, int destination, int target) {
        // 先不考慮邊權為 -1 的邊，
        // 使用 Dijkstra 算法求出從 source 到 destination 的最短距離 d
        long d = dijkstra(edges, n, source, destination);

        // 存在一條完全由正權邊組成的最短路徑，此時無論我們如何修改邊權為 -1 的邊，
        // 都無法使得 source 到 destination 的最短距離等於 target，
        // 因此不存在滿足題意的修改方案，返回一個空數組即可。
        if (d < target) {
            return new int[0][];
        }
        boolean ok = d == target;
        for (int[] e : edges) {
            // 尋找邊權為 -1 的邊
            if (e[2] > 0) {
                continue;
            }

            // 如果 d == target，說明存在一條完全由正權邊組成的、長度為 target 的最短路徑，
            // 此時可以將所有邊權為 -1 的邊修改為最大值 inf 即可。
            if (ok) {
                e[2] = inf;
                continue;
            }

            // 如果 d > target，可以嘗試往圖中加入一條邊權為 -1 的邊，將邊權設置為 1 ，
            // 然後再次使用 Dijkstra 算法求出從 source 到 destination 的最短距離 d
            // 1. 如果最短距離 d <= target，說明加入這條邊後，
            //    可以使得最短路變短，而且最短路也一定經過這條邊，
            //    那麼只需要將這條邊的邊權改為 target - d + 1，就可以使得最短路等於 target。
            //    然後我們將其余的邊權為 -1 的邊修改為最大值 inf 即可。
            // 2. 如果最短距離 d > target，
            //    說明加入這條邊後，最短路不會變短，那麼我們貪心地將這條邊的邊權保持為 -1，
            //    然後繼續嘗試加入其余的邊權為 -1 的邊。
            e[2] = 1;
            d = dijkstra(edges, n, source, destination);
            if (d <= target) {
                ok = true;
                e[2] += target - d;
            }
        }
        return ok ? edges : new int[0][];
    }

    private long dijkstra(int[][] edges, int n, int src, int dest) {
        int[][] g = new int[n][n];
        long[] dist = new long[n];
        Arrays.fill(dist, inf);
        dist[src] = 0;
        for (int[] f : g) {
            Arrays.fill(f, inf);
        }

        // 建立雙向圖
        for (int[] e : edges) {
            int a = e[0], b = e[1], w = e[2];
            if (w == -1) { // 先不考慮邊權為 -1 的邊
                continue;
            }
            g[a][b] = w;  // 兩個方向同樣權重
            g[b][a] = w;
        }

        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            int k = -1;
            for (int j = 0; j < n; ++j) {
                if (!vis[j] && (k == -1 || dist[k] > dist[j])) {
                    k = j;
                }
            }
            vis[k] = true;
            for (int j = 0; j < n; ++j) {
                dist[j] = Math.min(dist[j], dist[k] + g[k][j]);
            }
        }
        return dist[dest];
    }

}

