package GuChengAlgorithm.Ch02_BasicAlgorithm.TwoPointers;

public class Q04_RainWater {
    // https://docs.google.com/presentation/d/1G_A3upxVNg_LHMpS3GeTRVQE9JZli_Kbt5gnvE6WhsU/edit#slide=id.g132d3712cdb_0_3
    public int trap(int[] height) {
        // time : O(n)
        // space : O(1)
        if (height.length == 0) return 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int ans = 0;
        while (left < right) {
            if (height[left] > leftMax) leftMax = height[left];
            if (height[right] > rightMax) rightMax = height[right];
            if (leftMax < rightMax) {
                ans += Math.max(0, leftMax - height[left]);
                left++;
            } else {
                ans += Math.max(0, rightMax - height[right]);
                right--;
            }
        }
        return ans;
    }
}
