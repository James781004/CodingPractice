package Grind.Ch01_Array;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Q21_SlidingWindowMaximum {
    // https://leetcode.cn/problems/sliding-window-maximum/solutions/8869/dan-diao-dui-lie-by-labuladong/
    /* 解題函數的實現 */
    public int[] maxSlidingWindow(int[] nums, int k) {
        MonotonicQueue window = new MonotonicQueue();
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                //先填滿窗口的前 k - 1
                window.push(nums[i]);
            } else {
                // 窗口向前滑動，加入新數字
                window.push(nums[i]);
                // 記錄當前窗口的最大值
                res.add(window.max());
                // 移出舊數字
                window.pop(nums[i - k + 1]);
            }
        }
        // 需要轉成 int[] 數組再返回
        int[] arr = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            arr[i] = res.get(i);
        }
        return arr;
    }

    /* 單調隊列的實現 */
    class MonotonicQueue {
        LinkedList<Integer> q = new LinkedList<>();

        public void push(int n) {
            // 將小於 n 的元素全部刪除
            while (!q.isEmpty() && q.getLast() < n) {
                q.pollLast();
            }
            // 然後將 n 加入尾部
            q.addLast(n);
        }

        public int max() {
            return q.getFirst();
        }

        public void pop(int n) {
            if (n == q.getFirst()) {
                q.pollFirst();
            }
        }
    }


    // https://zxi.mytechroad.com/blog/heap/leetcode-239-sliding-window-maximum/
    // 直接用 Deque 模擬單調隊列
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (k == 0) return nums;

        int[] ans = new int[nums.length - k + 1];
        Deque<Integer> indices = new LinkedList<>();

        for (int i = 0; i < nums.length; ++i) {
            // 將不大於當前 nums[i] 的元素全部刪除
            while (indices.size() > 0 && nums[i] >= nums[indices.getLast()]) {
                indices.removeLast();
            }

            // 從隊列後方加入下標 i
            indices.addLast(i);

            // 如果已經形成 window 隊列首位就是 window 最大值
            // 例如當前下標 i 為 2，k 為 3，
            // i - k + 1 == 0，窗口內包含 [0, 1, 2]，滿足形成窗口條件
            if (i - k + 1 >= 0) ans[i - k + 1] = nums[indices.getFirst()];

            // 加入新的右端點前，移除最左端下標
            if (i - k + 1 >= indices.getFirst()) indices.removeFirst();
        }

        return ans;
    }


}
