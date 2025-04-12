package EndlessCheng.GenreMenu.MonoStack.Rectangle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MaximalRectangle {

    // https://leetcode.cn/problems/maximal-rectangle/solutions/1774721/by-burling-lucky-gqg0/
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        // 存放每行高度
        List<int[]> allHeights = new ArrayList<>();
        for (int i = m - 1; i >= 0; i--) {
            int[] heights = new int[n + 2]; // 在heights數組前後增加兩個哨兵 用來清零單調遞增棧裡的元素
            for (int j = 0; j < n; j++) {
                int k = i;
                while (k >= 0 && matrix[k][j] == '1') {
                    heights[j + 1]++;
                    k--;
                }
            }
            allHeights.add(heights);
        }

        // 84 題的變形
        int res = 0;
        for (int[] heights : allHeights) {
            Deque<Integer> stack = new ArrayDeque<>();  // 單調遞增棧 注意棧存的時下標
            for (int i = 0; i < heights.length; i++) {
                // 當前元素對應的高度小於棧頂元素對應的高度時
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    int cur = stack.pop(); // 出棧
                    int val = heights[cur] * (i - stack.peek() - 1);  // 計算面積 並更新最大面積
                    res = Math.max(res, val); // 高乘寬
                }
                stack.push(i); // 當前下標加入棧
            }
        }
        return res;
    }


}
