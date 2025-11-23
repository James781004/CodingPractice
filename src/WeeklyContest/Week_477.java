package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_477 {

    // https://leetcode.cn/problems/concatenate-non-zero-digits-and-multiply-by-sum-i/solutions/3839722/bu-yong-zi-fu-chuan-de-zuo-fa-pythonjava-0pai/
    public long sumAndMultiply(int n) {
        int x = 0;
        int sum = 0;
        for (int pow10 = 1; n > 0; n /= 10) {
            int d = n % 10;
            if (d > 0) {
                x += d * pow10;
                sum += d;
                pow10 *= 10;
            }
        }
        return (long) x * sum;
    }


    // https://leetcode.cn/problems/find-maximum-balanced-xor-subarray-length/solutions/3839716/qian-zhui-he-yu-ha-xi-biao-pythonjavacgo-2504/
    public int maxBalancedSubarray(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int xor = 0;
        int diff = n; // 保證 diff 非負
        Map<Long, Integer> pos = new HashMap<>();
        // 把 xor 和 diff 合並為一個 long
        pos.put((long) xor << 32 | diff, -1); // 空前綴的位置視作 -1
        for (int i = 0; i < n; i++) {
            xor ^= nums[i];
            diff += nums[i] % 2 == 1 ? 1 : -1;
            long key = (long) xor << 32 | diff;
            Integer j = pos.get(key);
            if (j != null) {
                ans = Math.max(ans, i - j);
            } else {
                pos.put(key, i);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/concatenate-non-zero-digits-and-multiply-by-sum-ii/solutions/3839697/san-ge-qian-zhui-he-pythonjavacgo-by-end-6e1a/
    private static final int MOD = 1_000_000_007;
    private static final int MAX_N = 100_001;
    private static final int[] pow10 = new int[MAX_N];

    static {
        // 預處理 10 的冪
        pow10[0] = 1;
        for (int i = 1; i < MAX_N; i++) {
            pow10[i] = (int) (pow10[i - 1] * 10L % MOD);
        }
    }

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();
        int[] sumD = new int[n + 1];       // s 的前綴和
        int[] preNum = new int[n + 1];     // s 的前綴對應的數字（模 mod）
        int[] sumNonZero = new int[n + 1]; // s 的前綴的非零數字個數
        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            sumD[i + 1] = sumD[i] + d; // s 的前綴和
            preNum[i + 1] = d > 0 ? (int) ((preNum[i] * 10L + d) % MOD) : preNum[i]; // s 的前綴對應的數字（模 MOD）
            sumNonZero[i + 1] = sumNonZero[i] + (d > 0 ? 1 : 0); // s 的前綴的非零數字個數
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1] + 1; // 注意這裡把 r 加一了
            int length = sumNonZero[r] - sumNonZero[l];
            long x = preNum[r] - (long) preNum[l] * pow10[length] % MOD; // 注意結果可能是負數，所以下面 +MOD
            ans[i] = (int) ((x + MOD) * (sumD[r] - sumD[l]) % MOD);
        }
        return ans;
    }


    // https://leetcode.cn/problems/number-of-effective-subsequences/solutions/3839695/zheng-nan-ze-fan-rong-chi-yuan-li-sos-dp-abir/
//    private static final int MOD = 1_000_000_007;
//    private static final int MAX_N = 100_001;
    private static final int[] pow2 = new int[MAX_N];
    private static boolean initialized = false;

    // 這樣寫比 static block 快
    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        // 預處理 2 的冪
        pow2[0] = 1;
        for (int i = 1; i < MAX_N; i++) {
            pow2[i] = pow2[i - 1] * 2 % MOD;
        }
    }

    public int countEffective(int[] nums) {
        init();

        int or = 0;
        for (int x : nums) {
            or |= x;
        }

        int mx = 32 - Integer.numberOfLeadingZeros(or);
        int[] f = new int[1 << mx];
        for (int x : nums) {
            f[x]++;
        }
        for (int i = 0; i < mx; i++) {
            for (int s = 0; s < (1 << mx); s++) {
                s |= 1 << i;
                f[s] += f[s ^ (1 << i)];
            }
        }
        // 計算完畢後，f[s] 表示 nums 中的是 s 的子集的元素個數

        long ans = pow2[nums.length]; // 所有子序列的個數
        // 枚舉 or 的所有子集（包括空集）
        int sub = or;
        do {
            int sign = Integer.bitCount(or ^ sub) % 2 > 0 ? -1 : 1;
            ans -= sign * pow2[f[sub]];
            sub = (sub - 1) & or;
        } while (sub != or);
        return (int) ((ans % MOD + MOD) % MOD);
    }


}









