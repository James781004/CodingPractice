package EndlessCheng.GenreMenu.DP.Enhance.MonoQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaxResult {

    // https://leetcode.cn/problems/jump-game-vi/solutions/2631981/yi-bu-bu-you-hua-cong-di-gui-dao-di-tui-84qn3/
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] f = new int[n];
        f[0] = nums[0];
        Deque<Integer> q = new ArrayDeque<>();
        q.add(0);
        for (int i = 1; i < n; i++) {
            // 1. 出：如果隊首小於 i−k，則彈出隊首。注意單調隊列只需保存下標
            if (q.peekFirst() < i - k) {
                q.pollFirst();
            }
            // 2. 轉移：f[i]=f[q[0]]+nums[i]。其中 q[0] 表示單調隊列 q 的隊首，此時隊首就是轉移來源最大值的下標
            f[i] = f[q.peekFirst()] + nums[i];
            // 3. 入：不斷彈出隊尾，直到隊列為空，或者 f[i] 小於隊尾對應的 f 值為止。然後把 i 加到隊尾
            while (!q.isEmpty() && f[i] >= f[q.peekLast()]) {
                q.pollLast();
            }
            q.add(i);
        }
        return f[n - 1];
    }

    public int maxResult2(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> q = new ArrayDeque<>(); // 更快的寫法見【Java 數組】
        q.add(0);
        for (int i = 1; i < n; i++) {
            // 1. 出
            if (q.peekFirst() < i - k) {
                q.pollFirst();
            }
            // 2. 轉移
            nums[i] += nums[q.peekFirst()];
            // 3. 入
            while (!q.isEmpty() && nums[i] >= nums[q.peekLast()]) {
                q.pollLast();
            }
            q.add(i);
        }
        return nums[n - 1];
    }


}
