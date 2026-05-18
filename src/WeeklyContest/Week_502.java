package WeeklyContest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Week_502 {

    // https://leetcode.cn/problems/check-adjacent-digit-differences/solutions/3969736/bian-li-pythonjavacgo-by-endlesscheng-9mh6/
    public boolean isAdjacentDiffAtMostTwo(String s) {
        for (int i = 1; i < s.length(); i++) {
            if (Math.abs(s.charAt(i) - s.charAt(i - 1)) > 2) {
                return false;
            }
        }
        return true;
    }

    // https://leetcode.cn/problems/count-k-th-roots-in-a-range/solutions/3969732/shu-xue-zuo-fa-pythonjavacgo-by-endlessc-8xed/
    public int f(int n, int k) {
        if (n < 0) {
            return 0;
        }
        int x = (int) Math.pow(n, 1.0 / k);
        // 可能 x 的正確值是 6，但算出來的 x = int(5.99999...) = 5
        if (pow(x + 1, k) <= n) { // 避免浮點誤差，這裡用整數計算 pow
            x++;
        }
        return x + 1;
    }

    public int countKthRoots(int l, int r, int k) {
        return f(r, k) - f(l - 1, k);
    }

    // 50. Pow(x, n)
    private long pow(long x, int k) {
        long res = 1;
        while (k > 0) {
            if (k % 2 > 0) {
                res *= x;
            }
            x *= x;
            k /= 2;
        }
        return res;
    }


    // https://leetcode.cn/problems/largest-local-values-in-a-matrix-ii/solutions/3969719/mo-ban-er-wei-st-biao-pythonjavacgo-by-e-hw5t/
    public int countLocalMaximums(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int wn = 32 - Integer.numberOfLeadingZeros(n);
        int wm = 32 - Integer.numberOfLeadingZeros(m);

        // st[k1][k2][i][j] 表示左上角在 (i, j)，右下角在 (i+(1<<k1)-1, j+(1<<k2)-1) 的子矩陣最大值
        int[][][][] st = new int[wn][wm][n][m];

        // 初始值
        st[0][0] = matrix;

        // 單獨計算 k1 = 0
        for (int k2 = 1; k2 < wm; k2++) {
            int half = 1 << (k2 - 1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= m - (1 << k2); j++) {
                    st[0][k2][i][j] = Math.max(st[0][k2 - 1][i][j], st[0][k2 - 1][i][j + half]);
                }
            }
        }

        for (int k1 = 1; k1 < wn; k1++) {
            int half = 1 << (k1 - 1);
            for (int k2 = 0; k2 < wm; k2++) {
                for (int i = 0; i <= n - (1 << k1); i++) {
                    for (int j = 0; j <= m - (1 << k2); j++) {
                        st[k1][k2][i][j] = Math.max(st[k1 - 1][k2][i][j], st[k1 - 1][k2][i + half][j]);
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int x = matrix[i][j];
                if (x == 0) {
                    continue;
                }
                int max1 = query(st, i - x, j - x + 1, i + x + 1, j + x, n, m);
                int max2 = query(st, i - x + 1, j - x, i + x, j + x + 1, n, m);
                if (Math.max(max1, max2) <= x) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // 返回子矩陣最大值
    // 左閉右開，行號范圍 [r1, r2)，列號范圍 [c1, c2)
    private int query(int[][][][] st, int r1, int c1, int r2, int c2, int n, int m) {
        r1 = Math.max(r1, 0);
        c1 = Math.max(c1, 0);
        r2 = Math.min(r2, n);
        c2 = Math.min(c2, m);
        int k1 = 31 - Integer.numberOfLeadingZeros(r2 - r1);
        int k2 = 31 - Integer.numberOfLeadingZeros(c2 - c1);
        // 視作四個子矩陣的並集
        return Math.max(
                Math.max(st[k1][k2][r1][c1], st[k1][k2][r2 - (1 << k1)][c1]),
                Math.max(st[k1][k2][r1][c2 - (1 << k2)], st[k1][k2][r2 - (1 << k1)][c2 - (1 << k2)])
        );
    }


    // https://leetcode.cn/problems/smallest-unique-subarray/solutions/3969805/3934-zui-duan-wei-yi-zi-shu-zu-by-storms-29j9/
    static final int BASE_LOWER_BOUND = 100001, BASE_UPPER_BOUND = 200001;
    static final int MODULO_BOUND = 1000000007;
    Random random = new Random();
    long base1, base2, modulo1, modulo2;
    int n;
    long[] hashes1;
    long[] hashes2;
    long[] factors1;
    long[] factors2;

    public int smallestUniqueSubarray(int[] nums) {
        this.base1 = BASE_LOWER_BOUND + random.nextInt(BASE_UPPER_BOUND - BASE_LOWER_BOUND + 1);
        this.base2 = BASE_LOWER_BOUND + random.nextInt(BASE_UPPER_BOUND - BASE_LOWER_BOUND + 1);
        this.modulo1 = MODULO_BOUND + random.nextInt(MODULO_BOUND);
        this.modulo2 = MODULO_BOUND + random.nextInt(MODULO_BOUND);
        this.n = nums.length;
        this.hashes1 = new long[n + 1];
        this.hashes2 = new long[n + 1];
        this.factors1 = new long[n + 1];
        this.factors2 = new long[n + 1];
        factors1[0] = 1;
        factors2[0] = 1;
        for (int i = 1; i <= n; i++) {
            int val = nums[i - 1];
            hashes1[i] = (hashes1[i - 1] * base1 % modulo1 + val) % modulo1;
            hashes2[i] = (hashes2[i - 1] * base2 % modulo2 + val) % modulo2;
            factors1[i] = factors1[i - 1] * base1 % modulo1;
            factors2[i] = factors2[i - 1] * base2 % modulo2;
        }
        int low = 1, high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (allUnique(nums, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public boolean allUnique(int[] nums, int subLength) {
        Map<Long, Integer> hashes1Count = new HashMap<Long, Integer>();
        Map<Long, Integer> hashes2Count = new HashMap<Long, Integer>();
        for (int i = subLength - 1; i < n; i++) {
            long hash1 = getHash(hashes1, factors1, modulo1, i - subLength + 1, i);
            long hash2 = getHash(hashes2, factors2, modulo2, i - subLength + 1, i);
            hashes1Count.put(hash1, hashes1Count.getOrDefault(hash1, 0) + 1);
            hashes2Count.put(hash2, hashes2Count.getOrDefault(hash2, 0) + 1);
        }
        Set<Map.Entry<Long, Integer>> entries1 = hashes1Count.entrySet();
        Set<Map.Entry<Long, Integer>> entries2 = hashes2Count.entrySet();
        for (Map.Entry<Long, Integer> entry : entries1) {
            if (entry.getValue() == 1) {
                return true;
            }
        }
        for (Map.Entry<Long, Integer> entry : entries2) {
            if (entry.getValue() == 1) {
                return true;
            }
        }
        return false;
    }

    public long getHash(long[] hashes, long[] factors, long modulo, int start, int end) {
        long hash = (hashes[end + 1] - hashes[start] * factors[end - start + 1] % modulo) % modulo;
        if (hash < 0) {
            hash += modulo;
        }
        return hash;
    }


}










