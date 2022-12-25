package GuChengAlgorithm.Ch02_BasicAlgorithm.DivideAndConquer;

import java.util.Arrays;

public class Q01_MajorityElement {
    // https://docs.google.com/presentation/d/1L0GLS7C6-pRcutYbQ1_2fOII9luGESWyHxovM8pFu5g/edit#slide=id.ga3a9644e84_0_48
    public int majorityElement(int[] nums) {
        return divide(nums, 0, nums.length - 1);
    }

    private int divide(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        int mid = left + (right - left) / 2;
        int leftRes = divide(nums, left, mid);
        int rightRes = divide(nums, mid + 1, right);
        if (leftRes == rightRes) return leftRes;
        int leftCount = conquer(nums, leftRes, left, right);
        int rightCount = conquer(nums, rightRes, left, right);
        return leftCount > rightCount ? leftRes : rightRes;
    }

    private int conquer(int[] nums, int target, int left, int right) {
        int count = 0;
        for (int i = left; i < right; i++) {
            if (nums[i] == target) count++;
        }
        return count;
    }


    // 投票法
    public int majorityElement2(int[] nums) {
        int count = 0, candidate = 0;
        for (int num : nums) {
            if (count == 0) candidate = num;
            count += num == candidate ? 1 : -1;
        }
        return candidate;
    }

    // sort
    public int majorityElement3(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
