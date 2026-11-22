package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;
import java.util.Map;

public class CountStableSubarrays {

    // https://leetcode.cn/problems/stable-subarrays-with-equal-boundary-and-interior-sum/solutions/3815641/qian-zhui-he-yu-ha-xi-biao-shi-zi-bian-x-d6vf/
    public long countStableSubarrays(int[] capacity) {
        Map<Pair, Integer> cnt = new HashMap<>();
        long sum = capacity[0]; // 前綴和
        long ans = 0;
        for (int r = 1; r < capacity.length; r++) {
            ans += cnt.getOrDefault(new Pair(capacity[r], sum), 0);
            cnt.merge(new Pair(capacity[r - 1], capacity[r - 1] + sum), 1, Integer::sum);
            sum += capacity[r];
        }
        return ans;
    }

    private record Pair(int x, long s) {
    }
}
