package Grind.Ch11_Stack;

import java.util.Stack;

public class Q04_MinStack {
    // https://leetcode.cn/problems/min-stack/solutions/42521/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-38/
    class MinStack {
        private Stack<Integer> stack;
        private Stack<Integer> minStack;

        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int x) {
            stack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
        }

        public void pop() {
            if (stack.pop().equals(minStack.peek())) minStack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }


    class MinStack2 {
        int min = Integer.MAX_VALUE;
        Stack<Integer> stack = new Stack<Integer>();

        public void push(int x) {
            //當前值更小
            if (x <= min) {
                //將之前的最小值保存
                stack.push(min);
                //更新最小值
                min = x;
            }
            stack.push(x);
        }

        public void pop() {
            // 如果彈出的值是最小值，那麼將下一個元素更新為最小值
            if (stack.pop() == min) {
                min = stack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }

}
