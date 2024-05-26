package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_399 {
    // https://leetcode.cn/problems/find-the-number-of-good-pairs-ii/solutions/2790631/tong-ji-yin-zi-ge-shu-pythonjavacgo-by-e-bl3o/
    public long numberOfPairs(int[] nums1, int[] nums2, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums1) {
            if (x % k != 0) {
                continue;
            }
            x /= k;
            for (int d = 1; d * d <= x; d++) { // 保存 x / k 的所有可能因數，加入因數表 cnt
                if (x % d > 0) {
                    continue;
                }
                cnt.merge(d, 1, Integer::sum);
                if (d * d < x) { // d 如果是 x 的因數，x / d 也要加入 cnt
                    cnt.merge(x / d, 1, Integer::sum);
                }
            }
        }

        long ans = 0;
        for (int x : nums2) { // 從因數表中查看 x 存在多少個
            ans += cnt.getOrDefault(x, 0);
        }
        return ans;
    }


    // https://leetcode.cn/problems/string-compression-iii/solutions/2790666/mo-ni-pythonjavacgo-by-endlesscheng-3hk7/
    public String compressedString(String word) {
        StringBuilder t = new StringBuilder();
        char[] s = word.toCharArray();
        int i0 = -1; // 左指針
        for (int i = 0; i < s.length; i++) {
            char c = s[i];
            if (i + 1 == s.length || c != s[i + 1]) { // 右指針下一位到底，或者下一位是不同字元
                int k = i - i0; // 區間長度
                for (int j = 0; j < k / 9; j++) { // 題目規定前綴最長是9
                    t.append('9').append(c);
                }
                if (k % 9 > 0) { // 剩下不滿9的前綴
                    t.append((char) ('0' + (k % 9))).append(c);
                }
                i0 = i; // 左指針更新位置
            }
        }
        return t.toString();
    }


    // https://leetcode.cn/problems/maximum-sum-of-subsequence-with-non-adjacent-elements/solutions/2790603/fen-zhi-si-xiang-xian-duan-shu-pythonjav-xnhz/
    public int maximumSumSubsequence(int[] nums, int[][] queries) {
        int n = nums.length;
        // 4 個數分別保存 f00, f01, f10, f11
        long[][] t = new long[2 << (32 - Integer.numberOfLeadingZeros(n))][4];
        build(t, nums, 1, 0, n - 1);
        long ans = 0;
        for (int[] q : queries) {
            update(t, 1, 0, n - 1, q[0], q[1]);
            ans += t[1][3]; // 注意 f11 沒有任何限制，也就是整個數組的打家劫舍
        }
        return (int) (ans % 1_000_000_007);
    }

    private void maintain(long[][] t, int o) {
        long[] a = t[o * 2], b = t[o * 2 + 1];
        t[o] = new long[]{
                Math.max(a[0] + b[2], a[1] + b[0]),
                Math.max(a[0] + b[3], a[1] + b[1]),
                Math.max(a[2] + b[2], a[3] + b[0]),
                Math.max(a[2] + b[3], a[3] + b[1])
        };
    }

    // 用 nums 初始化線段樹
    private void build(long[][] t, int[] nums, int o, int l, int r) {
        if (l == r) {
            t[o][3] = Math.max(nums[l], 0);
            return;
        }
        int m = (l + r) / 2;
        build(t, nums, o * 2, l, m);
        build(t, nums, o * 2 + 1, m + 1, r);
        maintain(t, o);
    }

    // 把 nums[i] 改成 val
    private void update(long[][] t, int o, int l, int r, int i, int val) {
        if (l == r) {
            t[o][3] = Math.max(val, 0);
            return;
        }
        int m = (l + r) / 2;
        if (i <= m) {
            update(t, o * 2, l, m, i, val);
        } else {
            update(t, o * 2 + 1, m + 1, r, i, val);
        }
        maintain(t, o);
    }
}


