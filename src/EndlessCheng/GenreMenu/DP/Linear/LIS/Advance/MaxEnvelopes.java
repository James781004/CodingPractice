package EndlessCheng.GenreMenu.DP.Linear.LIS.Advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxEnvelopes {


    // https://leetcode.cn/problems/russian-doll-envelopes/solutions/3785353/qiao-miao-pai-xu-zhuan-hua-cheng-yi-wei-25mb0/
    public int maxEnvelopes(int[][] envelopes) {
        // 雙關鍵字排序：寬度升序，高度降序
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        // 300. 最長遞增子序列
        List<Integer> g = new ArrayList<>();
        for (int[] e : envelopes) {
            int h = e[1];
            int j = lowerBound(g, h);
            if (j < g.size()) {
                g.set(j, h);
            } else {
                g.add(h);
            }
        }
        return g.size();
    }

    private int lowerBound(List<Integer> g, int target) {
        int left = -1, right = g.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (g.get(mid) >= target) {
                right = mid; // 范圍縮小到 (left, mid)
            } else {
                left = mid; // 范圍縮小到 (mid, right)
            }
        }
        return right; // 或者 left+1
    }


}
