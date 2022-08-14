package LeetcodeMaster.StackAndQueue;

import java.util.Stack;

public class Q01_DesignQueueByStack {
//    232.用棧實現隊列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0232.%E7%94%A8%E6%A0%88%E5%AE%9E%E7%8E%B0%E9%98%9F%E5%88%97.md
//
//    使用棧實現隊列的下列操作：
//
//    push(x) -- 將一個元素放入隊列的尾部。
//    pop() -- 從隊列首部移除元素。
//    peek() -- 返回隊列首部的元素。
//    empty() -- 返回隊列是否為空。
//
//    示例:
//
//    MyQueue queue = new MyQueue();
//    queue.push(1);
//    queue.push(2);
//    queue.peek();  // 返回 1
//    queue.pop();   // 返回 1
//    queue.empty(); // 返回 false
//    說明:
//
//    你只能使用標準的棧操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
//    你所使用的語言也許不支持棧。你可以使用 list 或者 deque（雙端隊列）來模擬一個棧，只要是標準的棧操作即可。
//    假設所有操作都是有效的 （例如，一個空的隊列不會調用 pop 或者 peek 操作）。


    class MyQueue {
        Stack<Integer> stackIn;
        Stack<Integer> stackOut;

        public MyQueue() {
            stackIn = new Stack<>();
            stackOut = new Stack<>();
        }

        public void push(int x) {
            stackIn.push(x);
        }

        public int pop() {
            dumpStackIn();
            return stackOut.pop();
        }

        public int peek() {
            dumpStackIn();
            return stackOut.peek();
        }


        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return stackIn.isEmpty() && stackOut.isEmpty();
        }

        // 如果stackOut為空，那麽將stackIn中的元素全部放到stackOut中
        private void dumpStackIn() {
            if (!stackOut.isEmpty()) return;
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
    }
}
