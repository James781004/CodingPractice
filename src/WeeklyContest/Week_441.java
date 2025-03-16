package WeeklyContest;

import java.util.*;

public class Week_441 {

    // https://leetcode.cn/problems/maximum-unique-subarray-sum-after-deletion/solutions/3614012/on-zuo-fa-suo-you-fei-fu-shu-qu-zhong-ho-4e89/
    public int maxSum(int[] nums) {
        int s = 0;
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            if (x >= 0 && set.add(x)) { // x 不在 set 中
                s += x;
            }
            // 注：這裡順帶求負數最大值，則可以做到一次遍歷
        }
        return set.isEmpty() ? Arrays.stream(nums).max().getAsInt() : s;
    }


    // https://leetcode.cn/problems/closest-equal-element-queries/solutions/3613906/er-fen-cha-zhao-jian-ji-xie-fa-pythonjav-516v/
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        Map<Integer, List<Integer>> indices = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            indices.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        int n = nums.length;
        for (List<Integer> p : indices.values()) {
            // 前後各加一個哨兵
            int i0 = p.get(0);
            p.add(0, p.get(p.size() - 1) - n);
            p.add(i0 + n);
        }

        List<Integer> ans = new ArrayList<>(queries.length); // 預分配空間
        for (int i : queries) {
            List<Integer> p = indices.get(nums[i]);
            if (p.size() == 3) {
                ans.add(-1);
            } else {
                int j = Collections.binarySearch(p, i);
                ans.add(Math.min(i - p.get(j - 1), p.get(j + 1) - i));
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/zero-array-transformation-iv/solutions/3613907/0-1-bei-bao-pythonjavacgo-by-endlesschen-2y0l/
    public int minZeroArray(int[] nums, int[][] queries) {
        if (Arrays.stream(nums).allMatch(x -> x == 0)) {
            return 0; // nums 全為 0
        }

        int n = nums.length;
        boolean[][] f = new boolean[n][];
        for (int i = 0; i < n; i++) {
            f[i] = new boolean[nums[i] + 1];
            f[i][0] = true;
        }

        for (int k = 0; k < queries.length; k++) {
            int l = queries[k][0], r = queries[k][1], val = queries[k][2];
            for (int i = l; i <= r; i++) {
                for (int j = nums[i]; j >= val; j--) {
                    f[i][j] = f[i][j] || f[i][j - val];
                }
            }
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if (!f[i][nums[i]]) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                return k + 1;
            }
        }
        return -1;
    }


    // https://leetcode.cn/problems/count-beautiful-numbers/solutions/3613931/mo-ban-shu-wei-dp-v21pythonjavacgo-by-en-fdzz/
    public int beautifulNumbers(int l, int r) {
        char[] low = String.valueOf(l).toCharArray(); // 無需補前導零
        char[] high = String.valueOf(r).toCharArray();
        Map<Long, Integer> memo = new HashMap<>();
        return dfs(0, 1, 0, true, true, low, high, memo);
    }

    private int dfs(int i, int m, int s, boolean limitLow, boolean limitHigh,
                    char[] low, char[] high, Map<Long, Integer> memo) {
        if (i == high.length) {
            return s > 0 && m % s == 0 ? 1 : 0;
        }
        long mask = (long) m << 32 | i << 16 | s; // 三個 int 壓縮成一個 long
        if (!limitLow && !limitHigh && memo.containsKey(mask)) {
            return memo.get(mask);
        }

        int diffLh = high.length - low.length;
        int lo = limitLow && i >= diffLh ? low[i - diffLh] - '0' : 0;
        int hi = limitHigh ? high[i] - '0' : 9;

        int res = 0;
        int d = lo;
        if (limitLow && i < diffLh) { // 利用 limitLow 和 i 可以判斷出 isNum 是否為 true，所以 isNum 可以省略
            res = dfs(i + 1, 1, 0, true, false, low, high, memo); // 什麼也不填
            d = 1; // 下面循環從 1 開始
        }
        for (; d <= hi; d++) {
            res += dfs(i + 1, m * d, s + d, limitLow && d == lo, limitHigh && d == hi, low, high, memo);
        }

        if (!limitLow && !limitHigh) {
            memo.put(mask, res);
        }
        return res;
    }


}









