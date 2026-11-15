package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class MaxAbsoluteSum {

    // https://leetcode.cn/problems/maximum-absolute-sum-of-any-subarray/solutions/2377930/liang-chong-fang-fa-dong-tai-gui-hua-qia-dczr/
    public int maxAbsoluteSum(int[] nums) {
        int s = 0, mx = 0, mn = 0;
        for (int x : nums) {
            s += x;
            // mx = Math.max(mx, s);
            // mn = Math.min(mn, s);
            if (s > mx) mx = s;
            else if (s < mn) mn = s; // 效率更高的寫法
        }
        return mx - mn;
    }

}
