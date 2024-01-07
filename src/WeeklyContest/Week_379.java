package WeeklyContest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Week_379 {
    // https://leetcode.cn/problems/maximum-area-of-longest-diagonal-rectangle/solutions/2594444/jian-ji-xie-fa-on-yi-ci-bian-li-pythonja-exzg/
    public int areaOfMaxDiagonal(int[][] dimensions) {
        int ans = 0, maxL = 0;
        for (int[] d : dimensions) {
            int x = d[0], y = d[1];
            int l = x * x + y * y;
            if (l > maxL || (l == maxL && x * y > ans)) {
                maxL = l;
                ans = x * y;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-moves-to-capture-the-queen/solutions/2594432/fen-lei-tao-lun-jian-ji-xie-fa-pythonjav-aoa8/
    public int minMovesToCaptureTheQueen(int a, int b, int c, int d, int e, int f) {
        if (a == e && (c != e || ok(b, d, f)) ||
                b == f && (d != f || ok(a, c, e)) ||
                c + d == e + f && (a + b != e + f || ok(c, a, e)) ||
                c - d == e - f && (a - b != e - f || ok(c, a, e))) {
            return 1;
        }
        return 2;
    }

    private boolean ok(int l, int m, int r) {
        return m < Math.min(l, r) || m > Math.max(l, r);
    }


    // https://leetcode.cn/problems/maximum-size-of-a-set-after-removals/solutions/2594380/tan-xin-pythonjavacgo-by-endlesscheng-ymuh/
    public int maximumSetSize(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        for (int x : nums1) {
            set1.add(x);
        }
        Set<Integer> set2 = new HashSet<>();
        for (int x : nums2) {
            set2.add(x);
        }
        int common = 0;
        for (int x : set1) {
            if (set2.contains(x)) {
                common++;
            }
        }

        int n1 = set1.size();
        int n2 = set2.size();
        int ans = n1 + n2 - common;

        int m = nums1.length / 2;
        if (n1 > m) {
            int mn = Math.min(n1 - m, common);
            ans -= n1 - mn - m;
            common -= mn;
        }

        if (n2 > m) {
            n2 -= Math.min(n2 - m, common);
            ans -= n2 - m;
        }

        return ans;
    }


    // https://leetcode.cn/problems/maximize-the-number-of-partitions-after-operations/solutions/2595072/ji-yi-hua-sou-suo-jian-ji-xie-fa-pythonj-6g5z/
    private final Map<Long, Integer> memo = new HashMap<>();

    public int maxPartitionsAfterOperations(String s, int k) {
        return dfs(0, 0, 0, s.toCharArray(), k);
    }

    private int dfs(int i, int mask, int changed, char[] s, int k) {
        if (i == s.length) {
            return 1;
        }

        long argsMask = (long) i << 32 | mask << 1 | changed;
        if (memo.containsKey(argsMask)) { // 之前計算過
            return memo.get(argsMask);
        }

        int res;
        // 不改 s[i]
        int bit = 1 << (s[i] - 'a');
        int newMask = mask | bit;
        if (Integer.bitCount(newMask) > k) {
            // 分割出一個子串，這個子串的最後一個字母在 i-1
            // s[i] 作為下一段的第一個字母，也就是 bit 作為下一段的 mask 的初始值
            res = dfs(i + 1, bit, changed, s, k) + 1;
        } else { // 不分割
            res = dfs(i + 1, newMask, changed, s, k);
        }

        if (changed == 0) {
            // 枚舉把 s[i] 改成 a,b,c,...,z
            for (int j = 0; j < 26; j++) {
                newMask = mask | (1 << j);
                if (Integer.bitCount(newMask) > k) {
                    // 分割出一個子串，這個子串的最後一個字母在 i-1
                    // j 作為下一段的第一個字母，也就是 1<<j 作為下一段的 mask 的初始值
                    res = Math.max(res, dfs(i + 1, 1 << j, 1, s, k) + 1);
                } else { // 不分割
                    res = Math.max(res, dfs(i + 1, newMask, 1, s, k));
                }
            }
        }

        memo.put(argsMask, res); // 記憶化
        return res;
    }
}
