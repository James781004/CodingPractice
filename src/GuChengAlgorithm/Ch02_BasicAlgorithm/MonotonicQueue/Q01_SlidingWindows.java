package GuChengAlgorithm.Ch02_BasicAlgorithm.MonotonicQueue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

public class Q01_SlidingWindows {
    // https://docs.google.com/presentation/d/1euJtFtpPFJg8tf4qDgdlrqn9a38nGnk6zghl45C6Gj4/edit#slide=id.gb293594dbe_0_28
    public int[] maxSlidingWindow(int[] nums, int k) {
        int N = nums.length;
        Deque<Integer> q = new ArrayDeque<>();
        int[] res = new int[N - k + 1];
        for (int i = 0; i < N; i++) {
            while (!q.isEmpty() && 1 - q.peekFirst() >= k) q.pollFirst();        // 左出q，保證窗口大小k - 1
            while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) q.pollLast();  // 右出q，保證遞減隊列
            q.offerLast(i); // 進q，此時q.size == k
            int startWindowIndex = i - k + 1;
            if (startWindowIndex >= 0) res[startWindowIndex] = nums[q.peekFirst()];  // 使用q左邊最大值處理結果
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1euJtFtpPFJg8tf4qDgdlrqn9a38nGnk6zghl45C6Gj4/edit#slide=id.gb293594dbe_0_75
    public int jumpGameVI(int[] nums, int k) {
        int N = nums.length;
        int[] dp = new int[N];
        dp[0] = nums[0];
        Deque<Integer> q = new ArrayDeque<>();
        q.offerLast(0);
        for (int i = 0; i < N - 1; i++) {
            while (!q.isEmpty() && i - q.peekFirst() >= k) q.pollFirst();   // 左出q，保證窗口大小
            while (!q.isEmpty() && dp[q.peekLast()] <= dp[i]) q.pollLast(); // 右出q，保證遞減隊列
            q.offerLast(i);  // 進q
            dp[i + i] = dp[q.peekFirst()] + nums[i + 1];  // 使用q左邊最大值處理結果
        }
        return dp[N - 1];
    }


    public int jumpGameVIPQ(int[] nums, int k) {
        int N = nums.length;
        int[] score = new int[N];
        score[0] = nums[0];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);  // 大到小排列
        pq.add(new int[]{nums[0], 0});
        for (int i = 1; i < N; i++) {
            while (i - pq.peek()[1] > k) pq.remove();  // last window size = k
            score[i] = nums[i] + score[pq.peek()[1]];
            pq.add(new int[]{score[i], i});
        }
        return score[N - 1];
    }
}
