package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_459 {

    // https://leetcode.cn/problems/check-divisibility-by-digit-sum-and-product/solutions/3728558/bian-li-shu-wei-pythonjavacgo-by-endless-pbmh/
    public boolean checkDivisibility(int n) {
        int s = 0, m = 1;
        for (int x = n; x > 0; x /= 10) {
            int d = x % 10;
            s += d;
            m *= d;
        }
        return n % (s + m) == 0;
    }


    // https://leetcode.cn/problems/count-number-of-trapezoids-i/solutions/3728518/mei-ju-you-wei-hu-zuo-pythonjavacgo-by-e-lwnv/
    private static final int MOD = 1_000_000_007;

    public int countTrapezoids(int[][] points) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int[] p : points) {
            cnt.merge(p[1], 1, Integer::sum);  // 枚舉y，統計x
        }

        long ans = 0, s = 0;
        for (int c : cnt.values()) { // 枚舉 y 上的所有 x
            // 水平線中取任意兩點作為梯形水平邊組合數
            long k = (long) c * (c - 1) / 2 % MOD;

            // 設這一行有 k 條水平邊，那麼另外一條邊就是之前遍歷過的行的邊數 s
            // 根據乘法原理，之前遍歷過的行與這一行，一共可以組成 s⋅k 個水平梯形，加入答案。
            ans = (ans + s * k) % MOD;

            // s 表示遍歷過的行的邊數
            s = (s + k) % MOD;
        }
        return (int) ans;
    }


    // https://leetcode.cn/problems/number-of-integers-with-popcount-depth-equal-to-k-ii/solutions/3728547/6-ge-shu-zhuang-shu-zu-pythonjavacgo-by-klqxt/
    public int[] popcountDepth(long[] nums, long[][] queries) {
        int n = nums.length;
        FenwickTree[] f = new FenwickTree[6];
        Arrays.setAll(f, v -> new FenwickTree(n + 1));

        for (int i = 0; i < n; i++) {
            update(i, nums[i], 1, f);
        }

        int ansSize = 0;
        for (long[] q : queries) {
            ansSize += q[0] == 1 ? 1 : 0;
        }

        int[] ans = new int[ansSize];
        int idx = 0;
        for (long[] q : queries) {
            if (q[0] == 1) {
                ans[idx++] = f[(int) q[3]].query((int) q[1] + 1, (int) q[2] + 1);
            } else {
                int i = (int) q[1];
                update(i, nums[i], -1, f); // 撤銷舊的
                nums[i] = q[2];
                update(i, nums[i], 1, f); // 添加新的
            }
        }
        return ans;
    }

    private int popDepth(long x) {
        if (x == 1) {
            return 0;
        }
        return popDepth(Long.bitCount(x)) + 1;
    }

    private void update(int i, long x, int delta, FenwickTree[] f) {
        int d = popDepth(x);
        if (d <= 5) {
            f[d].update(i + 1, delta);
        }
    }


    class FenwickTree {
        private final int[] tree; // 如果計算結果沒有超出 int 范圍，可以改成 int

        public FenwickTree(int n) {
            tree = new int[n + 1]; // 使用下標 1 到 n
        }

        // a[i] 增加 val
        // 1 <= i <= n
        // 時間復雜度 O(log n)
        public void update(int i, int val) {
            for (; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        // 求前綴和 a[1] + ... + a[i]
        // 1 <= i <= n
        // 時間復雜度 O(log n)
        public int pre(int i) {
            int res = 0;
            for (; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }

        // 求區間和 a[l] + ... + a[r]
        // 1 <= l <= r <= n
        // 時間復雜度 O(log n)
        public int query(int l, int r) {
            if (r < l) {
                return 0;
            }
            return pre(r) - pre(l - 1);
        }
    }


    // https://leetcode.cn/problems/count-number-of-trapezoids-ii/solutions/3728529/tong-ji-zhi-xian-qu-diao-zhong-fu-tong-j-a3f9/
    public int countTrapezoidsII(int[][] points) {
        Map<Double, Map<Double, Integer>> cnt = new HashMap<>(); // 斜率 -> 截距 -> 個數
        Map<Integer, Map<Double, Integer>> cnt2 = new HashMap<>(); // 中點 -> 斜率 -> 個數

        int n = points.length;
        for (int i = 0; i < n; i++) {
            int x = points[i][0], y = points[i][1];
            for (int j = 0; j < i; j++) {
                int x2 = points[j][0], y2 = points[j][1];
                int dy = y - y2;
                int dx = x - x2;
                double k = dx != 0 ? 1.0 * dy / dx : Double.MAX_VALUE;
                double b = dx != 0 ? 1.0 * (y * dx - dy * x) / dx : x;

                // 歸一化 -0.0 為 0.0
                if (k == -0.0) {
                    k = 0.0;
                }
                if (b == -0.0) {
                    b = 0.0;
                }

                // cnt[k][b]++
                cnt.computeIfAbsent(k, v -> new HashMap<>()).merge(b, 1, Integer::sum);

                int mask = (x + x2 + 2000) << 16 | (y + y2 + 2000); // 把 pair 壓縮成一個 int
                // cnt2[mask][k]++
                cnt2.computeIfAbsent(mask, v -> new HashMap<>()).merge(k, 1, Integer::sum);
            }
        }

        int ans = 0;
        for (Map<Double, Integer> m : cnt.values()) {
            int s = 0;
            for (int c : m.values()) {
                ans += s * c;
                s += c;
            }
        }

        for (Map<Double, Integer> m : cnt2.values()) {
            int s = 0;
            for (int c : m.values()) {
                ans -= s * c;
                s += c;
            }
        }

        return ans;
    }


}









