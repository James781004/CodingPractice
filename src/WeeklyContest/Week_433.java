package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Week_433 {

    // https://leetcode.cn/problems/sum-of-variable-length-subarrays/solutions/3051553/liang-chong-fang-fa-bao-li-chai-fen-shu-iuwyi/
    // 用差分數組計算每個 nums[i] 要加到答案中多少次。
    // 做法是把區間 [max(i−nums[i],0),i] 加一，再計算差分數組的前綴和。
    public int subarraySum(int[] nums) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        for (int i = 0; i < n; i++) {
            diff[Math.max(i - nums[i], 0)]++;
            diff[i + 1]--;
        }

        int ans = 0, sd = 0;
        for (int i = 0; i < n; i++) {
            sd += diff[i];
            ans += nums[i] * sd;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-and-minimum-sums-of-at-most-size-k-subsequences/solutions/3051549/gong-xian-fa-zu-he-shu-xue-pythonjavacgo-0jod/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 100_000;

    private static final long[] F = new long[MX]; // f[i] = i!
    private static final long[] INV_F = new long[MX]; // inv_f[i] = i!^-1

    static {
        F[0] = 1;
        for (int i = 1; i < MX; i++) {
            F[i] = F[i - 1] * i % MOD;
        }

        INV_F[MX - 1] = pow(F[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; i--) {
            INV_F[i - 1] = INV_F[i] * i % MOD;
        }
    }

    public int minMaxSums(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            long s = 0;
            for (int j = 0; j < Math.min(k, i + 1); j++) {
                s += comb(i, j);
            }
            ans = (ans + s % MOD * (nums[i] + nums[n - 1 - i])) % MOD;
        }
        return (int) ans;
    }

    private long comb(int n, int m) {
        return F[n] * INV_F[m] % MOD * INV_F[n - m] % MOD;
    }

    private static long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }


    // https://leetcode.cn/problems/paint-house-iv/solutions/3051581/duo-wei-dpcong-ji-yi-hua-sou-suo-dao-di-javxs/
    public long minCost(int n, int[][] cost) {
        long[][][] memo = new long[n / 2][4][4];
        for (long[][] mat : memo) {
            for (long[] arr : mat) {
                Arrays.fill(arr, -1); // -1 表示沒有計算過
            }
        }
        return dfs(n / 2 - 1, 3, 3, cost, memo);
    }

    private long dfs(int i, int preJ, int preK, int[][] cost, long[][][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][preJ][preK] != -1) { // 之前計算過
            return memo[i][preJ][preK];
        }
        long res = Long.MAX_VALUE;
        for (int j = 0; j < 3; j++) {
            if (j == preJ) {
                continue;
            }
            for (int k = 0; k < 3; k++) {
                if (k != preK && k != j) {
                    res = Math.min(res, dfs(i - 1, j, k, cost, memo) + cost[i][j] + cost[cost.length - 1 - i][k]);
                }
            }
        }
        return memo[i][preJ][preK] = res; // 記憶化
    }


    // https://leetcode.cn/problems/maximum-and-minimum-sums-of-at-most-size-k-subarrays/solutions/3051552/gong-xian-fa-dan-diao-zhan-pythonjavacgo-9gz3/
    public long minMaxSubarraySum(int[] nums, int k) {
        long ans = sumSubarrayMins(nums, k);
        // 所有元素取反，就可以復用同一份代碼求最大值的貢獻了
        for (int i = 0; i < nums.length; i++) {
            nums[i] = -nums[i];
        }
        ans -= sumSubarrayMins(nums, k);
        return ans;
    }

    // 計算最小值的貢獻
    private long sumSubarrayMins(int[] nums, int k) {
        int n = nums.length;
        // 左邊界 left[i] 為左側嚴格小於 nums[i] 的最近元素位置（不存在時為 -1）
        int[] left = new int[n];
        Arrays.fill(left, -1);
        // 右邊界 right[i] 為右側小於等於 nums[i] 的最近元素位置（不存在時為 n）
        int[] right = new int[n];
        Arrays.fill(right, n);

        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 哨兵，方便賦值 left
        for (int i = 0; i < n; i++) {
            while (st.size() > 1 && nums[i] <= nums[st.peek()]) {
                right[st.pop()] = i; // i 是棧頂的右邊界
            }
            left[i] = st.peek();
            st.push(i);
        }

        long res = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int l = left[i];
            int r = right[i];
            if (r - l - 1 <= k) {
                long cnt = (long) (i - left[i]) * (right[i] - i);
                res += x * cnt; // 累加貢獻
            } else {
                l = Math.max(l, i - k);
                r = Math.min(r, i + k);
                // 左端點 > r-k 的子數組個數
                long cnt = (long) (r - i) * (i - (r - k));
                // 左端點 <= r-k 的子數組個數
                long cnt2 = (long) (l + r + k - i * 2 + 1) * (r - l - k) / 2;
                res += x * (cnt + cnt2); // 累加貢獻
            }
        }
        return res;
    }


}









