package GuChengAlgorithm.Ch02_BasicAlgorithm.MonotonicStack;

import java.util.Stack;

public class Q00_Monotonic_Stack_Model {
    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_15

    // 三步走：
    // 1. 維持遞減棧(或者遞增棧)
    // 2. 處理結果進result
    // 3. 當前元素入棧
    public int[] monotonicStack(int[] nums) {
        int[] res = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] >= stack.peek()) stack.pop(); // 1. 維持遞減棧(或者遞增棧)
            res[i] = stack.isEmpty() ? -1 : stack.peek(); // 2. 處理結果進result
            stack.push(nums[i]); // 3. 當前元素入棧
        }

        return res;
    }
}
