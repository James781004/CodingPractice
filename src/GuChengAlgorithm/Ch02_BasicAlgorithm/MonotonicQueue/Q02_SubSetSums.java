package GuChengAlgorithm.Ch02_BasicAlgorithm.MonotonicQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q02_SubSetSums {
    // https://docs.google.com/presentation/d/1euJtFtpPFJg8tf4qDgdlrqn9a38nGnk6zghl45C6Gj4/edit#slide=id.gb293594dbe_0_36
    // 求出前綴和之後,需求：j > i && preSum[j] - preSum[i] >= k && (j - i) 最小.
    // 需求的通俗理解：相當於排隊，對某個人來說，要找到前面比我矮最少k的人，且我和這個人的距離最近。
    public int shortestSubArray(int[] A, int k) {
        int N = A.length, res = N + 1;
        int[] sum = new int[N + 1];

        // 前綴和
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + A[i];
        }

        // 求數值和大於等於k的最短子數組
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            while (!q.isEmpty() && sum[i] - sum[q.peekFirst()] >= k) // 左出q，統計窗口大小
                res = Math.min(res, i - q.pollFirst());  // 窗口大小符合條件(數值和大於等於k)，處理res
            while (!q.isEmpty() && sum[q.peekLast()] >= sum[i]) q.pollLast();    // 右出q，保證遞增隊列
            q.offerLast(i); // 當前位置i進q
        }
        return res <= N ? res : -1;
    }


    // https://docs.google.com/presentation/d/1euJtFtpPFJg8tf4qDgdlrqn9a38nGnk6zghl45C6Gj4/edit#slide=id.gb293594dbe_0_57
    public int constrainedSubSetSums(int[] nums, int k) {
        Deque<Integer> q = new ArrayDeque<>();
        int[] sum = new int[nums.length];
        int res = nums[0];
        for (int i = 0; i < nums.length; i++) {
            sum[i] = nums[i];
            if (!q.isEmpty()) sum[i] += sum[q.peekFirst()];  // 取解
            res = Math.max(res, sum[i]); // 處理res
            if (!q.isEmpty() && i - q.peekFirst() >= k) q.pollFirst(); // 左出q
            while (!q.isEmpty() && sum[q.peekLast()] <= sum[i]) q.pollLast(); // 右出q
            if (sum[i] > 0) q.offerLast(i); // 進q
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1euJtFtpPFJg8tf4qDgdlrqn9a38nGnk6zghl45C6Gj4/edit#slide=id.gb293594dbe_0_67
    public int longestSubArray(int[] A, int limit) {
        Deque<Integer> maxd = new ArrayDeque<>();
        Deque<Integer> mind = new ArrayDeque<>();
        int left = 0, res = 0;
        for (int i = 0; i < A.length; i++) {
            while (!maxd.isEmpty() && maxd.peekLast() < A[i]) maxd.pollLast();  // 遞增
            while (!mind.isEmpty() && A[i] < mind.peekLast()) mind.pollLast();  // 遞減
            maxd.add(A[i]);
            mind.add(A[i]);
            if (maxd.peek() - mind.peek() > limit) {
                if (maxd.peek() == A[left]) maxd.poll();
                if (mind.peek() == A[left]) mind.poll();
                left++;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }
}
