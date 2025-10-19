package WeeklyContest;

import java.util.*;

public class Week_472 {

    // https://leetcode.cn/problems/smallest-missing-multiple-of-k/solutions/3809929/hashset-by-admiring-hellmangl3-zor4/
    public int missingMultiple(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        int ans = 0;

        for (int right = 0; right < nums.length; right++) {
            int num = nums[right];
            if (num % k == 0) {
                set.add(num);
            }
        }
        for (int i = k; i <= 10000; i += k) {
            int a = i;
            if (set.add(a)) {
                ans = a;
                break;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-balanced-subarray-i/solutions/3809920/mei-ju-by-tsreaper-200b/
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int ans = 0; // 用來儲存找到的最長長度

        // 外層迴圈：枚舉子陣列的起始位置 i
        for (int i = 0; i < n; i++) {
            // 用來儲存當前子陣列 [i, j] 中出現的奇數種類
            Set<Integer> odd = new HashSet<>();
            // 用來儲存當前子陣列 [i, j] 中出現的偶數種類
            Set<Integer> even = new HashSet<>();

            // 內層迴圈：枚舉子陣列的結束位置 j
            for (int j = i; j < n; j++) {
                int num = nums[j];

                // 判斷當前數字是奇數還是偶數
                if ((num & 1) != 0) { // 奇數
                    odd.add(num);
                } else { // 偶數
                    even.add(num);
                }

                // 檢查是否達到「平衡」條件：奇數種類數 == 偶數種類數
                if (odd.size() == even.size()) {
                    // 如果平衡，則更新最長長度
                    // 當前子陣列的長度是 j - i + 1
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/lexicographically-smallest-permutation-greater-than-target/solutions/3809944/dao-xu-tan-xin-pythonjavacgo-by-endlessc-fdf5/
    public String lexGreaterPermutation(String s, String target) {
        char[] t = target.toCharArray();
        int n = t.length;
        int[] left = new int[26];
        for (int i = 0; i < n; i++) {
            left[s.charAt(i) - 'a']++;
            left[t[i] - 'a']--;
        }
        StringBuilder ans = new StringBuilder(target);

        // 從右往左嘗試
        next:
        for (int i = n - 1; i >= 0; i--) {
            int b = t[i] - 'a';
            left[b]++;
            for (int c : left) {
                if (c < 0) { // 前面無法做到全部一樣
                    continue next;
                }
            }

            // target[i] 增大到 j
            for (int j = b + 1; j < 26; j++) {
                if (left[j] == 0) {
                    continue;
                }

                left[j]--;
                ans.setCharAt(i, (char) ('a' + j));
                ans.setLength(i + 1);

                for (int k = 0; k < 26; k++) {
                    char fillChar = (char) ('a' + k);
                    int count = left[k];
                    for (int l = 0; l < count; l++) {
                        ans.append(fillChar);
                    }
                }
                return ans.toString();
            }
        }
        return "";
    }


    // https://leetcode.cn/problems/longest-balanced-subarray-ii/solutions/3809924/fen-kuai-wei-hu-qian-zhui-he-pythonjavac-rt79/
    public int longestBalanced2(int[] nums) {
        int n = nums.length;
        LazySegmentTree t = new LazySegmentTree(n + 1, 0);

        Map<Integer, Integer> last = new HashMap<>();
        int ans = 0;
        int curSum = 0;

        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            int v = x % 2 > 0 ? 1 : -1;
            if (!last.containsKey(x)) {
                curSum += v;
                t.update(i, n, v);
            } else {
                int j = last.get(x);
                t.update(j, i - 1, -v);
            }
            last.put(x, i);

            int j = t.findFirst(0, i - 1, curSum);
            if (j >= 0) {
                ans = Math.max(ans, i - j);
            }
        }
        return ans;
    }


    static class LazySegmentTree {
        // 懶標記初始值
        private static final int TODO_INIT = 0;

        private static final class Node {
            int[] val;
            int todo;
        }

        // 合並兩個 val
        private int[] mergeVal(int[] a, int[] b) {
            return new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
        }

        // 合並兩個懶標記
        private int mergeTodo(int a, int b) {
            return a + b;
        }

        // 把懶標記作用到 node 子樹（本例為區間加）
        private void apply(int node, int l, int r, int todo) {
            Node cur = tree[node];
            // 計算 tree[node] 區間的整體變化
            cur.val[0] += todo;
            cur.val[1] += todo;
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

        // 查詢 [ql,qr] 內第一個等於 target 的元素下標
        // 找不到返回 -1
        // 0 <= ql <= qr <= n-1
        // 時間復雜度 O(log n)
        public int findFirst(int ql, int qr, int target) {
            return findFirst(1, 0, n - 1, ql, qr, target);
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
                tree[node].val = new int[]{a[l], a[l]}; // 初始化葉節點的值
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

        private int findFirst(int node, int l, int r, int ql, int qr, int target) {
            if (l > qr || r < ql || target < tree[node].val[0] || target > tree[node].val[1]) {
                return -1;
            }
            if (l == r) {
                return l;
            }
            spread(node, l, r);
            int m = (l + r) / 2;
            int idx = findFirst(node * 2, l, m, ql, qr, target);
            if (idx < 0) {
                idx = findFirst(node * 2 + 1, m + 1, r, ql, qr, target);
            }
            return idx;
        }
    }

}









