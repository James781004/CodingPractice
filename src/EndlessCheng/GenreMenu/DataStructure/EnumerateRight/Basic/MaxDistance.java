package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.List;

public class MaxDistance {

    // https://leetcode.cn/problems/maximum-distance-in-arrays/solutions/3067679/mei-ju-you-wei-hu-zuo-pythonjavaccgojsru-wtgb/
    public int maxDistance(List<List<Integer>> arrays) {
        int ans = 0;
        int mn = Integer.MAX_VALUE / 2; // 防止減法溢出
        int mx = Integer.MIN_VALUE / 2;
        for (List<Integer> a : arrays) {
            int x = a.getFirst();
            int y = a.getLast();
            ans = Math.max(ans, Math.max(y - mn, mx - x));
            mn = Math.min(mn, x);
            mx = Math.max(mx, y);
        }
        return ans;
    }

}
