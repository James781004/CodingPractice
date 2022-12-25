package GuChengAlgorithm.Ch02_BasicAlgorithm.MonotonicStack;

import java.util.Stack;

public class Q03_Rectangles {
    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_83
    // 只有高度從低變高的時候我們才需要計算儲水，高度遞減的時候我們不需要管，因為不儲水
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int res = 0, N = height.length;
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int pre = stack.pop();
                if (stack.isEmpty()) break;  // 沒有左牆壁就無法儲水
                int minHeight = Math.min(height[i], height[stack.peek()]);
                res += (minHeight - height[pre]) * (i - stack.peek() - 1);  // 計算長方形面積
            }
            stack.push(i);
        }
        return res;
    }


    public int trapDP(int[] height) {
        if (height.length == 0) return 0;
        int res = 0, N = height.length;
        int[] left = new int[N], right = new int[N];
        left[0] = height[0];
        right[N - 1] = height[N - 1];

        for (int i = 0; i < N; i++) {
            left[i] = Math.max(left[i - 1], height[i]);  // 保存每個相對位置最大左牆
        }

        for (int i = N - 1; i >= 0; i--) {
            right[i] = Math.max(right[i + 1], height[i]);  // 保存每個相對位置最大右牆
        }

        for (int i = 0; i < N; i++) {
            res += Math.min(left[i], right[i]) - height[i];  // 計算長方形面積
        }

        return res;
    }


    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_91
    // 當圖形處在上升期時(height[i] <= height[i + 1])，其實是不用計算面積的，
    // 因為在這種情況下再往前移動一格(i -> i + 1)所能得到的面積必然更大；
    // 當圖形處在下降期時（height[i] > height[i + 1]），就要開始計算當前矩形的面積了，
    // 這個時候暴力窮舉所有stack裡面的高度，因為stack是遞增的，
    // 從最高的高度開始不斷下降，隨著高度的下降，更多的豎條可以加入到大長方形中的面積來，保持所有可以生成的大長方形的最大面積
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;

        // mono stack
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
                int preHeight = heights[stack.pop()];
                int width = i - (stack.isEmpty() ? 0 : stack.peek() + 1);
                res = Math.min(res, preHeight * width);
            }
            stack.push(i);
        }

        // 處理stack剩下的部分
        while (!stack.isEmpty()) {
            int preHeight = heights[stack.pop()];
            int width = heights.length - (stack.isEmpty() ? 0 : stack.peek() + 1);
            res = Math.min(res, preHeight * width);
        }
        return res;
    }


    public int largestRectangleArea2(int[] heights) {
        return calculateArea(heights, 0, heights.length - 1);
    }

    // divide & conquer
    private int calculateArea(int[] heights, int start, int end) {
        if (start > end) return 0;
        int minHeightIndex = start;
        for (int i = start; i <= end; i++) {  // 先找一個最小的height
            if (heights[minHeightIndex] > heights[i]) minHeightIndex = i;
        }

        int cur = heights[minHeightIndex] * (end - start + 1);  // 計算長方形面積
        int left = calculateArea(heights, start, minHeightIndex - 1);  // 左半部
        int right = calculateArea(heights, minHeightIndex + 1, end); // 右半部
        return Math.max(cur, Math.max(left, right));
    }
}
