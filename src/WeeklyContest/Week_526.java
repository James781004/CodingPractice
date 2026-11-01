package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_526 {

    // https://leetcode.cn/problems/minimum-value-of-merged-integers/solutions/4051731/mo-ni-zi-fu-chuan-pin-jie-qing-song-qiu-7plop/
    public int minMergedInteger(int[] nums) {
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i += 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(nums[i]);
            sb.append(nums[i + 1]);
            ans = Math.min(ans, Integer.parseInt(sb.toString()));
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimize-the-maximum-value-of-gcd-connected-array/solutions/4051701/tan-xin-gou-zao-pythonjavacgo-by-endless-pk9j/
    public long minimumMaxValue(int n, int index, int value) {
        int minP = minPrimeDivisor(value);
        if (value == 1 || value - minP - (index - 1) * 2 <= 0) { // nums[0] <= 0
            return -1;
        }
        if (index == n - 1) {
            return value;
        }
        return (long) value + minP + (n - 2 - index) * 2;
    }

    // 返回 value 的最小質因子
    private int minPrimeDivisor(int value) {
        for (int p = 2; p * p <= value; p++) {
            if (value % p == 0) {
                return p;
            }
        }
        return value; // value 是質數或者 1
    }


    // https://leetcode.cn/problems/minimum-operations-to-sort-with-locked-indices/
    public int minOperations(int[] nums, int[] locked, int k) {
        int n = nums.length;
        FenwickTree t = new FenwickTree(n);
        int ans = 0;
        for (int i = 0; i < n; ) {
            if (nums[i] == i + 1) { // nums[i] 已在正確位置上
                i++;
                continue;
            }
            int start = i;
            int mn = n + 1, mx = 0;
            // 這一段的最小值必須是 start+1，且值域大小必須等於段長度
            for (; i < n && (mn != start + 1 || mx - mn + 1 != i - start); i++) {
                if (locked[i] > 0) {
                    if (k == 0) { // 無法解鎖
                        return -1;
                    }
                    k--;
                    ans++;
                }
                int x = nums[i];
                ans += t.query(x, mx); // x 與其左側元素的逆序對個數
                t.update(x, 1);
                mn = Math.min(mn, x);
                mx = Math.max(mx, x);
            }
        }
        return ans;
    }


    class FenwickTree {
        private final int[] tree;

        public FenwickTree(int n) {
            tree = new int[n + 1]; // 使用下标 1 到 n
        }

        // a[i] 增加 val
        // 1 <= i <= n
        // 时间复杂度 O(log n)
        public void update(int i, int val) {
            for (; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        // 求前缀和 a[1] + ... + a[i]
        // 1 <= i <= n
        // 时间复杂度 O(log n)
        public int pre(int i) {
            int res = 0;
            for (; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }

        // 求区间和 a[l] + ... + a[r]
        // 1 <= l <= r <= n
        // 时间复杂度 O(log n)
        public int query(int l, int r) {
            if (l > r) {
                return 0;
            }
            return pre(r) - pre(l - 1);
        }
    }

    // https://leetcode.cn/problems/minimum-cost-path-with-even-xor-parity/solutions/4051727/wei-yun-suan-heng-deng-shi-pin-jie-lu-ji-i2he/
    private static final long INF = Long.MAX_VALUE / 4;
    private long ans = INF;

    public int minActivationCost(int[][] edges, int[] values) {
        List<int[]>[] g = new ArrayList[values.length];
        Arrays.setAll(g, v -> new ArrayList<>());

        for (int[] e : edges) {
            int x = e[0], y = e[1], c = e[2];
            g[x].add(new int[]{y, c});
            g[y].add(new int[]{x, c});
        }

        dfs(0, -1, 0, g, values);
        return ans < INF ? (int) ans : -1;
    }

    // pOnes 為根到 x 的路徑異或和的二進制中的 1 的個數的奇偶性
    // 返回從 x 到其後代的最小開銷和（按照 pOnes 的奇偶性分別計算）
    private long[] dfs(int x, int fa, int pOnes, List<int[]>[] g, int[] values) {
        int pVal = Integer.bitCount(values[x]) % 2;
        pOnes ^= pVal;
        long[] minSum = {INF, INF};
        minSum[pOnes] = 0; // x 作為路徑的端點

        for (int[] e : g[x]) {
            int y = e[0], c = e[1];
            if (y == fa) {
                continue;
            }
            long[] mn = dfs(y, x, pOnes, g, values);
            mn[0] += c;
            mn[1] += c;
            // 現在 mn 是從 x 到子樹 y 中節點的最小開銷和
            // 拼接從 x 到兩棵子樹中的路徑（拼接後，路徑在 x 拐彎）
            ans = Math.min(ans, Math.min(minSum[0] + mn[pVal], minSum[1] + mn[pVal ^ 1]));
            minSum[0] = Math.min(minSum[0], mn[0]);
            minSum[1] = Math.min(minSum[1], mn[1]);
        }

        return minSum;
    }


}










