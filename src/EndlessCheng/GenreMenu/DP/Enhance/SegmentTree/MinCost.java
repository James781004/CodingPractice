package EndlessCheng.GenreMenu.DP.Enhance.SegmentTree;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-to-split-an-array/solutions/2072725/by-endlesscheng-05s0/
    public int minCost(int[] nums, int k) {
        int n = nums.length, ans = 0;
        mn = new int[n * 4];
        todo = new int[n * 4];
        int[] last = new int[n], last2 = new int[n];
        for (int i = 1; i <= n; ++i) {
            int x = nums[i - 1];
            update(1, 1, n, i, i, ans); // 相當於設置 f[i+1] 的值
            update(1, 1, n, last[x] + 1, i, -1);
            if (last[x] > 0) update(1, 1, n, last2[x] + 1, last[x], 1);
            ans = k + query(1, 1, n, 1, i);
            last2[x] = last[x];
            last[x] = i;
        }
        return ans + n;
    }

    // Lazy 線段樹模板（區間加，查詢區間最小）
    private int[] mn, todo;

    private void do_(int o, int v) {
        mn[o] += v;
        todo[o] += v;
    }

    private void spread(int o) {
        int v = todo[o];
        if (v != 0) {
            do_(o * 2, v);
            do_(o * 2 + 1, v);
            todo[o] = 0;
        }
    }

    // 區間 [L,R] 內的數都加上 v   o,l,r=1,1,n
    private void update(int o, int l, int r, int L, int R, int v) {
        if (L <= l && r <= R) {
            do_(o, v);
            return;
        }
        spread(o);
        int m = (l + r) / 2;
        if (m >= L) update(o * 2, l, m, L, R, v);
        if (m < R) update(o * 2 + 1, m + 1, r, L, R, v);
        mn[o] = Math.min(mn[o * 2], mn[o * 2 + 1]);
    }

    // 查詢區間 [L,R] 的最小值   o,l,r=1,1,n
    private int query(int o, int l, int r, int L, int R) {
        if (L <= l && r <= R)
            return mn[o];
        spread(o);
        int m = (l + r) / 2;
        if (m >= R) return query(o * 2, l, m, L, R);
        if (m < L) return query(o * 2 + 1, m + 1, r, L, R);
        return Math.min(query(o * 2, l, m, L, R), query(o * 2 + 1, m + 1, r, L, R));
    }


}
