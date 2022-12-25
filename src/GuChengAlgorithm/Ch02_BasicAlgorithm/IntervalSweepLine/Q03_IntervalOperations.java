package GuChengAlgorithm.Ch02_BasicAlgorithm.IntervalSweepLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q03_IntervalOperations {
    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g885522199d_0_185
    // Lc 56
    public int[][] merge(int[][] intervals) {
        List<int[]> res = new ArrayList<>();
        if (intervals == null || intervals.length == 0) return new int[0][];
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[] cur = intervals[0];
        for (int[] next : intervals) {
            if (cur[1] >= next[0]) {
                cur[1] = Math.max(cur[1], next[1]);
            } else {
                res.add(cur);
                cur = next;
            }
        }
        res.add(cur);
        return res.toArray(new int[0][]);
    }


    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g8883765d9c_0_0
    // LC 57
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        for (int[] cur : intervals) {
            if (newInterval == null || cur[1] < newInterval[0]) { // 太早，沒到插入點
                res.add(cur);
            } else if (cur[0] > newInterval[1]) {  // 時間到了，先插入newInterval後插入cur
                res.addAll(Arrays.asList(newInterval, cur));
                newInterval = null; // 插入newInterval完成後設定成null
            } else {  // overlap 狀況就 merge intervals
                newInterval[0] = Math.min(newInterval[0], cur[0]);
                newInterval[1] = Math.max(newInterval[1], cur[1]);
            }
        }
        if (newInterval != null) res.add(newInterval);
        return res.toArray(new int[res.size()][]);
    }


    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g8883765d9c_0_6
    // LC 1272
    public List<List<Integer>> remove(int[][] intervals, int[] toBoRemoved) {
        List<List<Integer>> res = new ArrayList<>();
        for (int[] i : intervals) {
            if (i[1] <= toBoRemoved[0] || i[0] >= toBoRemoved[1]) {  // no overlaps
                res.add(Arrays.asList(i[0], i[1]));
            } else {  // overlap 狀況就移除重合部份
                if (i[0] < toBoRemoved[0]) {  // left end extra，留下左邊多出來的部份
                    res.add(Arrays.asList(i[0], toBoRemoved[0]));
                }

                if (i[1] > toBoRemoved[1]) { // right end extra，留下右邊多出來的部份
                    res.add(Arrays.asList(toBoRemoved[1], i[1]));
                }
            }
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g8883765d9c_0_24
    // LC 435
    public int eraseOverlap(int[][] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int count = 0, end = Integer.MIN_VALUE;
        for (int[] cur : intervals) {
            if (end <= cur[0]) end = cur[1]; // end小於等於cur start表示沒有交集(no overlaps)，不用移除
            else count++;
        }
        return count;
    }


    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g8883765d9c_0_61
    // LC 1288
    public int removeCovered(int[][] intervals) {
        // Sort by start time increasing, end time decreasing
        // 保证只有index小的可以覆盖index大的
        Arrays.sort(intervals, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));
        int count = 0, cur = 0;
        for (int[] a : intervals) {
            if (cur < a[1]) {
                cur = a[1];
                count++;
            }
        }
        return count;
    }

}
