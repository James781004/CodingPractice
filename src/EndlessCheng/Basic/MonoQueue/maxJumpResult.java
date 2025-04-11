package EndlessCheng.Basic.MonoQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class maxJumpResult {

    // https://leetcode.cn/problems/jump-game-vi/solutions/2631981/yi-bu-bu-you-hua-cong-di-gui-dao-di-tui-84qn3/
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] f = new int[n]; // 表示從 0 跳到 i，經過的所有數字之和的最大值
        f[0] = nums[0];
        Deque<Integer> q = new ArrayDeque<>();
        q.add(0);
        for (int i = 1; i < n; i++) {
            // 1. 出
            if (q.peekFirst() < i - k) {
                q.pollFirst();
            }
            // 2. 轉移
            f[i] = f[q.peekFirst()] + nums[i]; // 計算 f[i] 時，需要保證隊首就是轉移來源最大值的下標
            // 3. 入
            while (!q.isEmpty() && f[i] >= f[q.peekLast()]) {
                q.pollLast();
            }
            q.add(i);
        }
        return f[n - 1];
    }


}
