package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;
import java.util.Deque;

public class NumberOfSubarrays {

    // https://leetcode.cn/problems/find-the-number-of-subarrays-where-boundary-elements-are-maximum/solutions/2738894/on-dan-diao-zhan-pythonjavacgo-by-endles-y00d/
    public long numberOfSubarrays(int[] nums) {
        long ans = nums.length; // 初始化答案等於 n，因為每個元素可以單獨組成一個長為 1 的子數組，滿足題目要求。
        Deque<int[]> st = new ArrayDeque<>(); // 維護一個底大頂小的單調棧，記錄元素及其出現次數。
        st.push(new int[]{Integer.MAX_VALUE, 0}); // 無窮大哨兵
        for (int x : nums) { // 從左到右遍歷 nums
            while (x > st.peek()[0]) { // 只要 x=nums[i] 大於棧頂，就把棧頂出棧
                st.pop();
            }
            if (x == st.peek()[0]) {
                // 如果 x 等於棧頂，設棧頂記錄的出現次數為 cnt，
                // 那麼 x 可以和左邊 cnt 個 x 組成 cnt 個滿足要求的子數組，
                // 把答案增加 cnt，然後把 cnt 加一
                ans += st.peek()[1]++;
            } else {
                st.push(new int[]{x, 1}); // 如果 x 小於棧頂，把 x 及其出現次數 1 入棧
            }
        }
        return ans;
    }


}
