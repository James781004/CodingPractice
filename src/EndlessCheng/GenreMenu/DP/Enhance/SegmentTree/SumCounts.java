package EndlessCheng.GenreMenu.DP.Enhance.SegmentTree;

import java.util.HashMap;

public class SumCounts {

    // https://leetcode.cn/problems/subarrays-distinct-element-sum-of-squares-ii/solutions/2502897/yi-bu-bu-ti-shi-ni-si-kao-ben-ti-pythonj-zhhs/
    private long[] sum;
    private int[] todo;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[n * 4];
        todo = new int[n * 4];

        long ans = 0, s = 0;
        var last = new HashMap<Integer, Integer>();
        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            int j = last.getOrDefault(x, 0);
            s += queryAndAdd1(1, 1, n, j + 1, i) * 2 + i - j;
            ans = (ans + s) % 1_000_000_007;
            last.put(x, i);
        }
        return (int) ans;
    }

    private void do_(int o, int l, int r, int add) {
        sum[o] += (long) add * (r - l + 1);
        todo[o] += add;
    }

    // o=1  [l,r] 1<=l<=r<=n
    // 把 [L,R] 加一，同時返回加一之前的區間和
    private long queryAndAdd1(int o, int l, int r, int L, int R) {
        if (L <= l && r <= R) {
            long res = sum[o];
            do_(o, l, r, 1);
            return res;
        }

        int m = (l + r) / 2;
        int add = todo[o];
        if (add != 0) {
            do_(o * 2, l, m, add);
            do_(o * 2 + 1, m + 1, r, add);
            todo[o] = 0;
        }

        long res = 0;
        if (L <= m) res += queryAndAdd1(o * 2, l, m, L, R);
        if (m < R) res += queryAndAdd1(o * 2 + 1, m + 1, r, L, R);
        sum[o] = sum[o * 2] + sum[o * 2 + 1];
        return res;
    }


}
