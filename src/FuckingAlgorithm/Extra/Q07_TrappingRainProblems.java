package FuckingAlgorithm.Extra;

public class Q07_TrappingRainProblems {
//    https://leetcode.cn/problems/container-with-most-water/

    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int l_max = 0, r_max = 0;

        int res = 0;
        while (left < right) {
            l_max = Math.max(l_max, height[left]);  // 左邊最高的柱子
            r_max = Math.max(r_max, height[right]); // 右邊最高的柱子

            // res += min(l_max, r_max) - height[i]
            // 雙指針技巧，移動較低的一邊
            if (l_max < r_max) {
                res += l_max - height[left];
                left++;
            } else {
                res += r_max - height[right];
                right--;
            }
        }
        return res;
    }


    //    https://leetcode.cn/problems/trapping-rain-water/
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int res = 0;
        while (left < right) {
            // [left, right] 之間的矩形面積
            int cur_area = Math.min(height[left], height[right]) * (right - left);
            res = Math.max(res, cur_area);
            // 雙指針技巧，移動較低的一邊
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }
}
