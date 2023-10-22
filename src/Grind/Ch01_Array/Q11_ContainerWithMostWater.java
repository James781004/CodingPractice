package Grind.Ch01_Array;

public class Q11_ContainerWithMostWater {
    // https://leetcode.com/problems/container-with-most-water/
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
