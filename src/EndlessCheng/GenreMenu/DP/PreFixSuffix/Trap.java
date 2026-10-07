package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.ArrayDeque;
import java.util.Deque;

public class Trap {

    // https://leetcode.cn/problems/trapping-rain-water/solutions/1974340/zuo-liao-nbian-huan-bu-hui-yi-ge-shi-pin-ukwm/
    public int trap(int[] height) {
        int n = height.length;
        int[] preMax = new int[n]; // preMax[i] 表示從 height[0] 到 height[i] 的最大值
        preMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            preMax[i] = Math.max(preMax[i - 1], height[i]);
        }

        int[] sufMax = new int[n]; // sufMax[i] 表示從 height[i] 到 height[n-1] 的最大值
        sufMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            sufMax[i] = Math.max(sufMax[i + 1], height[i]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.min(preMax[i], sufMax[i]) - height[i]; // 累加每個水桶能接多少水
        }
        return ans;
    }

    public int trap2(int[] height) {
        int ans = 0;
        int preMax = 0; // 前綴最大值，隨著左指針 left 的移動而更新
        int sufMax = 0; // 後綴最大值，隨著右指針 right 的移動而更新
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            preMax = Math.max(preMax, height[left]);
            sufMax = Math.max(sufMax, height[right]);
            if (preMax < sufMax) { // 可以確定 left 處的接水量
                ans += preMax - height[left];
                left++; // 搞定了 left，現在問題縮小到 [left+1, right]
            } else { // 可以確定 right 處的接水量
                ans += sufMax - height[right];
                right--; // 搞定了 right，現在問題縮小到 [left, right-1]
            }
        }

        return ans;
    }

    public int trap3(int[] height) {
        int ans = 0;
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            int h = height[i];
            while (!st.isEmpty() && height[st.peek()] <= h) {
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
}

