package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_478 {

    // https://leetcode.cn/problems/maximum-substrings-with-distinct-start/solutions/3845369/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-1e4a/
    public int maxDistinct(String s) {
        boolean[] vis = new boolean[26];
        int ans = 0;
        for (char c : s.toCharArray()) {
            c -= 'a';
            if (!vis[c]) {
                vis[c] = true;
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-elements-with-at-least-k-greater-values/solutions/3845384/pai-xu-er-fen-cha-zhao-kuai-su-xuan-ze-p-z3a6/
    public int countElements(int[] nums, int k) {
        int n = nums.length;
        if (k == 0) {
            return n;
        }

        // 排序+二分查找求小於第 k 大的元素個數
        Arrays.sort(nums);
        return lowerBound(nums, nums[n - k]); // 小於第 k 大的元素個數
    }

    private int lowerBound(int[] nums, int target) {
        int left = -1;
        int right = nums.length;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }


    // https://leetcode.cn/problems/minimum-absolute-distance-between-mirror-pairs/solutions/3845358/mei-ju-you-wei-hu-zuo-pythonjavacgo-by-e-wlzl/
    public int minMirrorPairDistance(int[] nums) {
        int n = nums.length;
        int ans = n;
        Map<Integer, Integer> lastIndex = new HashMap<>(n, 1); // 預分配空間

        for (int j = 0; j < n; j++) {
            int x = nums[j];
            Integer i = lastIndex.get(x);
            if (i != null) {
                ans = Math.min(ans, j - i);
            }

            // 計算 reverse(x)，不用字符串
            int rev = 0;
            for (; x > 0; x /= 10) {
                rev = rev * 10 + x % 10;
            }
            lastIndex.put(rev, j);
        }

        return ans < n ? ans : -1;
    }


    // https://leetcode.cn/problems/minimum-operations-to-equalize-subarrays/solutions/3845357/zhong-wei-shu-tan-xin-ke-chi-jiu-hua-xia-etpv/
    public long[] minOperations(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        int[] left = new int[n];
        for (int i = 1; i < n; i++) {
            left[i] = nums[i] % k == nums[i - 1] % k ? left[i - 1] : i;
        }

        // 准備離散化
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        // 構建可持久化線段樹
        Node[] t = new Node[n + 1];
        t[0] = Node.build(0, n - 1);
        for (int i = 0; i < n; i++) {
            int j = Arrays.binarySearch(sorted, nums[i]);
            t[i + 1] = t[i].add(j, nums[i]);
        }

        long[] ans = new long[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int[] q = queries[qi];
            int l = q[0];
            int r = q[1];
            if (left[r] > l) { // 無解
                ans[qi] = -1;
                continue;
            }

            r++; // 改成左閉右開

            // 計算區間中位數
            int sz = r - l;
            int i = t[r].kth(t[l], sz / 2 + 1);
            long median = sorted[i]; // 離散化後的值 -> 原始值

            // 計算區間所有元素到中位數的距離和
            long totalSum = t[r].sum - t[l].sum;
            long[] res = t[r].query(t[l], i);
            long cntLeft = res[0];
            long sumLeft = res[1];
            long sum = median * cntLeft - sumLeft; // 藍色面積
            sum += totalSum - sumLeft - median * (sz - cntLeft); // 綠色面積
            ans[qi] = sum / k; // 操作次數 = 距離和 / k
        }

        return ans;
    }


    static class Node {
        private final int l;
        private final int r;
        private Node lo;
        private Node ro;
        private int cnt;
        public long sum;

        private void maintain() {
            cnt = lo.cnt + ro.cnt;
            sum = lo.sum + ro.sum;
        }

        public Node(int l, int r, Node lo, Node ro, int cnt, long sum) {
            this.l = l;
            this.r = r;
            this.lo = lo;
            this.ro = ro;
            this.cnt = cnt;
            this.sum = sum;
        }

        public Node(int l, int r) {
            this(l, r, null, null, 0, 0);
        }

        public static Node build(int l, int r) {
            Node o = new Node(l, r);
            if (l == r) {
                return o;
            }
            int mid = (l + r) / 2;
            o.lo = build(l, mid);
            o.ro = build(mid + 1, r);
            return o;
        }

        // 在線段樹的位置 i 添加 val
        public Node add(int i, int val) {
            Node o = new Node(l, r, lo, ro, cnt, sum); // 復制當前節點
            if (l == r) {
                o.cnt++;
                o.sum += val;
                return o;
            }
            int mid = (l + r) / 2;
            if (i <= mid) {
                o.lo = o.lo.add(i, val);
            } else {
                o.ro = o.ro.add(i, val);
            }
            o.maintain();
            return o;
        }

        // 查詢 old 和 this 對應區間的第 k 小，k 從 1 開始
        public int kth(Node old, int k) {
            if (l == r) {
                return l;
            }
            int cntL = lo.cnt - old.lo.cnt;
            if (k <= cntL) { // 答案在左子樹中
                return lo.kth(old.lo, k);
            }
            return ro.kth(old.ro, k - cntL); // 答案在右子樹中
        }

        // 查詢 old 和 this 對應區間，有多少個數 <= i，這些數的元素和是多少
        public long[] query(Node old, int i) {
            if (r <= i) {
                return new long[]{cnt - old.cnt, sum - old.sum};
            }
            long[] left = lo.query(old.lo, i);
            long cnt = left[0];
            long s = left[1];
            int mid = (l + r) / 2;
            if (i > mid) {
                long[] right = ro.query(old.ro, i);
                cnt += right[0];
                s += right[1];
            }
            return new long[]{cnt, s};
        }
    }


}









