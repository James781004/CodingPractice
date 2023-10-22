package Grind.Ch01_Array;

import java.util.Arrays;
import java.util.LinkedList;

public class Q07_MergeIntervals {
    // https://leetcode.com/problems/merge-intervals/
    public int[][] merge(int[][] intervals) {
        LinkedList<int[]> res = new LinkedList<>();
        // 按區間的 start 升序排列
        Arrays.sort(intervals, (a, b) -> {
            return a[0] - b[0];
        });

        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            // res 中最後一個元素的引用
            int[] last = res.getLast();
            if (curr[0] <= last[1]) {
                last[1] = Math.max(last[1], curr[1]);
            } else {
                // 處理下一個待合併區間
                res.add(curr);
            }
        }
        return res.toArray(new int[0][0]);
    }
}
