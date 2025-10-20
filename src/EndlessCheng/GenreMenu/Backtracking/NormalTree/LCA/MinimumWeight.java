package EndlessCheng.GenreMenu.Backtracking.NormalTree.LCA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumWeight {

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
