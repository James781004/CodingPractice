package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class NextGreaterElementsII {

    // https://leetcode.cn/problems/next-greater-element-ii/solutions/2820363/shi-pin-dan-diao-zhan-de-liang-chong-xie-k236/
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 2 * n - 1; i >= 0; i--) {
            int x = nums[i % n];
            while (!st.isEmpty() && x >= st.peek()) {
                // 由於 x 的出現，棧頂元素永遠不會是左邊元素的「下一個更大元素」
                st.pop();
            }
            if (i < n && !st.isEmpty()) {
                ans[i] = st.peek();
            }
            st.push(x);
        }
        return ans;
    }


    public int[] nextGreaterElements2(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            int x = nums[i % n];
            while (!st.isEmpty() && x > nums[st.peek()]) {
                // x 是 nums[st.peek()] 的下一個更大元素
                // 既然 nums[st.peek()] 已經算出答案，則從棧頂彈出
                ans[st.pop()] = x;
            }
            if (i < n) {
                st.push(i);
            }
        }
        return ans;
    }


}
