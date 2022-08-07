package PointToOffer.TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q57_1_FindNumbersWithSum {
    public List<Integer> FindNumbersWithSum(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int cur = nums[i] + nums[j];
            if (cur == target)
                return new ArrayList<>(Arrays.asList(nums[i], nums[j]));
            if (cur < target)
                i++;
            else
                j--;
        }
        return new ArrayList<>();
    }
}
