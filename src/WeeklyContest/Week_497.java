package WeeklyContest;

import java.util.*;

public class Week_497 {

    // https://leetcode.cn/problems/find-the-degree-of-each-vertex/solutions/3949911/java-by-yuan-mu-et-t0x6/
    public int[] findDegrees(int[][] matrix) {
        int n = matrix.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix[i][j] == 1) {
                    arr[i] += 1;
                    arr[j] += 1;
                }
            }
        }
        return arr;
    }


    // https://leetcode.cn/problems/angles-of-a-triangle/solutions/3949898/yu-xian-ding-li-pythonjavacgo-by-endless-zwhx/
    public double[] internalAngles(int[] sides) {
        Arrays.sort(sides);
        int a = sides[0], b = sides[1], c = sides[2];
        if (a + b <= c) {
            return new double[0];
        }

        final double rad = 180 / Math.PI;
        double A = Math.acos((double) (b * b + c * c - a * a) / (double) (b * c * 2)) * rad;
        double B = Math.acos((double) (a * a + c * c - b * b) / (double) (a * c * 2)) * rad;
        return new double[]{A, B, 180 - A - B}; // 小邊對小角
    }


    // https://leetcode.cn/problems/longest-balanced-substring-after-one-swap/solutions/3949900/qian-zhui-he-ji-lu-qian-liang-ci-chu-xia-524r/
    public int longestBalanced(String S) {
        char[] s = S.toCharArray();
        int total0 = 0;
        for (char c : s) {
            if (c == '0') {
                total0++;
            }
        }
        int total1 = s.length - total0;

        Map<Integer, List<Integer>> pos = new HashMap<>();
        pos.computeIfAbsent(0, v -> new ArrayList<>()).add(-1); // 見 525 題

        int ans = 0;
        int sum = 0; // 前綴和

        for (int i = 0; i < s.length; i++) {
            sum += (s[i] - '0') * 2 - 1;

            List<Integer> p = pos.computeIfAbsent(sum, v -> new ArrayList<>());
            p.add(i);

            // 不交換
            ans = Math.max(ans, i - p.get(0));

            // 交換子串內的一個 1 和子串外的一個 0
            p = pos.get(sum - 2);
            if (p != null) {
                if ((i - p.get(0) - 2) / 2 < total0) {
                    ans = Math.max(ans, i - p.get(0));
                } else if (p.size() > 1) {
                    ans = Math.max(ans, i - p.get(1));
                }
            }

            // 交換子串內的一個 0 和子串外的一個 1
            p = pos.get(sum + 2);
            if (p != null) {
                if ((i - p.get(0) - 2) / 2 < total1) {
                    ans = Math.max(ans, i - p.get(0));
                } else if (p.size() > 1) {
                    ans = Math.max(ans, i - p.get(1));
                }
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/good-subsequence-queries/solutions/3949925/jie-lun-xian-duan-shu-by-endlesscheng-aihf/
    public int countGoodSubseq(int[] nums, int p, int[][] queries) {
        int n = nums.length;
        int cntP = 0;
        for (int x : nums) {
            if (x % p == 0) {
                cntP++;
            }
        }

        SegmentTree t = new SegmentTree(nums, p);
        int ans = 0;

        for (int[] q : queries) {
            int i = q[0];
            int x = q[1];

            if (nums[i] % p == 0) {
                cntP--;
            }
            if (x % p == 0) {
                cntP++;
            }
            nums[i] = x;
            t.update(i, x);

            if (t.queryAll() == p && (cntP < n || n > 7 || t.check(n))) {
                ans++;
            }
        }

        return ans;
    }


    // 模板來源 https://leetcode.cn/circle/discuss/mOr1u6/
    // 線段樹有兩個下標，一個是線段樹節點的下標，另一個是線段樹維護的區間的下標
    // 節點的下標：從 1 開始，如果你想改成從 0 開始，需要把左右兒子下標分別改成 node*2+1 和 node*2+2
    // 區間的下標：從 0 開始
    class SegmentTree {
        private final int targetGcd;
        private final int n;
        private final int[] tree;

        // 線段樹維護數組 a
        public SegmentTree(int[] a, int targetGcd) {
            this.targetGcd = targetGcd;
            n = a.length;
            tree = new int[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
            build(a, 1, 0, n - 1);
        }

        // 更新 a[i]
        // 時間復雜度 O(log n)
        public void update(int i, int val) {
            update(1, 0, n - 1, i, val);
        }

        // 返回用 gcd 合並所有 a[i] 的計算結果，其中 i 在閉區間 [ql, qr] 中
        // 時間復雜度 O(log n)
        public int query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr);
        }

        public int queryAll() {
            return tree[1];
        }

        public boolean check(int n) {
            for (int i = 0; i < n; i++) {
                if (gcd(query(0, i - 1), query(i + 1, n - 1)) == targetGcd) {
                    return true;
                }
            }
            return false;
        }

        // 合並左右兒子的 val 到當前節點的 val
        private void maintain(int node) {
            tree[node] = gcd(tree[node * 2], tree[node * 2 + 1]);
        }

        // 用 a 初始化線段樹
        // 時間復雜度 O(n)
        private void build(int[] a, int node, int l, int r) {
            if (l == r) { // 葉子
                tree[node] = a[l] % targetGcd == 0 ? a[l] : 0; // 初始化葉節點的值
                return;
            }
            int m = (l + r) / 2;
            build(a, node * 2, l, m); // 初始化左子樹
            build(a, node * 2 + 1, m + 1, r); // 初始化右子樹
            maintain(node);
        }

        private void update(int node, int l, int r, int i, int val) {
            if (l == r) { // 葉子（到達目標）
                tree[node] = val % targetGcd == 0 ? val : 0;
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

        private int query(int node, int l, int r, int ql, int qr) {
            if (ql > qr) {
                return 0;
            }
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
            int lRes = query(node * 2, l, m, ql, qr);
            int rRes = query(node * 2 + 1, m + 1, r, ql, qr);
            return gcd(lRes, rRes);
        }

        private int gcd(int a, int b) {
            while (a != 0) {
                int tmp = a;
                a = b % a;
                b = tmp;
            }
            return b;
        }
    }


}









