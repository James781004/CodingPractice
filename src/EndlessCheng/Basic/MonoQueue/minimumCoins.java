package EndlessCheng.Basic.MonoQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class minimumCoins {

    // https://leetcode.cn/problems/minimum-number-of-coins-for-fruits/solutions/2542044/dpcong-on2-dao-onpythonjavacgo-by-endles-nux5/
    public int minimumCoins(int[] prices) {
        int n = prices.length;
        Deque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[]{n + 1, 0}); // 哨兵
        for (int i = n; i > 0; i--) {
            while (q.peekLast()[0] > i * 2 + 1) { // 右邊離開窗口
                q.pollLast();
            }
            int f = prices[i - 1] + q.peekLast()[1];
            while (f <= q.peekFirst()[1]) {
                q.pollFirst();
            }
            q.addFirst(new int[]{i, f});  // 左邊進入窗口
        }
        return q.peekFirst()[1];
    }


}
