package EndlessCheng.Basic.TwoPointers;

public class twoSum {
    // https://leetcode.cn/problems/two-sum-ii-input-array-is-sorted/
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (true) {
            int s = numbers[left] + numbers[right];
            if (s == target) {
                return new int[]{left + 1, right + 1};
            }
            if (s > target) {
                right--;
            } else {
                left++;
            }
        }
    }
}
