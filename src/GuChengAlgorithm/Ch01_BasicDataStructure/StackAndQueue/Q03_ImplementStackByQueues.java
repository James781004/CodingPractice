package GuChengAlgorithm.Ch01_BasicDataStructure.StackAndQueue;

import java.util.LinkedList;
import java.util.Queue;

public class Q03_ImplementStackByQueues {
    class MyStack {
        Queue<Integer> q = new LinkedList<>();
        int top_elem = 0;

        /**
         * 添加元素到棧頂
         */
        public void push(int x) {
            // x 是隊列的隊尾，是棧的棧頂
            q.offer(x);
            top_elem = x;
        }

        /**
         * 返回棧頂元素
         */
        public int top() {
            return top_elem;
        }

        /**
         * 刪除棧頂的元素並返回
         */
        public int pop() {
            int size = q.size();
            
            // 留下隊尾 2 個元素
            while (size > 2) {
                q.offer(q.poll());
                size--;
            }
            // 記錄新的隊尾元素
            top_elem = q.peek();
            q.offer(q.poll());

            // 刪除之前的隊尾元素
            return q.poll();
        }

        /**
         * 判斷棧是否為空
         */
        public boolean empty() {
            return q.isEmpty();
        }
    }

    class MyStack2 {
        Queue<Integer> queue;

        public MyStack2() {
            this.queue = new LinkedList<>();
        }

        public void push(int x) {
            Queue<Integer> temp = new LinkedList<>();
            temp.add(x);
            temp.addAll(this.queue);  // java語言特性，一般是要使用while來操作
            this.queue = temp;
        }

        public int pop() {
            return this.queue.poll();
        }

        public int top() {
            return this.queue.peek();
        }

        public boolean empty() {
            return this.queue.size() == 0;
        }
    }

}
