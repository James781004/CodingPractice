package EndlessCheng.Basic.TwoPointers;

public class maxArea {

    // https://leetcode.cn/problems/container-with-most-water/solutions/1974355/by-endlesscheng-f0xz/
    public int maxArea(int[] height) {
        int ans = 0, left = 0, right = height.length - 1;
        while (left < right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            ans = Math.max(ans, area);
            if (height[left] < height[right]) ++left;
            else --right;
        }
        return ans;
    }
}
