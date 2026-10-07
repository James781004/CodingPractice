package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.ArrayList;
import java.util.List;

public class MinimumMountainRemovals {

    // https://leetcode.cn/problems/minimum-number-of-removals-to-make-mountain-array/solutions/2575527/qian-hou-zhui-fen-jie-zui-chang-di-zeng-9vowl/
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] suf = new int[n];
        List<Integer> g = new ArrayList<>();

        // 從右往左遍歷 nums，就相當於是在求最長嚴格遞增子序列，可以使用 O(nlogn) 的做法解決
        // 遍歷到 nums[i] 時，二分下標加一就是此時 suf[i] 的值
        for (int i = n - 1; i > 0; i--) {
            int x = nums[i];
            int j = lowerBound(g, x);
            if (j == g.size()) {
                g.add(x);
            } else {
                g.set(j, x);
            }
            suf[i] = j + 1; // 從 nums[i] 開始的最長嚴格遞減子序列的長度
        }

        int mx = 0;
        g.clear();
        for (int i = 0; i < n - 1; i++) {
            int x = nums[i];
            int j = lowerBound(g, x);
            if (j == g.size()) {
                g.add(x);
            } else {
                g.set(j, x);
            }
            int pre = j + 1; // 在 nums[i] 結束的最長嚴格遞增子序列的長度
            if (pre >= 2 && suf[i] >= 2) {
                mx = Math.max(mx, pre + suf[i] - 1); // 減去重復的 nums[i]
            }
        }
        return n - mx;
    }

    private int lowerBound(List<Integer> g, int target) {
        int left = -1, right = g.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = (left + right) >>> 1;
            if (g.get(mid) < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right; // 或者 left+1
    }

}
