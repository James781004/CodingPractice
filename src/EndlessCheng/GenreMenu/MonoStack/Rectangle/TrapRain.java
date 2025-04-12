package EndlessCheng.GenreMenu.MonoStack.Rectangle;

import java.util.ArrayDeque;
import java.util.Deque;

public class TrapRain {

    // https://leetcode.cn/problems/trapping-rain-water/solutions/1974340/zuo-liao-nbian-huan-bu-hui-yi-ge-shi-pin-ukwm/
    public int trap(int[] height) {
        int ans = 0;
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) { // 找上一個更大元素，在找的過程中填坑
            while (!st.isEmpty() && height[i] >= height[st.peek()]) {
                int bottomH = height[st.pop()];
                if (st.isEmpty()) {
                    break;
                }
                int left = st.peek();
                int dh = Math.min(height[left], height[i]) - bottomH; // 面積的高
                ans += dh * (i - left - 1);
            }
            st.push(i);
        }
        return ans;
    }


    public int trap2(int[] height) {
        int ans = 0;
        int left = 0;
        int right = height.length - 1;
        int preMax = 0; // 前綴最大值，隨著左指針 left 的移動而更新
        int sufMax = 0; // 後綴最大值，隨著右指針 right 的移動而更新
        while (left < right) {
            preMax = Math.max(preMax, height[left]);
            sufMax = Math.max(sufMax, height[right]);
            ans += preMax < sufMax ? preMax - height[left++] : sufMax - height[right--];
        }
        return ans;
    }


}
