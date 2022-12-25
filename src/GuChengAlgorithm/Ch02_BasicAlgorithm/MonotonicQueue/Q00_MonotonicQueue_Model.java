package GuChengAlgorithm.Ch02_BasicAlgorithm.MonotonicQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q00_MonotonicQueue_Model {
    // https://docs.google.com/presentation/d/1euJtFtpPFJg8tf4qDgdlrqn9a38nGnk6zghl45C6Gj4/edit#slide=id.gb293594dbe_0_14
    // 這里可以有不同的順序，只要保證
    // 1. 插入必須在去尾之後來位置單調性
    // 2. 根據不同題目來改變取解的具體位置
    public int[] MonotonicQueue(int[] nums, int k) {
        int N = nums.length;
        Deque<Integer> q = new ArrayDeque<>();
        int[] res = new int[N - k + 1];
        for (int i = 0; i < N; i++) {
            while (!q.isEmpty() && i - q.peekFirst() >= k) q.pollFirst();        // 左出q，保證窗口大小k - 1
            while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) q.pollLast();  // 右出q，保證遞減隊列
            q.offerLast(i); // 進q，此時q.size == k
            q.peekFirst();  // 使用q左邊最大值處理結果，可以在不同位置
        }
        return res;
    }
}
