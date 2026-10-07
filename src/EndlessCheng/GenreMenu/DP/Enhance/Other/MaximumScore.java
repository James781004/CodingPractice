package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.util.Arrays;

public class MaximumScore {

    // https://leetcode.cn/problems/maximize-cyclic-partition-score/solutions/3827068/min-ke-fu-si-ji-he-on-log-n-zuo-fa-by-ph-jvla/
    private static final long INF = (long) 1e15;

    // 狀態矩陣維度：3x3 (0: 封閉/獨立, 1: 需要匹配 +, 2: 需要匹配 -)
    // f[w][i][j] 儲存的是一個陣列，索引代表分割段數 k，值代表最大得分
    private long[][][][] f;
    private int nodeCount = 0;

    public long maximumScore(int[] nums, int k) {
        int n = nums.length;
        nodeCount = 0;
        f = new long[n << 1][3][3][];

        // 執行分治 (Divide and Conquer) 建立線段樹狀態
        int root = divideAndConquer(nums, 0, n - 1);

        long maxScore = 0;
        for (int i = 1; i <= k; i++) {
            // 情況 1：首尾完全閉合，使用了 i 個子段
            if (i < f[root][0][0].length) {
                maxScore = Math.max(maxScore, f[root][0][0][i]);
            }
            // 情況 2：首尾跨越（左端需要 +, 右端需要 -，跨越環形組合成一段）
            if (i < k && i < f[root][1][2].length) {
                maxScore = Math.max(maxScore, f[root][1][2][i]);
            }
            // 情況 3：首尾跨越（左端需要 -, 右端需要 +）
            if (i < k && i < f[root][2][1].length) {
                maxScore = Math.max(maxScore, f[root][2][1][i]);
            }
        }

        return maxScore;
    }

    private int divideAndConquer(int[] a, int l, int r) {
        int w = nodeCount++;
        int m = (l + r) >> 1;
        int len = r - l + 2;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                f[w][i][j] = new long[len];
                Arrays.fill(f[w][i][j], -INF);
            }
        }

        // 遞歸基底 (Base Case)：長度為 1 的區間
        if (l == r) {
            f[w][0][0][0] = 0;
            f[w][0][0][1] = 0;
            f[w][0][1][0] = a[l];
            f[w][1][0][0] = a[l];
            f[w][0][2][0] = -a[l];
            f[w][2][0][0] = -a[l];
            return w;
        }

        // 分治求解左右子區間
        int lw = divideAndConquer(a, l, m);
        int rw = divideAndConquer(a, m + 1, r);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // 1. 直接繼承左右子樹（點對點取 max）
                dotChmax(f[w][i][j], f[lw][i][j]);
                dotChmax(f[w][i][j], f[rw][i][j]);

                // 2. 左右子區間無跨越合併 (0 + 0)
                dotChmax(f[w][i][j], maxPlusConvolution(f[lw][i][0], f[rw][0][j]));

                // 3. 左右子區間跨越合併 (1 + 2 湊成一段新區間，段數 +1)
                dotChmax(f[w][i][j], rightShift(maxPlusConvolution(f[lw][i][1], f[rw][2][j])));

                // 4. 左右子區間跨越合併 (2 + 1 湊成一段新區間，段數 +1)
                dotChmax(f[w][i][j], rightShift(maxPlusConvolution(f[lw][i][2], f[rw][1][j])));
            }
        }

        return w;
    }

    // Min-Plus 卷積 (Max-Plus Convolution for Concave Arrays)
    // 利用凸函數的差分遞減特性，使用歸併 (Merge) 在 O(A.len + B.len) 時間內完成 $(+, \max)$ 卷積
    private long[] maxPlusConvolution(long[] a, long[] b) {
        int n = a.length;
        int m = b.length;
        long[] c = new long[n + m - 1];

        // 計算差分陣列 (adjacent_difference)
        long[] diffA = new long[n - 1];
        for (int i = 0; i < n - 1; i++) {
            diffA[i] = a[i + 1] - a[i];
        }

        long[] diffB = new long[m - 1];
        for (int i = 0; i < m - 1; i++) {
            diffB[i] = b[i + 1] - b[i];
        }

        c[0] = a[0] + b[0];

        // 雙指標歸併 (Merge diffA & diffB 降序排列)
        int p1 = 0, p2 = 0;
        int idx = 1;
        while (p1 < n - 1 && p2 < m - 1) {
            if (diffA[p1] >= diffB[p2]) {
                c[idx++] = diffA[p1++];
            } else {
                c[idx++] = diffB[p2++];
            }
        }
        while (p1 < n - 1) {
            c[idx++] = diffA[p1++];
        }
        while (p2 < m - 1) {
            c[idx++] = diffB[p2++];
        }

        // 前綴和還原 (partial_sum)
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i - 1] + c[i];
        }

        return c;
    }

    // 將陣列向右平移一位，相當於段數 k 增加了 1 (右移補上 -INF)
    private long[] rightShift(long[] a) {
        long[] res = new long[a.length + 1];
        res[0] = -INF;
        System.arraycopy(a, 0, res, 1, a.length);
        return res;
    }

    // 點對點更新最大值 (dot_chmax)
    private void dotChmax(long[] a, long[] b) {
        int limit = Math.min(a.length, b.length);
        for (int i = 0; i < limit; i++) {
            a[i] = Math.max(a[i], b[i]);
        }
    }
}
