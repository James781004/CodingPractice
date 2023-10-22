package Grind.Ch11_Stack;

import java.util.*;

public class Q10_MaximumFrequencyStack {
    // https://leetcode.cn/problems/maximum-frequency-stack/solutions/1998430/mei-xiang-ming-bai-yi-ge-dong-hua-miao-d-oich/
    class FreqStack {
        private final Map<Integer, Integer> cnt = new HashMap<>();
        private final List<Deque<Integer>> stacks = new ArrayList<>();

        public void push(int val) {
            int c = cnt.getOrDefault(val, 0);
            if (c == stacks.size()) // 這個元素的頻率已經是目前最多的，現在又出現了一次
                stacks.add(new ArrayDeque<>()); // 那麼必須創建一個新棧
            stacks.get(c).push(val);
            cnt.put(val, c + 1); // 更新頻率
        }

        public int pop() {
            int back = stacks.size() - 1;
            int val = stacks.get(back).pop(); // 彈出最右側棧的棧頂
            if (stacks.get(back).isEmpty()) // 棧為空
                stacks.remove(back); // 刪除
            cnt.put(val, cnt.get(val) - 1); // 更新頻率
            return val;
        }
    }
}
