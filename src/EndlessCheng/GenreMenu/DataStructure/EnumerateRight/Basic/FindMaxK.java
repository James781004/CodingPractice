package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashSet;

public class FindMaxK {

    // https://leetcode.cn/problems/largest-positive-integer-that-exists-with-its-negative/solutions/1895719/by-endlesscheng-jjtb/
    public int findMaxK(int[] nums) {
        int ans = -1;
        var s = new HashSet<Integer>();
        for (int x : nums) {
            if (s.contains(-x))
                ans = Math.max(ans, Math.abs(x));
            s.add(x);
        }
        return ans;
    }

}
