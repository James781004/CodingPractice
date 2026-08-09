package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_514 {

    // https://leetcode.cn/problems/minimum-total-price-after-applying-discounts/solutions/4009091/pai-xu-bu-deng-shi-bi-mian-fu-dian-yun-s-wfjg/
    public double minPrice(int[] prices, int[] discounts) {
        Arrays.sort(prices);
        Arrays.sort(discounts);

        long ans = 0;
        for (int i = 0; i < prices.length; i++) {
            int d = i < discounts.length ? discounts[discounts.length - 1 - i] : 0;
            ans += (long) prices[prices.length - 1 - i] * (100 - d);
        }
        return ans / 100.;
    }


    // https://leetcode.cn/problems/weighted-sum-of-a-tree/solutions/4009096/liang-ci-dfspythonjavacgo-by-endlesschen-fcb2/
    public long weightedSum(int[] parent, int[] nums) {
        int n = parent.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i);
        }

        int h = getH(0, -1, g);
        return dfs(0, -1, h, g, nums);
    }

    private int getH(int x, int fa, List<Integer>[] g) {
        int h = 0;
        for (int y : g[x]) {
            if (y != fa) {
                h = Math.max(h, getH(y, x, g));
            }
        }
        return h + 1;
    }

    private long dfs(int x, int fa, int weight, List<Integer>[] g, int[] nums) {
        long ans = (long) nums[x] * weight;
        for (int y : g[x]) {
            if (y != fa) {
                ans += dfs(y, x, weight - 1, g, nums);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-area-of-two-non-overlapping-square-submatrices/solutions/4009076/qian-hou-zhui-fen-jie-zhuan-hua-cheng-22-entn/
    public int maxArea(int[][] mat) {
        return Math.max(calc(mat), calc(transpose(mat)));
    }

    private int calc(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        // 221. 最大正方形（空間優化寫法）
        // 計算 mat 下半部分的最大正方形的邊長
        int[] sufMax = new int[m];
        int[] f = new int[n + 1];
        int mx = 0;
        for (int i = m - 1; i > 0; i--) {
            int last = 0;
            for (int j = 0; j < n; j++) {
                int x = mat[i][j];
                if (x == 1) {
                    int tmp = f[j + 1];
                    f[j + 1] = Math.min(Math.min(last, f[j + 1]), f[j]) + 1;
                    last = tmp;
                    mx = Math.max(mx, f[j + 1]);
                } else {
                    f[j + 1] = 0;
                    last = 0;
                }
            }
            sufMax[i] = mx;
        }

        int ans = 0;
        // 計算 mat 上半部分的最大正方形的邊長
        int preMax = 0;
        Arrays.fill(f, 0);
        for (int i = 0; i < m - 1; i++) {
            int last = 0;
            for (int j = 0; j < n; j++) {
                int x = mat[i][j];
                if (x == 1) {
                    int tmp = f[j + 1];
                    f[j + 1] = Math.min(Math.min(last, f[j + 1]), f[j]) + 1;
                    last = tmp;
                    preMax = Math.max(preMax, f[j + 1]);
                } else {
                    f[j + 1] = 0;
                    last = 0;
                }
            }
            if (sufMax[i + 1] <= ans) {
                break; // 最優性優化：繼續循環不會讓 ans 變大
            }
            ans = Math.max(ans, Math.min(preMax, sufMax[i + 1])); // 題目要求兩個正方形的邊長相等
        }

        return ans * ans;
    }

    // 轉置矩陣 mat
    private int[][] transpose(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = mat[j][i];
            }
        }
        return a;
    }


    // https://leetcode.cn/problems/peaks-in-array-ii/solutions/4009084/yong-fen-zhi-si-kao-yong-xian-duan-shu-w-2b40/
    public long[] countOfPeaks(int[] nums, int[][] queries) {
        int cnt1 = 0;
        for (int[] q : queries) {
            cnt1 += 2 - q[0];
        }

        int n = nums.length;
        SegmentTree t = new SegmentTree(nums);
        long[] ans = new long[cnt1];
        int k = 0;
        for (int[] q : queries) {
            if (q[0] == 1) {
                ans[k++] = t.query(q[1], q[2]);
                continue;
            }
            int i = q[1];
            nums[i] = q[2];
            for (int j = Math.max(i - 1, 1); j <= Math.min(i + 1, n - 2); j++) {
                // 注：這裡可以優化一下，如果更新前後 hasPeak 不變，則不調用 t.update
                boolean hasPeak = nums[j - 1] < nums[j] && nums[j] > nums[j + 1];
                t.update(j, hasPeak);
            }
        }
        return ans;
    }

    static class SegmentTree {
        private record Data(long cnt, int pre, int suf, int len, boolean hasPeak) {
        }

        private final int n;
        private final Data[] tree;

        private Data mergeData(Data a, Data b) {
            long cnt = a.cnt + b.cnt + (long) a.len * b.len - (long) a.suf * b.pre;
            int pre = a.hasPeak ? a.pre : a.len + b.pre;
            int suf = b.hasPeak ? b.suf : b.len + a.suf;
            return new Data(cnt, pre, suf, a.len + b.len, a.hasPeak || b.hasPeak);
        }

        public SegmentTree(int[] a) {
            n = a.length;
            tree = new Data[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
            build(a, 1, 0, n - 1);
        }

        public void update(int i, boolean hasPeak) {
            update(1, 0, n - 1, i, hasPeak);
        }

        public long query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr).cnt;
        }

        private void maintain(int node) {
            tree[node] = mergeData(tree[node * 2], tree[node * 2 + 1]);
        }

        private void build(int[] a, int node, int l, int r) {
            if (l == r) { // 葉子
                boolean hasPeak = 0 < l && l < n - 1 && a[l - 1] < a[l] && a[l] > a[l + 1];
                tree[node] = new Data(0, 1, 1, 1, hasPeak); // 初始化葉節點的值
                return;
            }
            int m = (l + r) >>> 1;
            build(a, node * 2, l, m); // 初始化左子樹
            build(a, node * 2 + 1, m + 1, r); // 初始化右子樹
            maintain(node);
        }

        private void update(int node, int l, int r, int i, boolean hasPeak) {
            if (l == r) { // 葉子（到達目標）
                Data d = tree[node];
                tree[node] = new Data(d.cnt, d.pre, d.suf, d.len, hasPeak);
                return;
            }
            int m = (l + r) >>> 1;
            if (i <= m) { // i 在左子樹
                update(node * 2, l, m, i, hasPeak);
            } else { // i 在右子樹
                update(node * 2 + 1, m + 1, r, i, hasPeak);
            }
            maintain(node);
        }

        private Data query(int node, int l, int r, int ql, int qr) {
            if (ql <= l && r <= qr) { // 當前子樹完全在 [ql, qr] 內
                return tree[node];
            }
            int m = (l + r) >>> 1;
            if (qr <= m) { // [ql, qr] 與右子樹無交集，僅需遞歸左子樹
                return query(node * 2, l, m, ql, qr);
            }
            if (ql > m) { // [ql, qr] 與左子樹無交集，僅需遞歸右子樹
                return query(node * 2 + 1, m + 1, r, ql, qr);
            }
            // [ql, qr] 與左右子樹均有交集，分別遞歸，然後合並結果
            Data lRes = query(node * 2, l, m, ql, qr);
            Data rRes = query(node * 2 + 1, m + 1, r, ql, qr);
            return mergeData(lRes, rRes);
        }
    }


}










