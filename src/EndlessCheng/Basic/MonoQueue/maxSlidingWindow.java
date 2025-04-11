package EndlessCheng.Basic.MonoQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class maxSlidingWindow {

    // https://leetcode.cn/problems/sliding-window-maximum/solutions/2499715/shi-pin-yi-ge-shi-pin-miao-dong-dan-diao-ezj6/
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> q = new ArrayDeque<>(); // 雙端隊列
        for (int i = 0; i < n; i++) {
            // 1. 入
            while (!q.isEmpty() && nums[q.getLast()] <= nums[i]) {
                q.removeLast(); // 維護 q 的單調性
            }
            q.addLast(i); // 入隊
            // 2. 出
            if (i - q.getFirst() >= k) { // 隊首已經離開窗口了
                q.removeFirst();
            }
            // 3. 記錄答案
            if (i >= k - 1) {
                // 由於隊首到隊尾單調遞減，所以窗口最大值就是隊首
                ans[i - k + 1] = nums[q.getFirst()];
            }
        }
        return ans;
    }


}
