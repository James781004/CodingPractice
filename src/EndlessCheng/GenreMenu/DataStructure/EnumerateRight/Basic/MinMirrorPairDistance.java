package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class MinMirrorPairDistance {

    // https://leetcode.cn/problems/minimum-absolute-distance-between-mirror-pairs/solutions/3845358/mei-ju-you-wei-hu-zuo-pythonjavacgo-by-e-wlzl/
    public int minMirrorPairDistance(int[] nums) {
        int n = nums.length;
        int ans = n;
        Map<Integer, Integer> lastIndex = new HashMap<>(n, 1); // 預分配空間

        for (int j = 0; j < n; j++) {
            int x = nums[j];
            Integer i = lastIndex.get(x);
            if (i != null) {
                ans = Math.min(ans, j - i);
            }

            // 計算 reverse(x)，不用字符串
            int rev = 0;
            for (; x > 0; x /= 10) {
                rev = rev * 10 + x % 10;
            }
            lastIndex.put(rev, j);
        }

        return ans < n ? ans : -1;
    }

}
