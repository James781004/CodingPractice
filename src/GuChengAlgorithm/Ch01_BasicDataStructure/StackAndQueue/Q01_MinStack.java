package GuChengAlgorithm.Ch01_BasicDataStructure.StackAndQueue;

import java.util.Stack;

public class Q01_MinStack {

    class MinStack {
        Stack<int[]> stack = new Stack<>(); // [當前加入值, 當前min值]

        public void push(int x) {
            if (stack.isEmpty()) {
                stack.push(new int[]{x, x});
                return;
            }
            int min = stack.peek()[1];
            stack.push(new int[]{x, Math.min(x, min)}); // 同時保存 [當前加入值, 當前min值]
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek()[0];
        }

        public int getMin() {
            return stack.peek()[1];
        }
    }


    class MinStack2 {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> minStack = new Stack<>();

        public void push(int x) {
            stack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
        }

        public void pop() {
            int top = stack.pop();
            if (top == minStack.peek()) minStack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    class MinStack3 {
        Stack<Integer> stack = new Stack<>();
        Stack<int[]> minStack = new Stack<>(); // [min值, 累積數量]

        public void push(int x) {
            stack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()[0]) {
                minStack.push(new int[]{x, 1});
            } else if (x == minStack.peek()[0]) {
                minStack.peek()[1]++;  // min值重複, 累積數量++
            }
        }

        public void pop() {
            int top = stack.pop();
            if (top == minStack.peek()[0]) minStack.peek()[1]--; // 當前頂部排出值就是min值, 累積數量--
            if (minStack.peek()[1] == 0) minStack.pop(); // min值累積數量為0, minStack排出頂部
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek()[0];
        }
    }
}
