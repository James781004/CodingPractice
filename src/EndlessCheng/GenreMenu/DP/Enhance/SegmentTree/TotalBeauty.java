package EndlessCheng.GenreMenu.DP.Enhance.SegmentTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TotalBeauty {

    // https://leetcode.cn/problems/sum-of-beautiful-subsequences/solutions/3768197/bei-shu-rong-chi-zhi-yu-shu-zhuang-shu-z-rs5w/
    private static final int MOD = 1_000_000_007;

    public int totalBeauty(int[] nums) {
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }

        List<Integer>[] groups = new ArrayList[m + 1];
        Arrays.setAll(groups, v -> new ArrayList<>());
        for (int x : nums) {
            for (int d = 1; d * d <= x; d++) { // 枚舉 x 的因子 d
                if (x % d == 0) {
                    groups[d].add(x);
                    if (d * d < x) {
                        groups[x / d].add(x);
                    }
                }
            }
        }

        int[] f = new int[m + 1];
        long ans = 0;
        for (int i = m; i > 0; i--) {
            long res = countIncreasingSubsequence(groups[i], i, m);
            // 倍數容斥
            for (int j = i * 2; j <= m; j += i) {
                res -= f[j];
            }
            res %= MOD;
            f[i] = (int) res;
            // m 個 MOD * m 相加，至多為 MOD * m * m，不會超過 64 位整數最大值
            ans += res * i;
        }
        // 保證結果非負
        return (int) ((ans % MOD + MOD) % MOD);
    }

    // 計算 b 的嚴格遞增子序列的個數
    private long countIncreasingSubsequence(List<Integer> b, int g, int m) {
        FenwickTree t = new FenwickTree(m / g);
        long res = 0;
        for (int x : b) {
            x /= g;
            // cnt 表示以 x 結尾的嚴格遞增子序列的個數
            long cnt = t.pre(x - 1) + 1; // +1 是因為 x 可以一個數組成一個子序列
            cnt %= MOD;
            res += cnt;
            t.update(x, cnt); // 更新以 x 結尾的嚴格遞增子序列的個數
        }
        return res;
    }

    // 完整模板見 https://leetcode.cn/circle/discuss/mOr1u6/
    static class FenwickTree {
        private final long[] tree;

        public FenwickTree(int n) {
            tree = new long[n + 1]; // 使用下標 1 到 n
        }

        // a[i] 增加 val
        // 1 <= i <= n
        // 時間復雜度 O(log n)
        public void update(int i, long val) {
            for (; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        // 求前綴和 a[1] + ... + a[i]
        // 1 <= i <= n
        // 時間復雜度 O(log n)
        public long pre(int i) {
            long res = 0;
            for (; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }
    }


}
