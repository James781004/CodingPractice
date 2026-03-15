package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_493 {

    // https://leetcode.cn/problems/count-commas-in-range-ii/solutions/3925828/heng-kan-cheng-ling-ce-cheng-feng-gong-x-8qaw/
    public long countCommas(long n) {
        long ans = 0;
        // 從右往左，枚舉逗號的位置
        for (long low = 1000; low <= n; low *= 1000) {
            // [low, n] 中的每個數都在這個位置上有一個逗號
            ans += n - low + 1;
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-arithmetic-sequence-after-changing-at-most-one-element/solutions/3925831/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-hys8/
    public int longestArithmetic(int[] nums) {
        int[] pre = calc(nums);
        int ans = 0;
        for (int x : pre) {
            ans = Math.max(ans, x);
        }
        ans++;

        int n = nums.length;
        if (ans >= n) { // 整個數組是等差的，或者修改端點元素後是等差的
            return n;
        }

        reverse(nums);
        int[] suf = calc(nums);
        reverse(suf);
        reverse(nums);
        // 注意 max(pre) == max(suf)，無需重復計算

        for (int i = 1; i < n - 1; i++) {
            // 把 nums[i] 改成 d2 / 2
            int d2 = nums[i + 1] - nums[i - 1];
            if (d2 % 2 != 0) { // d2 / 2 必須是整數
                continue;
            }

            boolean okLeft = i > 1 && nums[i - 1] - nums[i - 2] == d2 / 2;
            boolean okRight = i + 2 < n && nums[i + 2] - nums[i + 1] == d2 / 2;

            if (okLeft && okRight) {
                ans = Math.max(ans, pre[i - 1] + 1 + suf[i + 1]);
            } else if (okLeft) {
                ans = Math.max(ans, pre[i - 1] + 2);
            } else if (okRight) {
                ans = Math.max(ans, suf[i + 1] + 2);
            }
        }

        return ans;
    }

    private int[] calc(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n];
        pre[0] = 1;
        pre[1] = 2;
        for (int i = 2; i < n; i++) {
            if (nums[i - 2] + nums[i] == 2 * nums[i - 1]) { // 三個數等差
                pre[i] = pre[i - 1] + 1;
            } else {
                pre[i] = 2;
            }
        }
        return pre;
    }

    private void reverse(int[] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }


    // https://leetcode.cn/problems/maximum-points-activated-with-one-addition/solutions/3925832/bing-cha-ji-pythonjavacgo-by-endlesschen-n00l/
    public int maxActivated(int[][] points) {
        // 哈希表並查集
        Map<Long, Long> fa = new HashMap<>();

        final long OFFSET = (long) 3e9;
        for (int[] p : points) {
            long fx = find(p[0], fa);
            long fy = find(p[1] + OFFSET, fa);
            fa.put(fx, fy);
        }

        // 統計連通塊的大小
        Map<Long, Integer> size = new HashMap<>();
        for (int[] p : points) {
            size.merge(find(p[0], fa), 1, Integer::sum);
        }

        int mx1 = 0, mx2 = 0;
        for (int sz : size.values()) {
            if (sz > mx1) {
                mx2 = mx1;
                mx1 = sz;
            } else if (sz > mx2) {
                mx2 = sz;
            }
        }

        return mx1 + mx2 + 1;
    }

    private long find(long x, Map<Long, Long> fa) {
        if (!fa.containsKey(x)) {
            fa.put(x, x);
        }
        long fx = fa.get(x);
        if (fx != x) {
            long root = find(fx, fa);
            fa.put(x, root);
            return root;
        }
        return x;
    }


}









