package Grind.Ch01_Array;

public class Q15_RotateArray {
    // https://leetcode.com/problems/rotate-array/
    // https://leetcode.cn/problems/rotate-array/solutions/683855/shu-zu-fan-zhuan-xuan-zhuan-shu-zu-by-de-5937/
    public void rotate(int[] nums, int k) {
        // Ensure k is within array bounds
        k %= nums.length;
        // Reverse entire array
        swap(nums, 0, nums.length - 1);
        // Reverse first k elements
        swap(nums, 0, k - 1);
        // Reverse remaining elements
        swap(nums, k, nums.length - 1);
    }

    private void swap(int[] nums, int l, int r) {
        while (l < r) {
            int t = nums[l];
            nums[l] = nums[r];
            nums[r] = t;
            l++;
            r--;
        }
    }
}
