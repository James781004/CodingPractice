package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q07_MonotonicStack {
    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g108bbbbe68c_1_1
    class DailyTemperature {
        // 反向模板，stack裡面儲存下標以利後續計算距離
        public int[] dailyTemperature(int[] tmp) {
            int n = tmp.length, res[] = new int[n];
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = n - 1; i >= 0; i--) {
                while (!stack.isEmpty() && tmp[i] >= tmp[stack.peek()]) stack.pop();
                res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
                stack.push(i);
            }
            return res;
        }

        // 正向模板
        public int[] dailyTemperature2(int[] temperatures) {
            int n = temperatures.length;
            int[] res = new int[n];
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                int cur = temperatures[i];
                while (!stack.isEmpty() && temperatures[stack.peek()] < cur) {
                    int pre = stack.pop();
                    res[pre] = cur - pre;
                }
                stack.push(cur);
            }
            return res;
        }


        public int[] dailyTemperature3(int[] temperatures) {
            int n = temperatures.length;
            int[] res = new int[n];
            int hottest = 0;
            for (int i = n - 1; i >= 0; i--) {
                int cur = temperatures[i];
                if (cur >= hottest) {
                    hottest = cur;
                    continue;
                }
                int days = 1;
                while (temperatures[cur + days] <= cur) {
                    days += res[cur + days];
                }
                res[cur] = days;
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g108bbbbe68c_1_28
    class TrappingRainWater {
        public int trap(int[] height) {
            Deque<Integer> stack = new ArrayDeque<>();
            int res = 0, N = height.length;
            for (int i = 0; i < N; i++) {
                while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                    int pre = stack.pop();  // 湖底高度
                    if (stack.isEmpty()) break;  // 沒左牆了無法儲水
                    int depth = Math.min(height[stack.peek()], height[i]) - height[pre];
                    int width = i - stack.peek() - 1;
                    res += depth * width;
                }
            }
            return res;
        }

        public int trapDP(int[] height) {
            if (height.length == 0) return 0;
            int res = 0, N = height.length;
            int[] left = new int[N], right = new int[N];  // preSum
            left[0] = height[0];
            right[N - 1] = height[N - 1];

            // left side preSum
            for (int i = 1; i < N; i++) {
                left[i] = Math.max(left[i - 1], height[i]);
            }

            // right side preSum
            for (int i = N - 2; i >= 0; i--) {
                right[i] = Math.max(right[i + 1], height[i]);
            }

            for (int i = 0; i < N; i++) {
                res += Math.min(left[i], right[i]) - height[i];
            }

            return res;
        }
    }


    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g108bbbbe68c_1_45
    class LargestRectangleInHistogram {
        public int largestArea(int[] heights) {
            return calculateArea(heights, 0, heights.length - 1);
        }

        private int calculateArea(int[] heights, int start, int end) {
            if (start > end) return 0;
            int minHeightIndex = start;
            for (int i = start; i <= end; i++) {
                if (heights[minHeightIndex] > heights[i]) minHeightIndex = i;
            }
            int cur = heights[minHeightIndex] * (end - start + 1);
            int left = calculateArea(heights, start, minHeightIndex - 1);
            int right = calculateArea(heights, minHeightIndex + 1, end);
            return Math.max(cur, Math.max(left, right));
        }


        public int largestArea2(int[] heights) {
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(-1);  // 初始化先存-1方便進行第一組窗口計算
            int res = 0, N = heights.length;
            for (int i = 0; i < N; i++) {
                while (stack.peek() != -1 && heights[i] <= heights[stack.peek()]) {
                    int preHeight = heights[stack.pop()];
                    int width = i - stack.peek() - 1;
                    res = Math.max(res, preHeight * width);
                }
                stack.push(i);
            }

            while (stack.peek() != -1) {
                int preHeight = heights[stack.pop()];
                int width = N - stack.peek() - 1;
                res = Math.max(res, preHeight * width);
            }

            return res;
        }
    }
}
