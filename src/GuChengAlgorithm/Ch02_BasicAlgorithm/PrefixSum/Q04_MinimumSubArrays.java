package GuChengAlgorithm.Ch02_BasicAlgorithm.PrefixSum;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q04_MinimumSubArrays {
    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_1_53
    public int minSubArrayLen(int target, int[] A) {
        int left = 0, N = A.length, res = Integer.MAX_VALUE, sum = 0;
        for (int i = 0; i < N; i++) {
            sum += A[i];
            while (sum >= target) {
                res = Math.min(res, i - left + 1);
                sum -= A[left++];
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }


    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_1_64
    public int shortestSubArray(int k, int[] A) {
        int N = A.length, res = N + 1;
        int[] sum = new int[N + 1];
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            while (!q.isEmpty() && sum[q.peekLast()] >= sum[i]) q.pollLast();   // 右出q，保證遞增隊列
            while (!q.isEmpty() && sum[i] - sum[q.peekFirst()] >= k) // 左出q，統計窗口大小
                res = Math.min(res, i - q.pollFirst());    // 取解
            q.offerLast(i); // 進q
        }
        return res <= N ? res : -1;
    }
}
