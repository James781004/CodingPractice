package GuChengAlgorithm.Ch02_BasicAlgorithm.Greedy;

public class Q02_TwoPointer {
    // https://docs.google.com/presentation/d/18M3cuDjvBlaaMjZ2R5a6pK1pDI_ZCDbBmVniyW1PdEE/edit#slide=id.g1c33e7d9828_0_72
    // 直接greedy雙指針往中間逼近
    public int maxArea(int[] height) {
        int res = 0, left = 0, right = height.length - 1;
        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            int width = right - left;
            res = Math.max(res, minHeight * width);
            if (height[left] <= height[right]) left++;
            else right--;
        }
        return res;
    }
}
