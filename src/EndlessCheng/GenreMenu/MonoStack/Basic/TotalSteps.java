package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;

public class TotalSteps {

    // https://leetcode.cn/problems/steps-to-make-array-non-decreasing/solutions/1524614/by-endlesscheng-s2yc/
    public int totalSteps(int[] nums) {
        var ans = 0;
        var st = new ArrayDeque<int[]>();
        for (var num : nums) {
            var maxT = 0;
            while (!st.isEmpty() && st.peek()[0] <= num)
                maxT = Math.max(maxT, st.pop()[1]);
            maxT = st.isEmpty() ? 0 : maxT + 1;
            ans = Math.max(ans, maxT);
            st.push(new int[]{num, maxT});
        }
        return ans;
    }


}
