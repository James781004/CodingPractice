package Grind.Ch01_Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q04_3Sum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) return res; // border cases
        int len = nums.length;
        Arrays.sort(nums);

        for (int i = 0; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // avoid repeat

            // 2 pointers
            int begin = i + 1;
            int end = len - 1;

            while (begin < end) {
                int sum = nums[i] + nums[begin] + nums[end];
                if (sum == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[begin]);
                    list.add(nums[end]);
                    res.add(list);
                    begin++;
                    end--;

                    // avoid repeat again!!!!
                    while (begin < end && nums[begin] == nums[begin - 1]) begin++;
                    while (begin < end && nums[end] == nums[end + 1]) end--;
                } else if (sum > 0) {
                    end--;
                } else {
                    begin++;
                }
            }
        }

        return res;
    }
}
