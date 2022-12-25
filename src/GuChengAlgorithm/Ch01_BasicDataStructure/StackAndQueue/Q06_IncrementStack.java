package GuChengAlgorithm.Ch01_BasicDataStructure.StackAndQueue;

import java.util.Stack;

public class Q06_IncrementStack {
    //    https://leetcode.com/problems/design-a-stack-with-increment-operation/
//    1381. Design a Stack With Increment Operation
//    Design a stack that supports increment operations on its elements.
//
//    Implement the CustomStack class:
//
//    CustomStack(int maxSize) Initializes the object with maxSize which is the maximum number of elements in the stack.
//    void push(int x) Adds x to the top of the stack if the stack has not reached the maxSize.
//    int pop() Pops and returns the top of the stack or -1 if the stack is empty.
//    void inc(int k, int val) Increments the bottom k elements of the stack by val.
//    If there are less than k elements in the stack, increment all the elements in the stack.

    class IncrementStack {
        int n;
        int[] inc;
        Stack<Integer> stack = new Stack<>();

        public IncrementStack(int k) {
            n = k;
            inc = new int[n];
        }

        public void push(int x) {
            if (stack.size() < n) stack.push(x);
        }

        public int pop() {
            int i = stack.size() - 1;
            if (i < 0) return -1;
            if (i > 0) inc[i - 1] += inc[i]; // 出棧操作後，inc對應位置前移，讓後續的值也能進行相同操作
            int res = stack.pop() + inc[i]; // 加上inc對應位置記錄新增值
            inc[i] = 0; // inc對應位置記錄歸零
            return res;
        }

        public void increment(int k, int val) {
            int i = Math.min(k, stack.size()) - 1;
            if (i >= 0) inc[i] += val;  // 懶加載策略，先在inc對應位置記錄新增值，等到出棧的時候才進行計算
        }
    }
}
