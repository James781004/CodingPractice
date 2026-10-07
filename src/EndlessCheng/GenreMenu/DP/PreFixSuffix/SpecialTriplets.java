package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.HashMap;
import java.util.Map;

public class SpecialTriplets {

    // https://leetcode.cn/problems/count-special-triplets/solutions/3700554/mei-ju-zhong-jian-qian-hou-zhui-fen-jie-5uad8/
    public int specialTriplets(int[] nums) {
        final int MOD = 1_000_000_007;
        Map<Integer, Integer> suf = new HashMap<>();
        for (int x : nums) {
            suf.merge(x, 1, Integer::sum); // suf[x]++
        }

        long ans = 0;
        Map<Integer, Integer> pre = new HashMap<>();
        for (int x : nums) { // x = nums[j]
            suf.merge(x, -1, Integer::sum); // suf[x]-- // 撤銷
            // 現在 pre 中的是 [0,j-1]，suf 中的是 [j+1,n-1]
            ans += (long) pre.getOrDefault(x * 2, 0) * suf.getOrDefault(x * 2, 0);
            pre.merge(x, 1, Integer::sum); // pre[x]++
        }
        return (int) (ans % MOD);
    }

}
