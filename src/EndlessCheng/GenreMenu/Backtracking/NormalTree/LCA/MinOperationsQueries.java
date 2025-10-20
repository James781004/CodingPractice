package EndlessCheng.GenreMenu.Backtracking.NormalTree.LCA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinOperationsQueries {

    // https://leetcode.cn/problems/minimum-edge-weight-equilibrium-queries-in-a-tree/solutions/2424060/lca-mo-ban-by-endlesscheng-j54b/
    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1], w = e[2] - 1;
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }

        int m = 32 - Integer.numberOfLeadingZeros(n); // n 的二進制長度
        var pa = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(pa[i], -1);
        }
        var cnt = new int[n][m][26];
        var depth = new int[n];
        dfs(0, -1, g, pa, cnt, depth);

        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                if (p != -1) {
                    int pp = pa[p][i];
                    pa[x][i + 1] = pp;
                    for (int j = 0; j < 26; j++) {
                        cnt[x][i + 1][j] = cnt[x][i][j] + cnt[p][i][j];
                    }
                }
            }
        }

        var ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int x = queries[qi][0], y = queries[qi][1];
            int pathLen = depth[x] + depth[y];
            var cw = new int[26];
            if (depth[x] > depth[y]) {
                int temp = x;
                x = y;
                y = temp;
            }

            // 讓 y 和 x 在同一深度
            for (int k = depth[y] - depth[x]; k > 0; k &= k - 1) {
                int i = Integer.numberOfTrailingZeros(k);
                int p = pa[y][i];
                for (int j = 0; j < 26; ++j) {
                    cw[j] += cnt[y][i][j];
                }
                y = p;
            }

            if (y != x) {
                for (int i = m - 1; i >= 0; i--) {
                    int px = pa[x][i];
                    int py = pa[y][i];
                    if (px != py) {
                        for (int j = 0; j < 26; j++) {
                            cw[j] += cnt[x][i][j] + cnt[y][i][j];
                        }
                        x = px;
                        y = py; // x 和 y 同時上跳 2^i 步
                    }
                }
                for (int j = 0; j < 26; j++) {
                    cw[j] += cnt[x][0][j] + cnt[y][0][j];
                }
                x = pa[x][0];
            }

            int lca = x;
            pathLen -= depth[lca] * 2;
            int maxCw = 0;
            for (int i = 0; i < 26; i++) {
                maxCw = Math.max(maxCw, cw[i]);
            }
            ans[qi] = pathLen - maxCw;
        }
        return ans;
    }

    private void dfs(int x, int fa, List<int[]>[] g, int[][] pa, int[][][] cnt, int[] depth) {
        pa[x][0] = fa;
        for (var e : g[x]) {
            int y = e[0], w = e[1];
            if (y != fa) {
                cnt[y][0][w] = 1;
                depth[y] = depth[x] + 1;
                dfs(y, x, g, pa, cnt, depth);
            }
        }
    }

}
