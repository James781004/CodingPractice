package LeetcodeMaster.StackAndQueue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Q02_DesignStackByQueue {
//    225. 用隊列實現棧
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0225.%E7%94%A8%E9%98%9F%E5%88%97%E5%AE%9E%E7%8E%B0%E6%A0%88.md
//
//    使用隊列實現棧的下列操作：
//
//    push(x) -- 元素 x 入棧
//    pop() -- 移除棧頂元素
//    top() -- 獲取棧頂元素
//    empty() -- 返回棧是否為空
//    注意:
//
//    你只能使用隊列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 這些操作是合法的。
//    你所使用的語言也許不支持隊列。 你可以使用 list 或者 deque（雙端隊列）來模擬一個隊列 , 只要是標準的隊列操作即可。
//    你可以假設所有操作都是有效的（例如, 對一個空的棧不會調用 pop 或者 top 操作）。

    class MyStack {
        Queue<Integer> queue1; // 和棧中保持一樣元素的隊列
        Queue<Integer> queue2; // 輔助隊列


        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

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


    class MyStack2 {
        // Deque 接口繼承了 Queue 接口
        // 所以 Queue 中的 add、poll、peek等效於 Deque 中的 addLast、pollFirst、peekFirst
        Deque<Integer> que1;

        /**
         * Initialize your data structure here.
         */
        public MyStack2() {
            que1 = new ArrayDeque<>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            que1.addLast(x);
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            int size = que1.size();
            size--;
            // 將 que1 導入 que2 ，但留下最後一個值
            while (size-- > 0) {
                que1.addLast(que1.peekFirst());
                que1.pollFirst();
            }

            int res = que1.pollFirst();
            return res;
        }

        /**
         * Get the top element.
         */
        public int top() {
            return que1.peekLast();
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return que1.isEmpty();
        }
    }
}
