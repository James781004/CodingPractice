package Grind.Ch11_Stack;

import java.util.Stack;

public class Q07_LargestRectangleHistogram {
    // https://leetcode.cn/problems/largest-rectangle-in-histogram/solutions/893994/dai-ma-sui-xiang-lu-dai-ni-gao-ding-dan-e3cak/
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> st = new Stack<>();

        // 數組擴容，在頭和尾各加入一個元素
        int[] newHeights = new int[heights.length + 2];
        newHeights[0] = 0;
        newHeights[newHeights.length - 1] = 0;
        for (int index = 0; index < heights.length; index++) {
            newHeights[index + 1] = heights[index];
        }

        heights = newHeights;

        st.push(0);
        int result = 0;
        // 第一個元素已經入棧，從下標1開始
        for (int i = 1; i < heights.length; i++) {
            // 注意heights[i] 是和heights[st.top()] 比較 ，st.top()是下標
            if (heights[i] > heights[st.peek()]) {
                st.push(i);
            } else if (heights[i] == heights[st.peek()]) {
                st.pop(); // 這個可以加，可以不加，效果一樣，思路不同
                st.push(i);
            } else {
                while (heights[i] < heights[st.peek()]) { // 注意是while
                    int mid = st.peek();
                    st.pop();
                    int left = st.peek();
                    int right = i;
                    int w = right - left - 1;
                    int h = heights[mid];
                    result = Math.max(result, w * h);
                }
                st.push(i);
            }
        }
        return result;
    }


    // 雙指針
    public int largestRectangleArea2(int[] heights) {
        int length = heights.length;
        int[] minLeftIndex = new int[length];
        int[] minRightIndex = new int[length];
        // 記錄左邊第一個小於該柱子的下標
        minLeftIndex[0] = -1;
        for (int i = 1; i < length; i++) {
            int t = i - 1;
            // 這裡不是用if，而是不斷向右尋找的過程
            while (t >= 0 && heights[t] >= heights[i]) t = minLeftIndex[t];
            minLeftIndex[i] = t;
        }
        // 記錄每個柱子右邊第一個小於該柱子的下標
        minRightIndex[length - 1] = length;
        for (int i = length - 2; i >= 0; i--) {
            int t = i + 1;
            while (t < length && heights[t] >= heights[i]) t = minRightIndex[t];
            minRightIndex[i] = t;
        }
        // 求和
        int result = 0;
        for (int i = 0; i < length; i++) {
            int sum = heights[i] * (minRightIndex[i] - minLeftIndex[i] - 1);
            result = Math.max(sum, result);
        }
        return result;
    }
}
