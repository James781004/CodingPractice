package EndlessCheng.GenreMenu.MonoStack.Rectangle;

import java.util.ArrayDeque;
import java.util.Deque;

public class LargestRectangleArea {


    // https://leetcode.cn/problems/largest-rectangle-in-histogram/solutions/2695467/dan-diao-zhan-fu-ti-dan-pythonjavacgojsr-89s7/
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int x = heights[i];
            while (!st.isEmpty() && x <= heights[st.peek()]) {
                st.pop();
            }
            // 在 i 左側的小於 h 的最近元素的下標 left，如果不存在則為 −1
            // 如果結果是-1，可以理解為 i 左側元素全部大於或等於 heights[i]
            left[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }

        int[] right = new int[n];
        st.clear();
        for (int i = n - 1; i >= 0; i--) {
            int x = heights[i];
            while (!st.isEmpty() && x <= heights[st.peek()]) {
                st.pop();
            }
            // 在 i 右側的小於 h 的最近元素的下標 right，如果不存在則為 n
            // 如果結果是 n，可以理解為 i 右側元素全部大於或等於 heights[i]
            right[i] = st.isEmpty() ? n : st.peek();
            st.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, heights[i] * (right[i] - left[i] - 1));
        }
        return ans;
    }


}
