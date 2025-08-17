package WeeklyContest;

import java.util.Arrays;

public class Week_463 {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-using-strategy/solutions/3755315/bao-li-zuo-fa-java-by-strange-archimedes-q8f7/
    public long maxProfit(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        long[] pre = new long[n + 1];
        long[] suf = new long[n + 1];
        long ans = 0;
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + (long) (prices[i] * strategy[i]);
            ans += (long) (prices[i] * strategy[i]);
        }

        for (int i = n - 1; i >= 0; i--) {
            suf[i] = suf[i + 1] + (long) (prices[i] * strategy[i]);
        }

        for (int i = 0; i + k - 1 < n; i++) {
            long tmp = pre[i] + suf[i + k];
            int l = i + k / 2;
            int r = l + k / 2 - 1;
            for (int j = l; j <= r; j++) {
                tmp = tmp + prices[j];
            }

            ans = Math.max(ans, tmp);
        }
        return ans;
    }


    // https://leetcode.cn/problems/xor-after-range-multiplication-queries-i/solutions/3755316/mo-ni-by-strange-archimedeshtk-uaot/
    public int xorAfterQueries(int[] nums, int[][] queries) {
        long mod = (long) 1e9 + 7;
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0], r = queries[i][1];
            int k = queries[i][2], v = queries[i][3];
            int idx = l;
            while (idx <= r) {
                long tmp = nums[idx];
                tmp = (tmp * v) % mod;
                nums[idx] = (int) (tmp % mod);
                idx += k;
            }
        }
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            ans ^= nums[i];
        }
        return (int) ans;
    }


    // https://leetcode.cn/problems/minimum-sum-after-divisible-sum-deletions/solutions/3755268/dong-tai-gui-hua-qian-zhui-he-pythonjava-nia8/
    public long minArraySum(int[] nums, int k) {
        long[] minF = new long[k];
        Arrays.fill(minF, Long.MAX_VALUE);
        minF[0] = 0; // sum[0] = 0，對應的 f[0] = 0
        long f = 0;
        int sum = 0;
        for (int x : nums) {
            sum = (sum + x) % k;
            // 不刪除 x，那麼轉移來源為 f + x
            // 刪除 x，問題變成剩余前綴的最小和
            // 其中剩余前綴的元素和模 k 等於 sum，對應的 f 值的最小值記錄在 minF[sum] 中
            f = Math.min(f + x, minF[sum]);
            // 維護前綴和 sum 對應的最小和，由於上面計算了 min，這裡無需再計算 min
            minF[sum] = f;
        }
        return f;
    }


    // https://leetcode.cn/problems/xor-after-range-multiplication-queries-ii/solutions/3755296/gen-hao-suan-fa-pythonjavacgo-by-endless-moxx/
    private static final int MOD = 1_000_000_007;

    public int xorAfterQueries2(int[] nums, int[][] queries) {
        int n = nums.length;
        int B = (int) Math.sqrt(queries.length);
        int[][] diff = new int[B][];

        for (int[] q : queries) {
            int l = q[0], r = q[1], k = q[2];
            long v = q[3];
            if (k < B) {
                // 懶初始化
                if (diff[k] == null) {
                    diff[k] = new int[n + k];
                    Arrays.fill(diff[k], 1);
                }
                diff[k][l] = (int) (diff[k][l] * v % MOD);
                r = r - (r - l) % k + k;
                diff[k][r] = (int) (diff[k][r] * pow(v, MOD - 2) % MOD);
            } else {
                for (int i = l; i <= r; i += k) {
                    nums[i] = (int) (nums[i] * v % MOD);
                }
            }
        }

        for (int k = 0; k < B; k++) {
            int[] d = diff[k];
            if (d == null) {
                continue;
            }
            for (int start = 0; start < k; start++) {
                long mulD = 1;
                for (int i = start; i < n; i += k) {
                    mulD = mulD * d[i] % MOD;
                    nums[i] = (int) (nums[i] * mulD % MOD);
                }
            }
        }

        int ans = 0;
        for (int x : nums) {
            ans ^= x;
        }
        return ans;
    }

    private long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }


}









