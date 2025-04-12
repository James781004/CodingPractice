package EndlessCheng.GenreMenu.BinarySearch.Minimum;

import java.util.PriorityQueue;

public class MinNumberOfSeconds {

    // https://leetcode.cn/problems/minimum-number-of-seconds-to-make-mountain-height-zero/solutions/2925848/er-fen-da-an-pythonjavacgo-by-endlessche-myg4/
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        int maxT = 0;
        for (int t : workerTimes) {
            maxT = Math.max(maxT, t);
        }
        int h = (mountainHeight - 1) / workerTimes.length + 1;
        long left = 0;
        long right = (long) maxT * h * (h + 1) / 2;
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if (check(mid, mountainHeight, workerTimes)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    // 查看當前秒數下，能否將山的高度降低到0
    private boolean check(long m, int leftH, int[] workerTimes) {
        for (int t : workerTimes) {
            leftH -= ((int) Math.sqrt(m / t * 8 + 1) - 1) / 2;
            if (leftH <= 0) {
                return true;
            }
        }
        return false;
    }


    public long minNumberOfSeconds2(int mountainHeight, int[] workerTimes) {
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        for (int t : workerTimes) {
            pq.offer(new long[]{t, t, t});
        }
        long ans = 0;
        while (mountainHeight-- > 0) {
            // 工作後總用時，當前工作（山高度降低 1）用時，workerTimes[i]
            long[] w = pq.poll();
            long nxt = w[0], delta = w[1], base = w[2];
            ans = nxt; // 最後一個出堆的 nxt 即為答案
            pq.offer(new long[]{nxt + delta + base, delta + base, base});
        }
        return ans;
    }

}
