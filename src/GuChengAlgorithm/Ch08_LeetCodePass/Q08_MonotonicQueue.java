package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

public class Q08_MonotonicQueue {
    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g108bbbbe68c_1_171
    class SlidingWindowMaximum {
        public int[] maxWindow(int[] nums, int k) {
            int N = nums.length;
            Deque<Integer> q = new ArrayDeque<>();
            int[] res = new int[N - k + 1];

            for (int i = 0; i < N; i++) {
                int startWindowIndex = i - k + 1;
                while (!q.isEmpty() && i - q.peekFirst() >= k) q.pollFirst();            // 左出q，保證窗口大小k-1
                while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) q.pollLast();      // 右出q，保證遞減隊列
                q.offerLast(i);                                                          // 進q，此時q.size == k
                if (startWindowIndex >= 0) res[startWindowIndex] = nums[q.peekFirst()];  // 使用q左邊最大值
            }

            return res;
        }


        public int[] maxWindowPQ(int[] nums, int k) {
            int N = nums.length;
            int[] res = new int[N - k + 1];
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);

            for (int i = 0; i < k; i++) {
                pq.offer(new int[]{i, nums[i]});
            }

            res[0] = pq.peek()[1];
            int index = 1;

            for (int i = k; i < N; i++) {
                while (!pq.isEmpty() && i - pq.peek()[0] >= k) pq.poll(); // 當前最大值出window
                pq.offer(new int[]{i, nums[i]});
                res[index++] = pq.peek()[1];
            }

            return res;
        }
    }


    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g108bbbbe68c_1_180
    class ShortestSubArrayWithSumAtLeastK {
        public int shortestSubArray(int[] A, int k) {
            int N = A.length, res = N + 1;
            long[] sum = new long[N + 1];  // prefixSum
            for (int i = 0; i < N; i++) {
                sum[i + 1] = sum[i] + A[i];
            }

            Deque<Integer> q = new ArrayDeque<>();
            for (int i = 0; i < N + 1; i++) {
                while (!q.isEmpty() && sum[i] - sum[q.peekFirst()] >= k) {         // 左出q，統計窗口大小
                    res = Math.min(res, i - q.pollFirst());                        // 取解
                }
                while (!q.isEmpty() && sum[q.peekLast()] >= sum[i]) q.pollLast();  // 右出q，保證遞增隊列
                q.offerLast(i);                                                    // 進q
            }
            return res <= N ? res : -1;
        }
    }
}
