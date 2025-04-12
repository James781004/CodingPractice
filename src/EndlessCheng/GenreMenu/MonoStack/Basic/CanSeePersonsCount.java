package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;
import java.util.Deque;

public class CanSeePersonsCount {

    // https://leetcode.cn/problems/number-of-visible-people-in-a-queue/solutions/2591558/dan-diao-zhan-de-ben-zhi-ji-shi-qu-diao-8tp3s/
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && st.peek() < heights[i]) {
                st.pop();
                ans[i]++;
            }
            if (!st.isEmpty()) { // 還可以再看到一個人
                ans[i]++;
            }
            st.push(heights[i]);
        }
        return ans;
    }


}
