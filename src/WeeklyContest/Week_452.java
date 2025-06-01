package WeeklyContest;

import java.util.*;

public class Week_452 {

    // https://leetcode.cn/problems/partition-array-into-two-equal-product-subsets/solutions/3690767/mei-ju-zuo-chu-fa-on-by-lzyqweasdzxc-cbwa/
    public boolean checkEqualPartitions(int[] nums, long target) {
        long num = target;
        int times = 0;
        for (int j : nums) {
            // 不夠除了 就乘一下target
            if (num % j != 0) {
                num = num * target;
                times++;
            }
            // 如果已經乘了1次。或者乘完了一次還不夠整除 那麽不是一個合法輸入 直接返回
            if (times > 1 || num % j != 0) {
                return false;
            }
            num = num / j;
        }
        // 如果只乘了一次 並且結果恰好為1 說名合法
        return times == 1 && num == 1;
    }


    // https://leetcode.cn/problems/minimum-absolute-difference-in-sliding-submatrix/solutions/3690788/bao-li-mei-ju-pythonjavacgo-by-endlessch-ecsp/
    public int[][] minAbsDiff(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] ans = new int[m - k + 1][n - k + 1];
        int[] a = new int[k * k];
        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                int idx = 0;
                for (int x = 0; x < k; x++) {
                    for (int y = 0; y < k; y++) {
                        a[idx++] = grid[i + x][j + y];
                    }
                }
                Arrays.sort(a);

                int res = Integer.MAX_VALUE;
                for (int p = 1; p < a.length; p++) {
                    if (a[p] > a[p - 1]) {
                        res = Math.min(res, a[p] - a[p - 1]);
                    }
                }
                if (res < Integer.MAX_VALUE) {
                    ans[i][j] = res;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-moves-to-clean-the-classroom/solutions/3690747/bfs-by-endlesscheng-rpk6/
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    private record Node(int x, int y, int e, int mask) {
    }

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        int[][] idx = new int[m][n];
        int cntL = 0, sx = 0, sy = 0;
        for (int i = 0; i < m; i++) {
            String row = classroom[i];
            for (int j = 0; j < n; j++) {
                char b = row.charAt(j);
                if (b == 'L') {
                    idx[i][j] = cntL++; // 給垃圾分配編號
                } else if (b == 'S') {
                    sx = i;
                    sy = j;
                }
            }
        }

        int u = 1 << cntL;
        boolean[][][][] vis = new boolean[m][n][energy + 1][u];
        vis[sx][sy][energy][u - 1] = true;

        List<Node> q = new ArrayList<>();
        q.add(new Node(sx, sy, energy, u - 1));
        for (int ans = 0; !q.isEmpty(); ans++) {
            List<Node> tmp = q;
            q = new ArrayList<>();
            for (Node p : tmp) {
                if (p.mask == 0) { // 所有垃圾清理完畢
                    return ans;
                }
                if (p.e == 0) { // 走不動了
                    continue;
                }
                for (int[] d : DIRS) {
                    int x = p.x + d[0], y = p.y + d[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && classroom[x].charAt(y) != 'X') {
                        int newE = classroom[x].charAt(y) == 'R' ? energy : p.e - 1;
                        int newMask = classroom[x].charAt(y) == 'L' ? p.mask & ~(1 << idx[x][y]) : p.mask;
                        if (!vis[x][y][newE][newMask]) {
                            vis[x][y][newE][newMask] = true;
                            q.add(new Node(x, y, newE, newMask));
                        }
                    }
                }
            }
        }
        return -1;
    }


    // https://leetcode.cn/problems/maximize-count-of-distinct-primes-after-split/solutions/3690759/xian-duan-shu-you-xu-ji-he-by-endlessche-j3nm/
    private static final int MX = 100_000;
    private static final boolean[] np = new boolean[MX + 1];
    private static boolean initialized = false;

    // 這樣寫比 static block 快
    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        np[0] = np[1] = true;
        for (int i = 2; i <= MX; i++) {
            if (!np[i]) {
                for (int j = i; j <= MX / i; j++) { // 避免溢出的寫法
                    np[i * j] = true;
                }
            }
        }
    }

    public int[] maximumCount(int[] nums, int[][] queries) {
        init();

        int n = nums.length;
        Map<Integer, TreeSet<Integer>> pos = new HashMap<>();

        LazySegmentTree t = new LazySegmentTree(n, 0);
        for (int i = 0; i < n; i++) {
            int v = nums[i];
            if (!np[v]) {
                pos.computeIfAbsent(v, k -> new TreeSet<>()).add(i);
            }
        }

        // 對出現次數大於 1 的質數，其最小最大索引之間的區間 +1
        for (TreeSet<Integer> s : pos.values()) {
            if (s.size() > 1) {
                t.update(s.first(), s.last(), 1);
            }
        }

        int[] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int[] q = queries[qi];
            int i = q[0];
            int v = q[1];
            int old = nums[i];
            nums[i] = v;

            // 刪除舊值 old 的影響
            if (!np[old]) {
                TreeSet<Integer> s = pos.get(old);
                if (s.size() > 1) {
                    t.update(s.first(), s.last(), -1);
                }
                s.remove(i);
                if (s.size() > 1) {
                    t.update(s.first(), s.last(), 1);
                } else if (s.isEmpty()) {
                    pos.remove(old);
                }
            }

            // 插入新值 v 的影響
            if (!np[v]) {
                TreeSet<Integer> s = pos.computeIfAbsent(v, k -> new TreeSet<>());
                if (s.size() > 1) {
                    t.update(s.first(), s.last(), -1);
                }
                s.add(i);
                if (s.size() > 1) {
                    t.update(s.first(), s.last(), 1);
                }
            }

            ans[qi] = pos.size() + t.query(0, n - 1);
        }
        return ans;
    }


    class LazySegmentTree {
        // 懶標記初始值
        private static final int TODO_INIT = 0; // **根據題目修改**

        private static final class Node {
            int val;
            int todo;
        }

        // 合並兩個 val
        private int mergeVal(int a, int b) {
            return Math.max(a, b);
        }

        // 合並兩個懶標記
        private int mergeTodo(int a, int b) {
            return a + b;
        }

        // 把懶標記作用到 node 子樹（本例為區間加）
        private void apply(int node, int l, int r, int todo) {
            Node cur = tree[node];
            // 計算 tree[node] 區間的整體變化
            cur.val += todo;
            cur.todo = mergeTodo(todo, cur.todo);
        }

        private final int n;
        private final Node[] tree;

        // 線段樹維護一個長為 n 的數組（下標從 0 到 n-1），元素初始值為 initVal
        public LazySegmentTree(int n, int initVal) {
            this.n = n;
            int[] a = new int[n];
            Arrays.fill(a, initVal);
            tree = new Node[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
            build(a, 1, 0, n - 1);
        }

        // 線段樹維護數組 a
        public LazySegmentTree(int[] a) {
            n = a.length;
            tree = new Node[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
            build(a, 1, 0, n - 1);
        }

        // 用 f 更新 [ql, qr] 中的每個 a[i]
        // 0 <= ql <= qr <= n-1
        // 時間復雜度 O(log n)
        public void update(int ql, int qr, int f) {
            update(1, 0, n - 1, ql, qr, f);
        }

        // 返回用 mergeVal 合並所有 a[i] 的計算結果，其中 i 在閉區間 [ql, qr] 中
        // 0 <= ql <= qr <= n-1
        // 時間復雜度 O(log n)
        public int query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr);
        }

        // 把當前節點的懶標記下傳給左右兒子
        private void spread(int node, int l, int r) {
            int todo = tree[node].todo;
            if (todo == TODO_INIT) { // 沒有需要下傳的信息
                return;
            }
            int m = (l + r) / 2;
            apply(node * 2, l, m, todo);
            apply(node * 2 + 1, m + 1, r, todo);
            tree[node].todo = TODO_INIT; // 下傳完畢
        }

        // 合並左右兒子的 val 到當前節點的 val
        private void maintain(int node) {
            tree[node].val = mergeVal(tree[node * 2].val, tree[node * 2 + 1].val);
        }

        // 用 a 初始化線段樹
        // 時間復雜度 O(n)
        private void build(int[] a, int node, int l, int r) {
            tree[node] = new Node();
            tree[node].todo = TODO_INIT;
            if (l == r) { // 葉子
                tree[node].val = a[l]; // 初始化葉節點的值
                return;
            }
            int m = (l + r) / 2;
            build(a, node * 2, l, m); // 初始化左子樹
            build(a, node * 2 + 1, m + 1, r); // 初始化右子樹
            maintain(node);
        }

        private void update(int node, int l, int r, int ql, int qr, int f) {
            if (ql <= l && r <= qr) { // 當前子樹完全在 [ql, qr] 內
                apply(node, l, r, f);
                return;
            }
            spread(node, l, r);
            int m = (l + r) / 2;
            if (ql <= m) { // 更新左子樹
                update(node * 2, l, m, ql, qr, f);
            }
            if (qr > m) { // 更新右子樹
                update(node * 2 + 1, m + 1, r, ql, qr, f);
            }
            maintain(node);
        }

        private int query(int node, int l, int r, int ql, int qr) {
            if (ql <= l && r <= qr) { // 當前子樹完全在 [ql, qr] 內
                return tree[node].val;
            }
            spread(node, l, r);
            int m = (l + r) / 2;
            if (qr <= m) { // [ql, qr] 在左子樹
                return query(node * 2, l, m, ql, qr);
            }
            if (ql > m) { // [ql, qr] 在右子樹
                return query(node * 2 + 1, m + 1, r, ql, qr);
            }
            int lRes = query(node * 2, l, m, ql, qr);
            int rRes = query(node * 2 + 1, m + 1, r, ql, qr);
            return mergeVal(lRes, rRes);
        }
    }

}









