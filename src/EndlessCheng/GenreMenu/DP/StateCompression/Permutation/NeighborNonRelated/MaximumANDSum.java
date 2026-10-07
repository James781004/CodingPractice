package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

public class MaximumANDSum {

    // https://leetcode.cn/problems/maximum-and-sum-of-array/solutions/1262392/zhuang-tai-ya-suo-dp-by-endlesscheng-5eqn/
    public int maximumANDSum(int[] nums, int numSlots) {
        var ans = 0;
        var f = new int[1 << (numSlots * 2)];
        for (var i = 0; i < f.length; i++) {
            var c = Integer.bitCount(i);
            if (c >= nums.length) continue;
            for (var j = 0; j < numSlots * 2; ++j) {
                if ((i & (1 << j)) == 0) { // 枚舉空籃子 j
                    var s = i | (1 << j);
                    f[s] = Math.max(f[s], f[i] + ((j / 2 + 1) & nums[c]));
                    ans = Math.max(ans, f[s]);
                }
            }
        }
        return ans;
    }


}
