package Grind.Ch11_Stack;

import java.util.Stack;

public class Q05_TrappingRainWater {
    // https://leetcode.cn/problems/trapping-rain-water/solutions/616404/42-jie-yu-shui-shuang-zhi-zhen-dong-tai-wguic/
    // 雙指針：
    public int trap(int[] height) {
        int length = height.length;
        if (length <= 2) return 0;
        int[] maxLeft = new int[length];
        int[] maxRight = new int[length];

        // 記錄每個柱子左邊柱子最大高度
        maxLeft[0] = height[0];
        for (int i = 1; i < length; i++) maxLeft[i] = Math.max(height[i], maxLeft[i - 1]);

        // 記錄每個柱子右邊柱子最大高度
        maxRight[length - 1] = height[length - 1];
        for (int i = length - 2; i >= 0; i--) maxRight[i] = Math.max(height[i], maxRight[i + 1]);

        // 求和
        int sum = 0;
        for (int i = 0; i < length; i++) {
            int count = Math.min(maxLeft[i], maxRight[i]) - height[i];
            if (count > 0) sum += count;
        }
        return sum;
    }


    // 單調棧法
    public int trap2(int[] height) {
        int size = height.length;

        if (size <= 2) return 0;

        // in the stack, we push the index of array
        // using height[] to access the real height
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);

        int sum = 0;
        for (int index = 1; index < size; index++) {
            int stackTop = stack.peek();
            if (height[index] < height[stackTop]) {
                stack.push(index);
            } else if (height[index] == height[stackTop]) {
                // 因為相等的相鄰牆，左邊一個是不可能存放雨水的，所以pop左邊的index, push當前的index
                stack.pop();
                stack.push(index);
            } else {
                // pop up all lower value
                int heightAtIdx = height[index];
                while (!stack.isEmpty() && (heightAtIdx > height[stackTop])) {
                    int mid = stack.pop();

                    if (!stack.isEmpty()) {
                        int left = stack.peek();

                        int h = Math.min(height[left], height[index]) - height[mid];
                        int w = index - left - 1;
                        int hold = h * w;
                        if (hold > 0) sum += hold;
                        stackTop = stack.peek();
                    }
                }
                stack.push(index);
            }
        }

        return sum;
    }
}
