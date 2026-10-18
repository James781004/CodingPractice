package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class CountBadPairs {

    // https://leetcode.cn/problems/count-number-of-bad-pairs/solutions/1728063/by-endlesscheng-uam3/
    public long countBadPairs(int[] nums) {
        int n = nums.length;
        long ans = (long) n * (n - 1) / 2;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i] - i;
            int c = cnt.getOrDefault(x, 0);
            ans -= c;
            cnt.put(x, c + 1);
        }
        return ans;
    }

}
