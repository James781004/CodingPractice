package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;
import java.util.Map;

public class FindMaxLength {

    // https://leetcode.cn/problems/contiguous-array/solutions/3805089/shi-zi-bian-xing-mei-ju-you-wei-hu-zuo-p-x9q2/
    public int findMaxLength(int[] nums) {
        // 前綴和數組的首項 0 相當於在 -1 下標
        Map<Integer, Integer> pos = new HashMap<>();
        pos.put(0, -1);
        int ans = 0, s = 0;
        for (int i = 0; i < nums.length; i++) {
            s += nums[i] == 0 ? -1 : 1;
            if (pos.containsKey(s)) {
                ans = Math.max(ans, i - pos.get(s));
            } else {
                pos.put(s, i);
            }
        }
        return ans;
    }

}
