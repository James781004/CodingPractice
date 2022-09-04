package LeetcodeMaster.MonoStack;

import java.util.Stack;

public class Q05_LargestRectangleArea {
//    84.柱狀圖中最大的矩形
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0084.%E6%9F%B1%E7%8A%B6%E5%9B%BE%E4%B8%AD%E6%9C%80%E5%A4%A7%E7%9A%84%E7%9F%A9%E5%BD%A2.md
//
//    給定 n 個非負整數，用來表示柱狀圖中各個柱子的高度。每個柱子彼此相鄰，且寬度為 1 。
//
//    求在該柱狀圖中，能夠勾勒出來的矩形的最大面積。


    // 動態規劃
    public int largestRectangleAreaDP(int[] heights) {
        int length = heights.length;
        int[] minLeftIndex = new int[length];

        int[] maxRigthIndex = new int[length];
        // 記錄左邊第一個小於該柱子的下標
        minLeftIndex[0] = -1;
        for (int i = 1; i < length; i++) {
            int t = i - 1;
            // 這里不是用if，而是不斷向右尋找的過程
            while (t >= 0 && heights[t] >= heights[i]) t = minLeftIndex[t];
            minLeftIndex[i] = t;
        }

        // 記錄每個柱子 右邊第一個小於該柱子的下標
        maxRigthIndex[length - 1] = length;
        for (int i = length - 2; i >= 0; i--) {
            int t = i + 1;
            while (t < length && heights[t] >= heights[i]) t = maxRigthIndex[t];
            maxRigthIndex[i] = t;
        }

        // 求和
        int result = 0;
        for (int i = 0; i < length; i++) {
            int sum = heights[i] * (maxRigthIndex[i] - minLeftIndex[i] - 1);
            result = Math.max(result, sum);
        }
        return result;
    }

    // 單調棧
    int largestRectangleAreaStack(int[] heights) {
        Stack<Integer> st = new Stack<Integer>();

        // 數組擴容，在頭和尾各加入一個元素0
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
            } else { // 精簡版本只需要做這塊while就行，上面的if判斷都可以省略
                while (heights[i] < heights[st.peek()]) { // 注意是while
                    int mid = st.pop();
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
}
