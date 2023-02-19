package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.ArrayList;
import java.util.List;

public class Q11_Bit {
    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g110eabfe94d_2_9
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int totalNumber = 1 << nums.length;
        for (int mask = 0; mask < totalNumber; mask++) {
            List<Integer> level = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {  // 按照mask把每一位取出
                if ((mask & (1 << j)) != 0) level.add(nums[j]);
                res.add(level);
            }
        }
        return res;
    }
}
