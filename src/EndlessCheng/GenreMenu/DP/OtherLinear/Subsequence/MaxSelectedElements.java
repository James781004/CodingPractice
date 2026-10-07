package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaxSelectedElements {

    // https://leetcode.cn/problems/maximize-consecutive-elements-in-an-array-after-modification/solutions/2643723/ben-ti-zui-jian-dan-xie-fa-pythonjavacgo-kcc6/
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> f = new HashMap<>();
        for (int x : nums) {
            f.put(x + 1, f.getOrDefault(x, 0) + 1);
            f.put(x, f.getOrDefault(x - 1, 0) + 1);
        }
        int ans = 0;
        for (int res : f.values()) {
            ans = Math.max(ans, res);
        }
        return ans;
    }


}
