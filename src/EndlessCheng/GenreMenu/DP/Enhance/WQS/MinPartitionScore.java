package EndlessCheng.GenreMenu.DP.Enhance.WQS;

import java.math.BigInteger;
import java.util.Arrays;

public class MinPartitionScore {

    // https://leetcode.cn/problems/minimum-partition-score/solutions/3893573/hua-fen-xing-dp-xie-lu-you-hua-tu-bao-yo-5cb0/
    private static class Vec {
        long x;
        long y;

        Vec(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private Vec sub(Vec a, Vec b) {
        return new Vec(a.x - b.x, a.y - b.y);
    }

    private long dot(Vec a, Vec b) {
        return a.x * b.x + a.y * b.y;
    }

    // 如果乘法會溢出，用 detCmp
    private long det(Vec a, Vec b) {
        return a.x * b.y - a.y * b.x;
    }

    private int detCmp(Vec a, Vec b) {
        return BigInteger.valueOf(a.x).multiply(BigInteger.valueOf(b.y))
                .compareTo(BigInteger.valueOf(a.y).multiply(BigInteger.valueOf(b.x)));
    }

    public long minPartitionScore(int[] nums, int k) {
        int n = nums.length;
        int[] sum = new int[n + 1]; // nums 的前綴和
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        long[] f = new long[n + 1];
        Arrays.fill(f, Long.MAX_VALUE / 2);
        f[0] = 0;

        Vec[] q = new Vec[n + 1];

        for (int K = 1; K <= k; K++) {
            int head = 0;
            int tail = 0; // 模擬 deque

            long s = sum[K - 1];
            q[tail++] = new Vec(s, f[K - 1] + s * s - s);

            for (int i = K; i <= n - (k - K); i++) { // 其他子數組的長度至少是 1
                s = sum[i];
                Vec p = new Vec(-2 * s, 1);
                while (tail - head > 1 && dot(p, q[head]) >= dot(p, q[head + 1])) {
                    head++;
                }

                Vec v = new Vec(s, f[i] + s * s - s);
                f[i] = dot(p, q[head]) + s * s + s;

                // 讀者可以把 detCmp 改成 det 感受下這個算法的效率
                // 目前 det 也能過，可以試試 hack 一下
                while (tail - head > 1 && detCmp(sub(q[tail - 1], q[tail - 2]), sub(v, q[tail - 1])) <= 0) {
                    tail--;
                }
                q[tail++] = v;
            }
        }

        return f[n] / 2;
    }


}
