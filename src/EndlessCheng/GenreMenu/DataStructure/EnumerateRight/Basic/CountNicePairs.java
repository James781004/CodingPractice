package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class CountNicePairs {

    // https://leetcode.cn/problems/count-nice-pairs-in-an-array/solutions/2065726/dao-ge-shua-ti-by-lcfgrn-vqfl/
    public int countNicePairs(int[] nums) {
        int mod = (int) 1e9 + 7;
        long ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : nums) {
            int a = x, b = 0;
            while (x != 0) {
                b = b * 10 + x % 10;
                x /= 10;
            }
            ans += map.getOrDefault(a - b, 0);
            map.put(a - b, map.getOrDefault(a - b, 0) + 1);
        }
        return (int) (ans % mod);
    }

}
