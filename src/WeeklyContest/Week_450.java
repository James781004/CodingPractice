package WeeklyContest;

import java.util.*;

public class Week_450 {

    // https://leetcode.cn/problems/smallest-index-with-digit-sum-equal-to-index/solutions/3679963/you-hua-zhi-duo-xun-huan-dao-i27pythonja-dr0d/
    public int smallestIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int s = 0;
            for (int x = nums[i]; x > 0; x /= 10) {
                s += x % 10;
            }
            if (s == i) {
                return i;
            }
        }
        return -1;
    }


    // https://leetcode.cn/problems/minimum-swaps-to-sort-by-digit-sum/solutions/3679949/jian-tu-ji-suan-lian-tong-kuai-de-ge-shu-6i2r/
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] a = new int[n][3];
        for (int i = 0; i < n; i++) {
            int s = 0;
            for (int x = nums[i]; x > 0; x /= 10) {
                s += x % 10;
            }
            a[i][0] = s;
            a[i][1] = nums[i];
            a[i][2] = i;
        }

        Arrays.sort(a, (p, q) -> p[0] != q[0] ? p[0] - q[0] : p[1] - q[1]);

        UnionFind u = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            u.merge(i, a[i][2]);
        }
        return n - u.cc;
    }

    class UnionFind {
        private final int[] fa; // 代表元
        public int cc; // 連通塊個數

        UnionFind(int n) {
            // 一開始有 n 個集合 {0}, {1}, ..., {n-1}
            // 集合 i 的代表元是自己，大小為 1
            fa = new int[n];
            for (int i = 0; i < n; i++) {
                fa[i] = i;
            }
            cc = n;
        }

        // 返回 x 所在集合的代表元
        // 同時做路徑壓縮，也就是把 x 所在集合中的所有元素的 fa 都改成代表元
        public int find(int x) {
            // 如果 fa[x] == x，則表示 x 是代表元
            if (fa[x] != x) {
                fa[x] = find(fa[x]); // fa 改成代表元
            }
            return fa[x];
        }

        // 把 from 所在集合合並到 to 所在集合中
        public void merge(int from, int to) {
            int x = find(from);
            int y = find(to);
            if (x == y) { // from 和 to 在同一個集合，不做合並
                return;
            }
            fa[x] = y; // 合並集合。修改後就可以認為 from 和 to 在同一個集合了
            cc--; // 成功合並，連通塊個數減一
        }
    }


    // https://leetcode.cn/problems/grid-teleportation-traversal/solutions/3679952/0-1-bfspythonjavacgo-by-endlesscheng-8nj9/
    private static final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int minMoves(String[] matrix) {
        int m = matrix.length, n = matrix[0].length();
        if (matrix[m - 1].charAt(n - 1) == '#') {
            return -1;
        }

        List<int[]>[] pos = new ArrayList[26];
        Arrays.setAll(pos, i -> new ArrayList<>());
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = matrix[i].charAt(j);
                if (Character.isUpperCase(c)) {
                    pos[c - 'A'].add(new int[]{i, j});
                }
            }
        }

        int[][] dis = new int[m][n];
        for (int[] row : dis) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dis[0][0] = 0;

        Deque<int[]> q = new ArrayDeque<>();
        q.addFirst(new int[]{0, 0});

        while (!q.isEmpty()) {
            int[] p = q.pollFirst();
            int x = p[0], y = p[1];
            if (x == m - 1 && y == n - 1) {
                return dis[x][y];
            }

            char c = matrix[x].charAt(y);
            if (c != '.') {
                // 使用所有傳送門
                for (int[] portal : pos[c - 'A']) {
                    int px = portal[0], py = portal[1];
                    if (dis[x][y] < dis[px][py]) {
                        dis[px][py] = dis[x][y];
                        q.addFirst(new int[]{px, py});
                    }
                }
                pos[c - 'A'].clear(); // 避免重復使用傳送門
            }

            // 下面代碼和普通 BFS 是一樣的
            for (int[] d : DIRS) {
                int nx = x + d[0], ny = y + d[1];
                if (0 <= nx && nx < m && 0 <= ny && ny < n && matrix[nx].charAt(ny) != '#') {
                    if (dis[x][y] + 1 < dis[nx][ny]) {
                        dis[nx][ny] = dis[x][y] + 1;
                        q.addLast(new int[]{nx, ny});
                    }
                }
            }
        }

        return -1;
    }


    // https://leetcode.cn/problems/minimum-weighted-subgraph-with-the-required-paths-ii/solutions/3679956/mo-ban-zui-jin-gong-gong-zu-xian-lcapyth-3bco/
    public int[] minimumWeight(int[][] edges, int[][] queries) {
        LcaBinaryLifting g = new LcaBinaryLifting(edges);
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int a = q[0], b = q[1], c = q[2];
            ans[i] = (g.getDis(a, b) + g.getDis(b, c) + g.getDis(a, c)) / 2; // 不會溢出
        }
        return ans;
    }

    class LcaBinaryLifting {
        private final int[] depth;
        private final int[] dis;
        private final int[][] pa;

        public LcaBinaryLifting(int[][] edges) {
            int n = edges.length + 1;
            int m = 32 - Integer.numberOfLeadingZeros(n); // n 的二進制長度
            List<int[]>[] g = new ArrayList[n];
            Arrays.setAll(g, e -> new ArrayList<>());
            for (int[] e : edges) {
                int x = e[0], y = e[1], w = e[2];
                g[x].add(new int[]{y, w});
                g[y].add(new int[]{x, w});
            }

            depth = new int[n];
            dis = new int[n];
            pa = new int[n][m];
            dfs(g, 0, -1);

            for (int i = 0; i < m - 1; i++) {
                for (int x = 0; x < n; x++) {
                    int p = pa[x][i];
                    pa[x][i + 1] = p < 0 ? -1 : pa[p][i];
                }
            }
        }

        private void dfs(List<int[]>[] g, int x, int fa) {
            pa[x][0] = fa;
            for (int[] e : g[x]) {
                int y = e[0];
                if (y != fa) {
                    depth[y] = depth[x] + 1;
                    dis[y] = dis[x] + e[1];
                    dfs(g, y, x);
                }
            }
        }

        public int getKthAncestor(int node, int k) {
            for (; k > 0; k &= k - 1) {
                node = pa[node][Integer.numberOfTrailingZeros(k)];
            }
            return node;
        }

        // 返回 x 和 y 的最近公共祖先（節點編號從 0 開始）
        public int getLCA(int x, int y) {
            if (depth[x] > depth[y]) {
                int tmp = y;
                y = x;
                x = tmp;
            }
            y = getKthAncestor(y, depth[y] - depth[x]); // 使 y 和 x 在同一深度
            if (y == x) {
                return x;
            }
            for (int i = pa[x].length - 1; i >= 0; i--) {
                int px = pa[x][i], py = pa[y][i];
                if (px != py) {
                    x = px;
                    y = py; // 同時往上跳 2^i 步
                }
            }
            return pa[x][0];
        }

        // 返回 x 到 y 的距離（最短路長度）
        public int getDis(int x, int y) {
            return dis[x] + dis[y] - dis[getLCA(x, y)] * 2;
        }
    }

}









