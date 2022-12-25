package GuChengAlgorithm.Ch02_BasicAlgorithm.TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q02_ThreeSum {
    // https://docs.google.com/presentation/d/1G_A3upxVNg_LHMpS3GeTRVQE9JZli_Kbt5gnvE6WhsU/edit#slide=id.g132d75113e7_0_31
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        // sort array 鎖定一個開頭，剩下部份頭尾雙指針往中心暴力嘗試
        for (int i = 0; i < nums.length - 2; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) continue;  // 跳過重複部份
            int start = i + 1, end = nums.length - 1;
            while (start < end) {
                int curSum = nums[i] + nums[start] + nums[end];
                if (curSum == 0) {
                    result.add(new ArrayList<Integer>(Arrays.asList(nums[i], nums[start], nums[end])));
                    start++;
                    end--;
                    while (start < end && nums[start] == nums[start - 1]) start++;
                    while (start < end && nums[end] == nums[end + 1]) end--;
                } else if (curSum < 0) {
                    start++;
                } else {
                    end--;
                }
            }
        }
        return result;
    }
}
