package EndlessCheng.GenreMenu.Graph.Floyd;

import java.util.Arrays;

public class MinimumCostII {

    // https://leetcode.cn/problems/minimum-cost-to-convert-string-ii/solutions/2577877/zi-dian-shu-floyddp-by-endlesscheng-oi2r/
    private Node root = new Node();
    private int sid = 0;

    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        // 初始化距離矩陣
        int m = cost.length;
        int[][] dis = new int[m * 2][m * 2];
        for (int i = 0; i < dis.length; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE / 2);
            dis[i][i] = 0;
        }
        for (int i = 0; i < cost.length; i++) {
            int x = put(original[i]);
            int y = put(changed[i]);
            dis[x][y] = Math.min(dis[x][y], cost[i]);
        }

        // Floyd 求任意兩點最短路
        for (int k = 0; k < sid; k++) {
            for (int i = 0; i < sid; i++) {
                if (dis[i][k] == Integer.MAX_VALUE / 2) {
                    continue;
                }
                for (int j = 0; j < sid; j++) {
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }

        char[] s = source.toCharArray();
        char[] t = target.toCharArray();
        int n = s.length;
        long[] f = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            // 不修改 source[i]
            f[i] = s[i] == t[i] ? f[i + 1] : Long.MAX_VALUE / 2;
            Node p = root, q = root;
            for (int j = i; j < n; j++) {
                p = p.son[s[j] - 'a'];
                q = q.son[t[j] - 'a'];
                if (p == null || q == null) {
                    break;
                }
                if (p.sid < 0 || q.sid < 0) {
                    continue;
                }
                // 修改從 i 到 j 的這一段
                int d = dis[p.sid][q.sid];
                if (d < Integer.MAX_VALUE / 2) {
                    f[i] = Math.min(f[i], d + f[j + 1]);
                }
            }
        }
        return f[0] < Long.MAX_VALUE / 2 ? f[0] : -1;
    }

    private int put(String s) {
        Node o = root;
        for (char b : s.toCharArray()) {
            int i = b - 'a';
            if (o.son[i] == null) {
                o.son[i] = new Node();
            }
            o = o.son[i];
        }
        if (o.sid < 0) {
            o.sid = sid++;
        }
        return o.sid;
    }

    class Node {
        Node[] son = new Node[26];
        int sid = -1; // 字符串的編號
    }
}
