package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class CountValidSelections {

    // https://leetcode.cn/problems/make-array-elements-equal-to-zero/solutions/2991515/on-qian-hou-zhui-fen-jie-jian-ji-xie-fa-an2kt/
    public int countValidSelections(int[] nums) {
        int total = 0;
        for (int x : nums) {
            total += x;
        }

        int ans = 0;
        int pre = 0;
        for (int x : nums) {
            if (x > 0) {
                pre += x;
            } else if (pre * 2 == total) {
                ans += 2;
            } else if (Math.abs(pre * 2 - total) == 1) {
                ans++;
            }
        }
        return ans;
    }

}
