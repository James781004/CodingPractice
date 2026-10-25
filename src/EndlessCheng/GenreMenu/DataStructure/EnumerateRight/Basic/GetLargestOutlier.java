package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class GetLargestOutlier {

    // https://leetcode.cn/problems/identify-the-largest-outlier-in-an-array/solutions/3006328/mei-ju-yi-chang-zhi-pythonjavacgo-by-end-wb3g/
    public int getLargestOutlier(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int total = 0;
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum); // cnt[x]++
            total += x;
        }

        int ans = Integer.MIN_VALUE;
        for (int x : nums) {
            cnt.merge(x, -1, Integer::sum);
            if ((total - x) % 2 == 0 && cnt.getOrDefault((total - x) / 2, 0) > 0) {
                ans = Math.max(ans, x);
            }
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }

}
