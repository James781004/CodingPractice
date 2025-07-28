package WeeklyContest;

import java.util.*;

public class Week_460 {

    // https://leetcode.cn/problems/maximum-median-sum-of-subsequences-of-size-3/solutions/3734796/tan-xin-fu-xiang-si-ti-mu-pythonjavacgo-6as1o/
    public long maximumMedianSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        long ans = 0;
        for (int i = n - 2; i >= n / 3; i -= 2) {
            ans += nums[i];
        }
        return ans;
    }

    // https://leetcode.cn/problems/maximum-number-of-subsequences-after-one-inserting/solutions/3734800/fu-yong-115-ti-dai-ma-qian-hou-zhui-fen-gtkqz/
    public long numOfSubsequences(String S) {
        char[] s = S.toCharArray();
        long extra = Math.max(Math.max(numDistinct(s, "CT"), numDistinct(s, "LC")), calcInsertT(s));
        return numDistinct(s, "LCT") + extra;
    }

    // 115. 不同的子序列
    private long numDistinct(char[] s, String T) {
        int n = s.length;
        int m = T.length();
        if (n < m) {
            return 0;
        }

        char[] t = T.toCharArray();
        long[] f = new long[m + 1];
        f[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = Math.min(i, m - 1); j >= Math.max(m - n + i, 0); j--) {
                if (s[i] == t[j]) {
                    f[j + 1] += f[j];
                }
            }
        }
        return f[m];
    }

    // 計算插入 T 產生的額外 LCT 子序列個數的最大值
    private long calcInsertT(char[] s) {
        int cntT = 0;
        for (char c : s) {
            if (c == 'T') {
                cntT++;
            }
        }

        long res = 0;
        int cntL = 0;
        for (char c : s) {
            if (c == 'T') {
                cntT--;
            }
            if (c == 'L') {
                cntL++;
            }
            res = Math.max(res, (long) cntL * cntT);
        }
        return res;
    }

    // https://leetcode.cn/problems/minimum-jumps-to-reach-end-via-prime-teleportation/solutions/3734792/bfspythonjavacgo-by-endlesscheng-bu60/
    private static final int mx = 1_000_001;
    private static final List<Integer>[] primeFactors = new ArrayList[mx];
    private static boolean initialized = false;

    // 這樣寫比 static block 更快
    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        Arrays.setAll(primeFactors, v -> new ArrayList<>());
        // 預處理每個數的質因子列表
        for (int i = 2; i < mx; i++) {
            if (primeFactors[i].isEmpty()) { // i 是質數
                for (int j = i; j < mx; j += i) { // i 的倍數有質因子 i
                    primeFactors[j].add(i);
                }
            }
        }
    }

    public int minJumps(int[] nums) {
        init();

        int n = nums.length;
        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int p : primeFactors[nums[i]]) {
                groups.computeIfAbsent(p, v -> new ArrayList<>()).add(i);
            }
        }

        int ans = 0;
        boolean[] vis = new boolean[n];
        vis[0] = true;
        List<Integer> q = List.of(0);

        while (true) {
            List<Integer> tmp = q;
            q = new ArrayList<>();
            for (int i : tmp) {
                if (i == n - 1) {
                    return ans;
                }
                List<Integer> idx = groups.computeIfAbsent(nums[i], v -> new ArrayList<>());
                idx.add(i + 1);
                if (i > 0) {
                    idx.add(i - 1);
                }
                for (int j : idx) {
                    if (!vis[j]) {
                        vis[j] = true;
                        q.add(j);
                    }
                }
                idx.clear();
            }
            ans++;
        }
    }


    // https://leetcode.cn/problems/partition-array-for-maximum-xor-and-and/solutions/3734850/shi-zi-bian-xing-xian-xing-ji-pythonjava-3e80/
    public long maximizeXorAndXor(int[] nums) {
        int n = nums.length;
        int maxVal = Arrays.stream(nums).max().getAsInt();
        int sz = 32 - Integer.numberOfLeadingZeros(maxVal);

        // 預處理所有子集的 AND 和 XOR（刷表法）
        int u = 1 << n;
        int[] subAnd = new int[u];
        int[] subXor = new int[u];
        subAnd[0] = -1;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int highBit = 1 << i;
            for (int mask = 0; mask < highBit; mask++) {
                subAnd[highBit | mask] = subAnd[mask] & x;
                subXor[highBit | mask] = subXor[mask] ^ x;
            }
        }
        subAnd[0] = 0;

        long ans = 0;
        for (int i = 0; i < u; i++) {
            ans = Math.max(ans, subAnd[(u - 1) ^ i] + maxXor2(i, subXor[i], nums, sz));
        }
        return ans;
    }

    private long maxXor2(int sub, int xor, int[] nums, int sz) {
        XorBasis b = new XorBasis(sz);
        for (int i = 0; i < nums.length; i++) {
            if ((sub >> i & 1) > 0) {
                // 只考慮有偶數個 1 的比特位（xor 在這些比特位上是 0）
                b.insert(nums[i] & ~xor);
            }
        }
        return xor + b.maxXor() * 2L;
    }


    // 線性基模板
    class XorBasis {
        private final int[] b;

        public XorBasis(int n) {
            b = new int[n];
        }

        public void insert(int x) {
            for (int i = b.length - 1; i >= 0; i--) {
                if ((x >> i & 1) > 0) {
                    if (b[i] == 0) {
                        b[i] = x;
                        return;
                    }
                    x ^= b[i];
                }
            }
        }

        public int maxXor() {
            int res = 0;
            for (int i = b.length - 1; i >= 0; i--) {
                if ((res ^ b[i]) > res) {
                    res ^= b[i];
                }
            }
            return res;
        }
    }


}









