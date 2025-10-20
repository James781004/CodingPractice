package EndlessCheng.GenreMenu.Backtracking.NormalTree.LCA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LcaBinaryLifting {
    // https://leetcode.cn/discuss/post/3142882/fen-xiang-gun-ti-dan-lian-biao-er-cha-sh-6srp/
    private final int[] depth;
    private final long[] dis; // 如果是無權樹（邊權為 1），dis 可以去掉，用 depth 代替
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
        dis = new long[n];
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
        // 使 y 和 x 在同一深度
        y = getKthAncestor(y, depth[y] - depth[x]);
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
    public long getDis(int x, int y) {
        return dis[x] + dis[y] - dis[getLCA(x, y)] * 2;
    }

}
