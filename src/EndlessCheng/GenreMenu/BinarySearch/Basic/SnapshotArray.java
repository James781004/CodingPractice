package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnapshotArray {

    // https://leetcode.cn/problems/snapshot-array/solutions/2756291/ji-lu-xiu-gai-li-shi-ha-xi-biao-er-fen-c-b1sh/

    // 當前快照編號，初始值為 0
    private int curSnapId;

    // 每個 index 的歷史修改記錄
    private final Map<Integer, List<int[]>> history = new HashMap<>();

    public SnapshotArray(int length) {
    }

    public void set(int index, int val) {
        history.computeIfAbsent(index, k -> new ArrayList<>()).add(new int[]{curSnapId, val});
    }

    public int snap() {
        return curSnapId++;
    }

    public int get(int index, int snapId) {
        if (!history.containsKey(index)) {
            return 0;
        }
        List<int[]> h = history.get(index);
        int j = search(h, snapId);
        return j < 0 ? 0 : h.get(j)[1];
    }

    // 返回最大的下標 i，滿足 h[i][0] <= x
    // 如果不存在則返回 -1
    private int search(List<int[]> h, int x) {
        // 開區間 (left, right)
        int left = -1;
        int right = h.size();
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // h[left][0] <= x
            // h[right][1] > x
            int mid = left + (right - left) / 2;
            if (h.get(mid)[0] <= x) {
                left = mid; // 區間縮小為 (mid, right)
            } else {
                right = mid; // 區間縮小為 (left, mid)
            }
        }
        // 根據循環不變量，此時 h[left][0] <= x 且 h[left+1][0] = h[right][0] > x
        // 所以 left 是最大的滿足 h[left][0] <= x 的下標
        // 如果不存在，則 left 為其初始值 -1
        return left;
    }


}
