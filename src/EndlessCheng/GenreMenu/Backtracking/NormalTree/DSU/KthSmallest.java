package EndlessCheng.GenreMenu.Backtracking.NormalTree.DSU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KthSmallest {

    // https://leetcode.cn/problems/kth-smallest-path-xor-sum/solutions/3705587/chi-xian-qi-fa-shi-he-bing-you-xu-ji-he-pm0km/
    public int[] kthSmallest(int[] par, int[] vals, int[][] queries) {
        int n = par.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[par[i]].add(i);
        }

        int[] a = new int[n];
        int[] in = new int[n];
        int[] out = new int[n]; // 左閉右開
        dfs(0, 0, g, vals, a, in, out);

        // 排序
        int[] b = Arrays.copyOf(a, n);
        Arrays.sort(b);

        // 離散化
        for (int i = 0; i < n; i++) {
            a[i] = Arrays.binarySearch(b, a[i]) + 1; // 從 1 開始
        }

        int nq = queries.length;
        int blockSize = (int) Math.ceil(n / Math.sqrt(nq * 2)); // 塊大小

        record Query(int bid, int l, int r, int k, int qid) {
        }
        Query[] qs = new Query[nq];
        for (int i = 0; i < nq; i++) {
            int[] q = queries[i];
            int l = in[q[0]], r = out[q[0]]; // 左閉右開
            qs[i] = new Query(l / blockSize, l, r, q[1], i);
        }

        Arrays.sort(qs, (x, y) -> {
            if (x.bid != y.bid) {
                return x.bid - y.bid;
            }
            // 奇偶化排序
            return x.bid % 2 == 0 ? x.r - y.r : y.r - x.r;
        });

        int[] cnt = new int[n + 1];
        FenwickTree t = new FenwickTree(n + 1);

        int[] ans = new int[nq];
        int l = 0, r = 0;
        for (Query q : qs) {
            int ql = q.l, qr = q.r, k = q.k, i = q.qid;
            while (l < ql) move(a[l++], -1, cnt, t);
            while (l > ql) move(a[--l], 1, cnt, t);
            while (r < qr) move(a[r++], 1, cnt, t);
            while (r > qr) move(a[--r], -1, cnt, t);

            int res = t.kth(k) - 1; // 離散化時 +1 了，所以要 -1
            ans[i] = res < n ? b[res] : -1;
        }
        return ans;
    }

    private int dfn = 0;

    private void dfs(int x, int xorVal, List<Integer>[] g, int[] vals, int[] a, int[] in, int[] out) {
        in[x] = dfn;
        xorVal ^= vals[x];
        a[dfn++] = xorVal;
        for (int y : g[x]) {
            dfs(y, xorVal, g, vals, a, in, out);
        }
        out[x] = dfn;
    }

    private void move(int v, int delta, int[] cnt, FenwickTree t) {
        if (delta > 0) {
            if (cnt[v] == 0) {
                t.update(v, 1);
            }
            cnt[v]++;
        } else {
            cnt[v]--;
            if (cnt[v] == 0) {
                t.update(v, -1);
            }
        }
    }

    class FenwickTree {
        private final int[] tree;
        private final int hb;

        // 使用下標 1 到 n
        public FenwickTree(int n) {
            tree = new int[n + 1];
            hb = Integer.highestOneBit(n);
        }

        // a[i] 增加 val
        // 1 <= i <= n
        // 時間復雜度 O(log n)
        public void update(int i, int val) {
            for (; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        // 求第 k 小（k 從 1 開始）
        // 如果不存在第 k 小，返回 n+1
        // 時間復雜度 O(log n)
        public int kth(int k) {
            int res = 0;
            for (int b = hb; b > 0; b >>= 1) {
                int next = res | b;
                if (next < tree.length && k > tree[next]) {
                    k -= tree[next];
                    res = next;
                }
            }
            return res + 1;
        }
    }


}
