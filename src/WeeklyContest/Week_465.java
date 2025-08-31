package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_465 {

    // https://leetcode.cn/problems/restore-finishing-order/solutions/3768238/xian-xing-zuo-fa-pythonjavacgo-by-endles-8lhj/
    public int[] recoverOrder(int[] order, int[] friends) {
        int n = order.length;
        boolean[] isFriend = new boolean[n + 1];
        for (int x : friends) {
            isFriend[x] = true;
        }

        int[] ans = new int[friends.length];
        int idx = 0;
        for (int x : order) {
            if (isFriend[x]) {
                ans[idx++] = x;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/balanced-k-factor-decomposition/solutions/3768233/bao-sou-pythonjavacgo-by-endlesscheng-x7jt/
    private static final int MX = 100_001;
    private static final List<Integer>[] divisors = new ArrayList[MX];
    private static boolean initialized = false;

    // 這樣寫比 static block 更快
    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        // 預處理每個數的因子
        Arrays.setAll(divisors, v -> new ArrayList<>());
        for (int i = 1; i < MX; i++) {
            for (int j = i; j < MX; j += i) {
                divisors[j].add(i);
            }
        }
    }

    public int[] minDifference(int n, int k) {
        init();
        int[] path = new int[k];
        dfs(k - 1, n, Integer.MAX_VALUE, 0, path);
        return ans;
    }

    private int minDiff = Integer.MAX_VALUE;
    private int[] ans;

    private void dfs(int i, int n, int mn, int mx, int[] path) {
        if (i == 0) {
            int d = Math.max(mx, n) - Math.min(mn, n); // 最後一個數是 n
            if (d < minDiff) {
                minDiff = d;
                path[i] = n;
                ans = path.clone();
            }
            return;
        }
        for (int d : divisors[n]) { // 枚舉 x 的因子
            path[i] = d; // 直接覆蓋，無需恢復現場
            dfs(i - 1, n / d, Math.min(mn, d), Math.max(mx, d), path);
        }
    }


    // https://leetcode.cn/problems/maximum-product-of-two-integers-with-no-common-bits/solutions/3768219/mo-ban-gao-wei-qian-zhui-he-sos-dppython-78fz/
    public long maxProduct(int[] nums) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int w = 32 - Integer.numberOfLeadingZeros(mx);
        int u = 1 << w;
        int[] f = new int[u];
        for (int x : nums) {
            f[x] = x;
        }

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < u; j++) {
                j |= 1 << i; // 快速跳到第 i 位是 1 的 j
                f[j] = Math.max(f[j], f[j ^ (1 << i)]);
            }
        }

        long ans = 0;
        for (int x : nums) {
            ans = Math.max(ans, (long) x * f[(u - 1) ^ x]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-beautiful-subsequences/solutions/3768197/bei-shu-rong-chi-zhi-yu-shu-zhuang-shu-z-rs5w/
    private static final int MOD = 1_000_000_007;
    private static final int MX2 = 70_001;
    private static final List<Integer>[] divisors2 = new ArrayList[MX2];
    private static boolean initialized2 = false;

    // 這樣寫比 static block 更快
    private void initDivisors() {
        if (initialized2) {
            return;
        }
        initialized = true;

        // 預處理每個數的因子
        Arrays.setAll(divisors2, v -> new ArrayList<>());
        for (int i = 1; i < MX; i++) {
            for (int j = i; j < MX; j += i) {
                divisors2[j].add(i);
            }
        }
    }

    public int totalBeauty(int[] nums) {
        initDivisors();
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }

        List<Integer>[] groups = new ArrayList[m + 1];
        Arrays.setAll(groups, v -> new ArrayList<>());
        for (int x : nums) {
            for (int d : divisors[x]) {
                groups[d].add(x);
            }
        }

        FenwickTree t = new FenwickTree(m);
        int[] f = new int[m + 1];
        long ans = 0;
        for (int i = m; i > 0; i--) {
            long res = cntIS(groups[i], t);
            // 倍數容斥
            for (int j = i * 2; j <= m; j += i) {
                res -= f[j];
            }
            res %= MOD;
            f[i] = (int) res;
            // 注意 |f[i]| * i < MOD * (m / i) * i = MOD * m
            // m 個 MOD * m 相加，至多為 MOD * m * m，不會超過 64 位整數最大值
            ans += res * i;
        }
        // 保證結果非負
        return (int) ((ans % MOD + MOD) % MOD);
    }

    // 計算 b 的嚴格遞增子序列的個數
    private long cntIS(List<Integer> b, FenwickTree t) {
        t.incrementNow();
        long res = 0;
        for (int x : b) {
            // cnt 表示以 x 結尾的嚴格遞增子序列的個數
            int cnt = t.pre(x - 1) + 1; // +1 是因為 x 可以一個數組成一個子序列
            res += cnt;
            t.update(x, cnt); // 更新以 x 結尾的嚴格遞增子序列的個數
        }
        return res;
    }


    class FenwickTree {
        private static final int MOD = 1_000_000_007;
        private final int[] tree;
        private final int[] time;
        private int now = 0;

        public FenwickTree(int size) {
            tree = new int[size + 1];
            time = new int[size + 1];
        }

        public void incrementNow() {
            now++;
        }

        public void update(int i, int val) {
            while (i < tree.length) {
                if (time[i] < now) {
                    time[i] = now;
                    tree[i] = 0; // 懶初始化
                }
                tree[i] = (tree[i] + val) % MOD;
                i += i & -i;
            }
        }

        public int pre(int i) {
            long res = 0;
            while (i > 0) {
                if (time[i] == now) {
                    res += tree[i];
                }
                i &= i - 1;
            }
            return (int) (res % MOD);
        }
    }


}









