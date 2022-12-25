package GuChengAlgorithm.Ch02_BasicAlgorithm.Recursive;

public class Q03_DivideAndConquer {
    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_106
    public int findKthLargest(int[] nums, int k) {
        divide(nums, 0, nums.length - 1, k);
        return nums[nums.length - k];
    }

    private void divide(int[] nums, int left, int right, int k) {
        if (left >= right) return;
        int position = conquer(nums, left, right);
        if (position == nums.length - k) return;
        else if (position < nums.length - k) divide(nums, position + 1, right, k);
        else divide(nums, left, position - 1, k);
    }

    private int conquer(int[] nums, int left, int right) {
        int pivot = nums[right], wall = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivot) {
                swap(nums, i, wall);
                wall++;
            }
        }
        swap(nums, wall, right);
        return wall;
    }

    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
}
