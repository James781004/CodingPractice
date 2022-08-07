package 劍指offer.Arrays;

public class Q3_RepeatNumber {
    // 从右上角或左下角开始找，逐行删除，或者用二分法查找
    public boolean find(int[][] array, int target) {
        if (array == null) {
            return false;
        }

        int row = 0;
        int column = array[0].length - 1;

        while (row < array.length && column >= 0) {
            int cur = array[row][column];
            if (cur == target) return true;
            if (cur > target) {
                column--;
            } else {
                row++;
            }
        }

        return false;
    }


    public int duplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) return nums[i];
                swap(nums, i, nums[i]);
            }
            swap(nums, i, nums[i]);
        }

        return -1;
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

}
