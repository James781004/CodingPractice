package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.ArrayList;
import java.util.List;

public class IncreasingTriplet {

    // https://leetcode.cn/problems/increasing-triplet-subsequence/solutions/3844143/tong-yong-jie-fa-ba-3-gai-cheng-k-ye-nen-t8si/
    public boolean increasingTriplet(int[] nums) {
        List<Integer> g = new ArrayList<>();
        for (int x : nums) {
            int j = lowerBound(g, x);
            if (j == 2) { // LIS 長度至少是 3
                return true;
            }
            if (j == g.size()) { // >=x 的 g[j] 不存在
                g.add(x);
            } else {
                g.set(j, x);
            }
        }
        return false;
    }

    // 開區間寫法
    private int lowerBound(List<Integer> g, int target) {
        int left = -1, right = g.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (g.get(mid) < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right; // 或者 left+1
    }

}
