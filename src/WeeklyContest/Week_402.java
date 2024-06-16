package WeeklyContest;

import java.util.*;

public class Week_402 {
    // https://leetcode.cn/problems/count-pairs-that-form-a-complete-day-ii/solutions/2812385/tao-lu-mei-ju-you-wei-hu-zuo-pythonjavac-3vhv/
    public long countCompleteDayPairs(int[] hours) {
        long ans = 0;
        int[] cnt = new int[24];
        for (int t : hours) {
            // 先查詢 cnt，再更新 cnt，因為題目要求 i<j
            // 如果先更新，再查詢，就把 i=j 的情況也考慮進去了
            ans += cnt[(24 - t % 24) % 24];
            cnt[t % 24]++;
        }
        return ans;
    }

    // https://leetcode.cn/problems/maximum-total-damage-with-spell-casting/solutions/2812389/tao-lu-da-jia-jie-she-pythonjavacgo-by-e-p9b5/
    public long maximumTotalDamage(int[] power) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : power) {
            cnt.merge(x, 1, Integer::sum);
        }

        int n = cnt.size();
        int[] a = new int[n];
        int k = 0;
        for (int x : cnt.keySet()) {
            a[k++] = x;
        }
        Arrays.sort(a);

        long[] memo = new long[n];
        Arrays.fill(memo, -1);
        return dfs(a, cnt, memo, n - 1);
    }

    private long dfs(int[] a, Map<Integer, Integer> cnt, long[] memo, int i) {
        if (i < 0) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int x = a[i];
        int j = i;
        while (j > 0 && a[j - 1] >= x - 2) {
            j--;
        }
        return memo[i] = Math.max(dfs(a, cnt, memo, i - 1), dfs(a, cnt, memo, j - 1) + (long) x * cnt.get(x));
    }

    // https://leetcode.cn/problems/peaks-in-array/solutions/2812394/shu-zhuang-shu-zu-pythonjavacgo-by-endle-tj0w/
    public List<Integer> countOfPeaks(int[] nums, int[][] queries) {
        int n = nums.length;
        Fenwick f = new Fenwick(n - 1);
        for (int i = 1; i < n - 1; i++) {
            update(f, nums, i, 1);
        }

        List<Integer> ans = new ArrayList<>();
        for (int[] q : queries) {
            if (q[0] == 1) {
                ans.add(f.query(q[1] + 1, q[2] - 1));
                continue;
            }
            int i = q[1];
            for (int j = Math.max(i - 1, 1); j <= Math.min(i + 1, n - 2); j++) {
                update(f, nums, j, -1);
            }
            nums[i] = q[2];
            for (int j = Math.max(i - 1, 1); j <= Math.min(i + 1, n - 2); j++) {
                update(f, nums, j, 1);
            }
        }
        return ans;
    }

    private void update(Fenwick f, int[] nums, int i, int val) {
        if (nums[i - 1] < nums[i] && nums[i] > nums[i + 1]) {
            f.update(i, val);
        }
    }

    class Fenwick {
        private final int[] f;

        Fenwick(int n) {
            f = new int[n];
        }

        void update(int i, int val) {
            for (; i < f.length; i += i & -i) {
                f[i] += val;
            }
        }

        private int pre(int i) {
            int res = 0;
            for (; i > 0; i &= i - 1) {
                res += f[i];
            }
            return res;
        }

        int query(int l, int r) {
            if (r < l) {
                return 0;
            }
            return pre(r) - pre(l - 1);
        }
    }
}


