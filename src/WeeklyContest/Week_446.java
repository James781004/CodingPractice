package WeeklyContest;

public class Week_446 {

    // https://leetcode.cn/problems/calculate-score-after-performing-instructions/solutions/3656619/vis-shu-zu-biao-ji-fang-wen-guo-de-wei-z-fd00/
    public long calculateScore(String[] instructions, int[] values) {
        int n = instructions.length;
        boolean[] vis = new boolean[n];
        long ans = 0;
        int i = 0;
        while (0 <= i && i < n && !vis[i]) {
            vis[i] = true;
            if (instructions[i].charAt(0) == 'a') {
                ans += values[i];
                i++;
            } else {
                i += values[i];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/make-array-non-decreasing/solutions/3656591/tan-xin-by-tsreaper-qk1i/
    public int maximumPossibleSize(int[] nums) {
        int n = nums.length;
        int ans = 1, now = nums[0];
        for (int i = 1; i < n; i++) {
            // 每次找到下一個大於等於的數，放到結果數組末尾即可
            if (nums[i] >= now) {
                ans++;
                now = nums[i];
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/find-x-value-of-array-i/solutions/3656580/zi-shu-zu-dp-shua-biao-fa-pythonjavacgo-8lonk/
    public long[] resultArray(int[] nums, int k) {
        int n = nums.length;
        long[] ans = new long[k];
        int[][] f = new int[n + 1][k];
        for (int i = 0; i < n; i++) {
            int v = nums[i];
            f[i + 1][v % k] = 1;
            for (int y = 0; y < k; y++) {
                f[i + 1][(int) ((long) y * v % k)] += f[i][y]; // 刷表法
            }
            for (int x = 0; x < k; x++) {
                ans[x] += f[i + 1][x];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-x-value-of-array-ii/solutions/3656583/dan-dian-xiu-gai-xian-duan-shu-by-endles-i3jy/
    public int[] resultArrayII(int[] nums, int k, int[][] queries) {
        SegmentTree t = new SegmentTree(nums, k);
        int n = nums.length;
        int[] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int[] q = queries[qi];
            t.update(q[0], q[1]);
            ans[qi] = t.query(q[2], n - 1, q[3]);
        }
        return ans;
    }

    // 線段樹有兩個下標，一個是線段樹節點的下標，另一個是線段樹維護的區間的下標
    // 節點的下標：從 1 開始，如果你想改成從 0 開始，需要把左右兒子下標分別改成 node*2+1 和 node*2+2
    // 區間的下標：從 0 開始
    static class SegmentTree {
        private record Data(int m, int[] cnt) {
        }

        private final int k;
        private final int n;
        private final Data[] tree;

        // 合並兩個 Data
        private Data mergeData(Data a, Data b) {
            int[] cnt = a.cnt.clone();
            for (int m = 0; m < k; m++) {
                cnt[a.m * m % k] += b.cnt[m];
            }
            return new Data(a.m * b.m % k, cnt);
        }

        private Data newData(int val) {
            int m = val % k;
            int[] cnt = new int[k];
            cnt[m] = 1;
            return new Data(m, cnt);
        }

        // 線段樹維護數組 a
        public SegmentTree(int[] a, int k) {
            this.k = k;
            n = a.length;
            tree = new Data[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
            build(a, 1, 0, n - 1);
        }

        // 更新 a[i] 為 mergeData(a[i], val)
        // 時間復雜度 O(log n)
        public void update(int i, int val) {
            update(1, 0, n - 1, i, val);
        }

        // 返回用 mergeData 合並所有 a[i] 的計算結果，其中 i 在閉區間 [ql, qr] 中
        // 時間復雜度 O(log n)
        public int query(int ql, int qr, int x) {
            return query(1, 0, n - 1, ql, qr).cnt[x];
        }

        // 合並左右兒子的 val 到當前節點的 val
        private void maintain(int node) {
            tree[node] = mergeData(tree[node * 2], tree[node * 2 + 1]);
        }

        // 用 a 初始化線段樹
        // 時間復雜度 O(n)
        private void build(int[] a, int node, int l, int r) {
            if (l == r) { // 葉子
                tree[node] = newData(a[l]); // 初始化葉節點的值
                return;
            }
            int m = (l + r) / 2;
            build(a, node * 2, l, m); // 初始化左子樹
            build(a, node * 2 + 1, m + 1, r); // 初始化右子樹
            maintain(node);
        }

        private void update(int node, int l, int r, int i, int val) {
            if (l == r) { // 葉子（到達目標）
                tree[node] = newData(val);
                return;
            }
            int m = (l + r) / 2;
            if (i <= m) { // i 在左子樹
                update(node * 2, l, m, i, val);
            } else { // i 在右子樹
                update(node * 2 + 1, m + 1, r, i, val);
            }
            maintain(node);
        }

        private Data query(int node, int l, int r, int ql, int qr) {
            if (ql <= l && r <= qr) { // 當前子樹完全在 [ql, qr] 內
                return tree[node];
            }
            int m = (l + r) / 2;
            if (qr <= m) { // [ql, qr] 在左子樹
                return query(node * 2, l, m, ql, qr);
            }
            if (ql > m) { // [ql, qr] 在右子樹
                return query(node * 2 + 1, m + 1, r, ql, qr);
            }
            Data lRes = query(node * 2, l, m, ql, qr);
            Data rRes = query(node * 2 + 1, m + 1, r, ql, qr);
            return mergeData(lRes, rRes);
        }
    }

}









