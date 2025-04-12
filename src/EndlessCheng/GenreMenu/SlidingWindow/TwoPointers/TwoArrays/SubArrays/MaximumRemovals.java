package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

import java.util.HashSet;
import java.util.Set;

public class MaximumRemovals {

    // https://leetcode.cn/problems/maximum-number-of-removable-characters/submissions/452419848/
    public int maximumRemovals(String s, String p, int[] removable) {
        int left = 0, right = removable.length;

        // 始終保持答案位於二分區間內，二分結束條件對應的值恰好在答案所處的位置。
        // 對於可能無解的情況，只要判斷二分結束後的 left 或者 right 是否滿足題意即可。
        while (left < right) {
            int mid = (left + right + 1) >> 1;
            if (check(s, p, removable, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    // 判斷p是否是s的子串，且不能包含被刪除的元素
    private boolean check(String s, String p, int[] removable, int mid) {
        int m = s.length(), n = p.length(), i = 0, j = 0;

        // 目標是返回可以找出的最多前 k 個下標，這邊嘗試先取到 mid，並加入排除 set 中
        Set<Integer> ids = new HashSet<>();
        for (int k = 0; k < mid; ++k) {
            ids.add(removable[k]);
        }

        while (i < m && j < n) {
            // 發現當前兩指針的字符匹配，並且不在 set 中，移動 j 指針
            if (!ids.contains(i) && s.charAt(i) == p.charAt(j)) {
                ++j;
            }
            ++i;
        }

        // 觀察 p 是否全部匹配完畢
        return j == n;
    }

}
