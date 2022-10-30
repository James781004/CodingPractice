package FuckingAlgorithm.StructureDesign;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q06_MonoQueue {

    class MonotonicQueue {
        // 雙鏈表，支持頭部和尾部增刪元素
        // 維護其中的元素自尾部到頭部單調遞增
        private LinkedList<Integer> maxq = new LinkedList<>();

        // 在尾部添加一個元素 n，維護 maxq 的單調性質
        public void push(int n) {
            // 將前面小於自己的元素都刪除
            while (!maxq.isEmpty() && maxq.getLast() < n) {
                maxq.pollLast();
            }
            maxq.addLast(n);
        }

        public int max() {
            // 隊頭的元素肯定是最大的
            return maxq.getFirst();
        }

        public void pop(int n) {
            if (n == maxq.getFirst()) {
                maxq.pollFirst();
            }
        }
    }


//    https://leetcode.cn/problems/sliding-window-maximum/
//    239. 滑動窗口最大值
//    給你一個整數數組 nums，有一個大小為 k 的滑動窗口從數組的最左側移動到數組的最右側。
//    你只可以看到在滑動窗口內的 k 個數字。滑動窗口每次只向右移動一位。
//
//    返回 滑動窗口中的最大值 。

    int[] maxSlidingWindow(int[] nums, int k) {
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
}
