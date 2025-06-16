package WeeklyContest;

import java.util.*;

public class Week_454 {

    // https://leetcode.cn/problems/generate-tag-for-video-caption/solutions/3700601/3582-wei-shi-pin-biao-ti-sheng-cheng-bia-9rkv/
    public String generateTag(String caption) {
        StringBuffer sb = new StringBuffer("#");
        int length = caption.length();
        for (int i = 0; i < length && sb.length() < 100; i++) {
            char c = caption.charAt(i);
            if (Character.isLetter(c)) {
                if (sb.length() > 1 && !Character.isLetter(caption.charAt(i - 1))) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }


    // https://leetcode.cn/problems/count-special-triplets/solutions/3700554/mei-ju-zhong-jian-qian-hou-zhui-fen-jie-5uad8/
    public int specialTriplets(int[] nums) {
        final int MOD = 1_000_000_007;
        Map<Integer, Integer> suf = new HashMap<>();
        for (int x : nums) {
            suf.merge(x, 1, Integer::sum); // suf[x]++
        }

        long ans = 0;
        Map<Integer, Integer> pre = new HashMap<>();
        for (int x : nums) { // x = nums[j]
            suf.merge(x, -1, Integer::sum); // suf[x]-- // 撤銷
            // 現在 pre 中的是 [0,j-1]，suf 中的是 [j+1,n-1]
            ans += (long) pre.getOrDefault(x * 2, 0) * suf.getOrDefault(x * 2, 0);
            pre.merge(x, 1, Integer::sum); // pre[x]++
        }
        return (int) (ans % MOD);
    }


    // https://leetcode.cn/problems/maximum-product-of-first-and-last-elements-of-a-subsequence/solutions/3700555/nao-jin-ji-zhuan-wan-mei-ju-you-wei-hu-z-93zo/
    public long maximumProduct(int[] nums, int m) {
        long ans = Long.MIN_VALUE;
        int mn = Integer.MAX_VALUE;
        int mx = Integer.MIN_VALUE;
        for (int i = m - 1; i < nums.length; i++) {
            // 維護左邊 [0,i-m+1] 中的最小值和最大值
            int y = nums[i - m + 1];
            mn = Math.min(mn, y);
            mx = Math.max(mx, y);
            // 枚舉右
            long x = nums[i];
            ans = Math.max(ans, Math.max(x * mn, x * mx));
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-weighted-median-node-in-tree/solutions/3700556/mo-ban-zui-jin-gong-gong-zu-xian-lcapyth-6ekj/
    public int[] findMedian(int n, int[][] edges, int[][] queries) {
        LcaBinaryLifting g = new LcaBinaryLifting(edges);

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0];
            int y = queries[i][1];
            if (x == y) {
                ans[i] = x;
                continue;
            }
            int lca = g.getLCA(x, y);
            long disXY = g.dis[x] + g.dis[y] - g.dis[lca] * 2;
            long half = (disXY + 1) / 2;
            if (g.dis[x] - g.dis[lca] >= half) { // 答案在 x-lca 路徑中
                // 先往上跳至多 half-1，然後再跳一步，就是至少 half
                int to = g.uptoDis(x, half - 1);
                ans[i] = g.pa[to][0];
            } else { // 答案在 y-lca 路徑中
                // 從 y 出發至多 dis_xy-half，就是從 x 出發至少 half
                ans[i] = g.uptoDis(y, disXY - half);
            }
        }
        return ans;
    }

    class LcaBinaryLifting {
        private final int[] depth;
        public final long[] dis; // 如果是無權樹（邊權為 1），dis 可以去掉，用 depth 代替
        public final int[][] pa;

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

        // 從 x 往上跳【至多】d 距離，返回最遠能到達的節點
        public int uptoDis(int x, long d) {
            long dx = dis[x];
            for (int i = pa[x].length - 1; i >= 0; i--) {
                int p = pa[x][i];
                if (p != -1 && dx - dis[p] <= d) { // 可以跳至多 d
                    x = p;
                }
            }
            return x;
        }
    }


}









