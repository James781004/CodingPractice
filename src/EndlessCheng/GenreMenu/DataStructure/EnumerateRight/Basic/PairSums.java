package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PairSums {

    // https://leetcode.cn/problems/pairs-with-sum-lcci/solutions/3739660/shu-dui-he-liang-shu-zhi-he-jia-qiang-ba-cml1/
    public List<List<Integer>> pairSums(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int num : nums) {
            int toMap = target - num;
            if (map.containsKey(toMap)) {
                ans.add(List.of(toMap, num));
                map.merge(toMap, -1, (oldVal, delta) -> {
                    int res = oldVal + delta;
                    return res == 0 ? null : res;
                });
            } else {
                map.merge(num, 1, Integer::sum);
            }
        }
        return ans;
    }
}
