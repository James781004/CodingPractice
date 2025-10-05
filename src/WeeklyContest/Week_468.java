package WeeklyContest;

import java.util.*;

public class Week_468 {

    // https://leetcode.cn/problems/bitwise-or-of-even-numbers-in-an-array/solutions/3787931/bian-li-pythonjavacgo-by-endlesscheng-j09e/
    public int evenNumberBitwiseORs(int[] nums) {
        int ans = 0;
        for (int x : nums) {
            if (x % 2 == 0) {
                ans |= x;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-total-subarray-value-i/solutions/3787895/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-4vir/
    public long maxTotalValue(int[] nums, int k) {
        int mn = Integer.MAX_VALUE;
        int mx = 0;
        for (int x : nums) {
            mn = Math.min(mn, x);
            mx = Math.max(mx, x);
        }
        return (long) (mx - mn) * k;
    }


    // https://leetcode.cn/problems/split-and-merge-array-transformation/solutions/3787914/bfspythonjavacgo-by-endlesscheng-86ya/
    public int minSplitMerge(int[] nums1, int[] nums2) {
        List<Integer> nums2List = toList(nums2);
        int n = nums1.length;
        Set<List<Integer>> vis = new HashSet<>();
        vis.add(toList(nums1));
        List<List<Integer>> q = List.of(toList(nums1));
        for (int ans = 0; ; ans++) {
            List<List<Integer>> tmp = q;
            q = new ArrayList<>();
            for (List<Integer> a : tmp) {
                if (a.equals(nums2List)) {
                    return ans;
                }
                for (int l = 0; l < n; l++) {
                    for (int r = l + 1; r <= n; r++) {
                        List<Integer> sub = a.subList(l, r);
                        List<Integer> b = new ArrayList<>(a);
                        b.subList(l, r).clear(); // 從 b 中移除 sub
                        for (int i = 0; i <= b.size(); i++) {
                            List<Integer> c = new ArrayList<>(b);
                            c.addAll(i, sub);
                            if (vis.add(c)) {
                                q.add(c);
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Integer> toList(int[] a) {
        List<Integer> b = new ArrayList<>();
        for (int x : a) {
            b.add(x);
        }
        return b;
    }


    // https://leetcode.cn/problems/maximum-total-subarray-value-ii/solutions/3787870/dui-dan-diao-zhan-rmq-by-time-v5-0yg3/
    record E(int v, int i, int l, int r) {
    } // v 為 i 代表的子數組 a[l:r] 的值

    public long maxTotalValue2(int[] a, int k) {
        int n = a.length;
        RMQ rmq = new RMQ(a, Math::min);// 區間最小值預處理，此時 min(a[l:r]) = rmq.query(l, r);
        PriorityQueue<E> pq = new PriorityQueue<>((i, j) -> j.v - i.v);
        int[] stk = new int[n];// 單調棧獲取每個 i 代表的 [l, r]
        int z = -1;
        for (int i = 0; i < n; i++) {
            while (z != -1 && a[stk[z]] <= a[i]) {
                int j = stk[z--], l = z == -1 ? 0 : stk[z] + 1, r = i - 1;
                pq.add(new E(a[j] - rmq.query(l, r), j, l, r));
            }
            stk[++z] = i;
        }
        while (z != -1) {
            int i = stk[z--], l = z == -1 ? 0 : stk[z] + 1, r = n - 1;
            pq.add(new E(a[i] - rmq.query(l, r), i, l, r));
        }
        long ans = 0;
        while (k > 0) {
            E e = pq.poll();
            if (e.v == 0) {
                break;
            }
            int mn = rmq.query(e.l, e.r), l = e.l, r = e.i - 1, m;
            while (l <= r) {
                m = (l + r) >> 1;
                if (rmq.query(m, e.i - 1) == mn) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            int pre = l;
            l = e.i + 1;
            r = e.r;
            while (l <= r) {
                m = (l + r) >> 1;
                if (rmq.query(e.i + 1, m) == mn) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            int suf = r;
            // 此時 e.i 代表的區間 [pre, suf] 所有子區間最小值都是 > mn 的
            long cnt = Math.min((long) (e.i - e.l + 1) * (e.r - e.i + 1) - (long) (e.i - pre + 1) * (suf - e.i + 1), k);
            ans += cnt * (a[e.i] - mn);
            k -= cnt;
            pq.add(new E(a[e.i] - rmq.query(pre, suf), e.i, pre, suf));
        }
        return ans;
    }

    class RMQ {
        private static final int MAXN = 50000;
        private static final int[] log = new int[MAXN + 1];

        static {
            log[0] = -1;
            for (int i = 1; i <= MAXN; i++) {
                log[i] = log[i >> 1] + 1;
            }
        }

        private int[][] st;

        private final java.util.function.IntBinaryOperator op;

        public RMQ(int[] a, java.util.function.IntBinaryOperator op) {
            this.op = op;
            int n = a.length, m = log[n] + 1;
            st = new int[m][n];
            System.arraycopy(a, 0, st[0], 0, n);
            for (int j = 1; j < m; j++) {
                for (int i = 0; i + (1 << j) - 1 < n; i++) {
                    st[j][i] = op.applyAsInt(st[j - 1][i], st[j - 1][i + (1 << (j - 1))]);
                }
            }
        }

        public int query(int l, int r) {
            int g = log[r - l + 1];
            return op.applyAsInt(st[g][l], st[g][r - (1 << g) + 1]);
        }
    }


}









