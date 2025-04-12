package EndlessCheng.GenreMenu.MonoStack.Rectangle;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumScore {

    // https://leetcode.cn/problems/maximum-score-of-a-good-subarray/solutions/2695415/liang-chong-fang-fa-dan-diao-zhan-shuang-24zl/
    // 本題要計算的分數，和 84. 柱狀圖中最大的矩形 是一樣的，計算的是最大矩形面積。
    // 只不過多了一個約束：矩形必須包含下標 k
    public int maximumScore(int[] nums, int k) {
        int n = nums.length;
        int[] left = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            while (!st.isEmpty() && x <= nums[st.peek()]) {
                st.pop();
            }
            left[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }

        int[] right = new int[n];
        st.clear();
        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i];
            while (!st.isEmpty() && x <= nums[st.peek()]) {
                st.pop();
            }
            right[i] = st.isEmpty() ? n : st.peek();
            st.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int h = nums[i];
            int l = left[i];
            int r = right[i];
            if (l < k && k < r) { // 相比 84 題多了個 if 判斷
                ans = Math.max(ans, h * (r - l - 1));
            }
        }
        return ans;
    }


    // 雙指針
    public int maximumScore2(int[] nums, int k) {
        int n = nums.length;
        int ans = nums[k], minH = nums[k];
        int i = k, j = k;
        for (int t = 0; t < n - 1; t++) { // 循環 n-1 次
            if (j == n - 1 || i > 0 && nums[i - 1] > nums[j + 1]) {
                minH = Math.min(minH, nums[--i]);
            } else {
                minH = Math.min(minH, nums[++j]);
            }
            ans = Math.max(ans, minH * (j - i + 1));
        }
        return ans;
    }


}
