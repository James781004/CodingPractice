package WeeklyContest;

import java.util.*;

public class Week_505 {

    // https://leetcode.cn/problems/sum-of-compatible-numbers-in-range-i/solutions/3980541/liang-chong-fang-fa-bao-li-mei-ju-shu-we-one9/
    public int sumOfGoodIntegers(int n, int k) {
        int low = Math.max(n - k, 1);
        int high = n + k;
        int m = 32 - Integer.numberOfLeadingZeros(high);
        int[][] memo = new int[m][];
        return dfs(m - 1, true, true, low, high, n, memo)[1];
    }

    // dfs 返回兩個數：子樹合法二進制數個數，子樹和
    private int[] dfs(int i, boolean limitLow, boolean limitHigh, int low, int high, int n, int[][] memo) {
        if (i < 0) {
            return new int[]{1, 0}; // 如果沒有特殊約束，那麼能遞歸到終點的都是合法二進制數
        }

        if (!limitLow && !limitHigh && memo[i] != null) {
            return memo[i];
        }

        int lo = limitLow ? low >> i & 1 : 0;
        int hi = limitHigh ? high >> i & 1 : 1;

        int cnt = 0;
        int sum = 0;

        for (int d = lo; d <= hi; d++) {
            int bit = d << i;
            if ((n & bit) > 0) { // 不滿足要求
                continue;
            }
            int[] sub = dfs(i - 1, limitLow && d == lo, limitHigh && d == hi, low, high, n, memo);
            cnt += sub[0]; // 累加子樹的合法二進制數個數
            sum += sub[1]; // 累加子樹和
            sum += bit * sub[0]; // bit 會出現在 sub[0] 個數中（貢獻法）
        }

        int[] res = new int[]{cnt, sum};
        if (!limitLow && !limitHigh) {
            memo[i] = res;
        }
        return res;
    }


    // https://leetcode.cn/problems/valid-binary-strings-with-cost-limit/solutions/3980522/yu-chu-li-mei-ge-shu-de-cheng-ben-python-u4et/
    class Solution {
        private static final int[] cost = new int[1 << 12];
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            for (int x = 1; x < cost.length; x++) {
                if ((x & (x >> 1)) > 0) { // 有兩個連續的 1
                    cost[x] = Integer.MAX_VALUE; // 不滿足要求
                } else {
                    // 去掉 x 中的一個比特位（最低位還是最高位都可以），計算 DP
                    cost[x] = cost[x & (x - 1)] + Integer.numberOfTrailingZeros(x);
                }
            }
        }

        public List<String> generateValidStrings(int n, int k) {
            List<String> ans = new ArrayList<>();
            char[] s = new char[n];
            for (int x = 0; x < (1 << n); x++) {
                if (cost[x] > k) {
                    continue;
                }
                int y = x;
                for (int j = 0; j < n; j++) { // 注意左邊是低位，右邊是高位
                    s[j] = (char) ('0' + (y & 1));
                    y >>= 1;
                }
                ans.add(new String(s));
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/maximum-sum-of-m-non-overlapping-subarrays-i/solutions/3980513/dan-diao-dui-lie-you-hua-dppythonjavacgo-lpex/
    public long maximumSum(int[] nums, int m, int left, int right) {
        int n = nums.length;
        long[] s = new long[n + 1]; // nums 的前綴和
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
        }

        // f[i][j] 表示在前 j 個數（下標 0 到 j-1）中選出恰好 i 個子數組，所選元素之和的最大值
        long[][] f = new long[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            Arrays.fill(f[i], Long.MIN_VALUE / 2); // 防止溢出
        }
        long ans = Long.MIN_VALUE;

        for (int i = 1; i <= m; i++) {
            Deque<Integer> q = new ArrayDeque<>();

            // 前 i 個子數組至少佔用了 i * left 個位置
            for (int j = i * left; j <= n; j++) {
                // 1. 入
                int k = j - left;
                long v = f[i - 1][k] - s[k];
                while (!q.isEmpty() && f[i - 1][q.peekLast()] - s[q.peekLast()] <= v) {
                    q.pollLast();
                }
                q.offerLast(k);

                // 2. 更新
                // 不選 nums[j-1] vs 選一個以 j-1 結尾的子數組
                f[i][j] = Math.max(f[i][j - 1], f[i - 1][q.peekFirst()] - s[q.peekFirst()] + s[j]);

                // 3. 出，下一輪循環隊首離開窗口
                if (q.peekFirst() <= j - right) {
                    q.pollFirst();
                }
            }

            // 枚舉恰好選 i 個子數組
            ans = Math.max(ans, f[i][n]);
        }

        return ans;
    }


    // https://leetcode.cn/problems/maximum-sum-of-m-non-overlapping-subarrays-ii/solutions/3980778/la-ge-lang-ri-song-chi-wqs-er-fen-python-m2iw/
    // DP 值, 子數組個數
    private record Pair(long f, int cnt) {
    }

    // 相等的時候，子數組個數更大的劣
    private boolean less(Pair a, Pair b) {
        return a.f < b.f || a.f == b.f && a.cnt > b.cnt;
    }

    public long maximumSumII(int[] nums, int m, int l, int r) {
        int n = nums.length;
        long[] s = new long[n + 1]; // nums 的前綴和
        long posSum = 0; // nums 中的正數之和
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
            if (nums[i] > 0) {
                posSum += nums[i];
            }
        }

        Pair res0 = dpWithoutLimit(0, n, l, r, s);
        if (res0.cnt <= m) { // 直接滿足題目要求
            return res0.f;
        }

        // 現在專注於解決「選恰好 m 個子數組」的問題
        long ans = 0;
        long left = 0;
        long right = posSum + 1;
        while (left + 1 < right) {
            long k = left + (right - left) / 2;
            Pair res = dpWithoutLimit(k, n, l, r, s);
            if (res.cnt <= m) {
                ans = res.f + m * k; // 不需要取 max，二分最終會縮小到凸函數中的 x=m 所在的那條線段
                right = k;
            } else {
                left = k;
            }
        }
        return ans;
    }

    // 沒有 m 約束，但每選一個子數組就要把元素和減少 k
    private Pair dpWithoutLimit(long k, int n, int l, int r, long[] s) {
        Pair[] f = new Pair[n + 1];
        Arrays.fill(f, 0, l, new Pair(0, 0));
        Deque<Integer> q = new ArrayDeque<>();
        Pair res = new Pair(Long.MIN_VALUE, 0);

        for (int i = l; i <= n; i++) {
            // 1. 入
            int j = i - l;
            Pair v = new Pair(f[j].f - s[j], f[j].cnt);
            while (!q.isEmpty() && less(new Pair(f[q.peekLast()].f - s[q.peekLast()], f[q.peekLast()].cnt), v)) {
                q.pollLast();
            }
            q.addLast(j);

            // 2. 更新答案
            j = q.peekFirst();
            Pair choose = new Pair(f[j].f - s[j] + s[i] - k, f[j].cnt + 1);
            if (less(res, choose)) {
                // choose 保證我們至少選了一個子數組
                res = choose;
            }

            // 更新 DP
            f[i] = less(f[i - 1], choose) ? choose : f[i - 1];

            // 3. 出，下一輪循環隊首離開窗口
            if (j <= i - r) {
                q.pollFirst();
            }
        }

        return res;
    }

}










