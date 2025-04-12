package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangeFreqQuery {

    // https://leetcode.cn/problems/range-frequency-queries/solutions/1113439/tong-ji-wei-zhi-er-fen-wei-zhi-by-endles-8l9u/
    private final Map<Integer, List<Integer>> pos = new HashMap<>();

    public RangeFreqQuery(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            pos.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
    }

    public int query(int left, int right, int value) {
        List<Integer> a = pos.get(value);
        if (a == null) {
            return 0;
        }
        return lowerBound(a, right + 1) - lowerBound(a, left);
    }

    // 開區間寫法
    private int lowerBound(List<Integer> a, int target) {
        // 開區間 (left, right)
        int left = -1;
        int right = a.size();
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // a[left] < target
            // a[right] >= target
            int mid = (left + right) >>> 1;
            if (a.get(mid) < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right; // right 是最小的滿足 a[right] >= target 的下標
    }


}
