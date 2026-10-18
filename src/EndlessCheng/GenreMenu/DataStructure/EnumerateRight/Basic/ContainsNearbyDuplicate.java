package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class ContainsNearbyDuplicate {

    // https://leetcode.cn/problems/contains-duplicate-ii/solutions/3041742/liang-chong-fang-fa-mei-ju-you-wei-hu-zu-kwjf/
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> last = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (last.containsKey(x) && i - last.get(x) <= k) {
                return true;
            }
            last.put(x, i);
        }
        return false;
    }

}
