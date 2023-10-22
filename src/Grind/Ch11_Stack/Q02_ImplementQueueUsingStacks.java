package Grind.Ch11_Stack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Q02_ImplementQueueUsingStacks {
    // https://leetcode.cn/problems/implement-queue-using-stacks/solutions/1724489/by-carlsun-2-kxer/
    class MyQueue {

        Stack<Integer> stackIn;
        Stack<Integer> stackOut;

        /**
         * Initialize your data structure here.
         */
        public MyQueue() {
            stackIn = new Stack<>(); // 負責進棧
            stackOut = new Stack<>(); // 負責出棧
        }

        /**
         * Push element x to the back of queue.
         */
        public void push(int x) {
            stackIn.push(x);
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public int pop() {
            dumpstackIn();
            return stackOut.pop();
        }

        /**
         * Get the front element.
         */
        public int peek() {
            dumpstackIn();
            return stackOut.peek();
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return stackIn.isEmpty() && stackOut.isEmpty();
        }

        // 如果stackOut為空，那麼將stackIn中的元素全部放到stackOut中
        private void dumpstackIn() {
            if (!stackOut.isEmpty()) return;
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
    }


    // https://leetcode.cn/problems/implement-stack-using-queues/solutions/378317/225-yong-dui-lie-shi-xian-zhan-liang-ge-dui-lie-sh/
    // 使用兩個 Queue 實現棧
    class MyStack {

        Queue<Integer> queue1; // 和棧中保持一樣元素的隊列
        Queue<Integer> queue2; // 輔助隊列

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            queue2.offer(x); // 先放在輔助隊列中
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }
            Queue<Integer> queueTemp;
            queueTemp = queue1;
            queue1 = queue2;
            queue2 = queueTemp; // 最後交換queue1和queue2，將元素都放到queue1中
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            return queue1.poll(); // 因為queue1中的元素和棧中的保持一致，所以這個和下面兩個的操作只看queue1即可
        }

        /**
         * Get the top element.
         */
        public int top() {
            return queue1.peek();
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return queue1.isEmpty();
        }
    }
}
