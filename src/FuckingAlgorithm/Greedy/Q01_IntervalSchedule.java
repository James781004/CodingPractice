package FuckingAlgorithm.Greedy;

import java.util.Arrays;
import java.util.Comparator;

public class Q01_IntervalSchedule {
    // interval 模板
    public int intervalSchedule(int[][] intvs) {
        if (intvs.length == 0) return 0;
        Arrays.sort(intvs, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[1] - b[1];
            }
        });

        // 至少有一個區間不相交
        int count = 1;
        // 排序後，第一個區間就是 x
        int x_end = intvs[0][1];
        for (int[] interval : intvs) {
            int start = interval[0];
            if (start >= x_end) {
                // 找到下一個選擇的區間了
                count++;
                x_end = interval[1];
            }
        }

        return count;
    }

//    https://leetcode.cn/problems/non-overlapping-intervals/
//    435. 無重疊區間
//    給定一個區間的集合 intervals ，其中 intervals[i] = [starti, endi] 。返回 需要移除區間的最小數量，使剩余區間互不重疊 。

    public int eraseOverlapIntervals(int[][] intervals) {
        int n = intervals.length;
        return n - intervalSchedule(intervals);
    }


//    https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/
//    452. 用最少數量的箭引爆氣球
//    有一些球形氣球貼在一堵用 XY 平面表示的牆面上。牆面上的氣球記錄在整數數組 points ，其中points[i] = [xstart, xend] 表示水平直徑在 xstart 和 xend之間的氣球。你不知道氣球的確切 y 坐標。
//
//    一支弓箭可以沿著 x 軸從不同點 完全垂直 地射出。在坐標 x 處射出一支箭，若有一個氣球的直徑的開始和結束坐標為 xstart，xend， 且滿足  xstart ≤ x ≤ xend，則該氣球會被 引爆 。可以射出的弓箭的數量 沒有限制 。 弓箭一旦被射出之後，可以無限地前進。
//
//    給你一個數組 points ，返回引爆所有氣球所必須射出的 最小 弓箭數 。

    public int findMinArrowShots(int[][] intvs) {
        if (intvs.length == 0) return 0;
        // 按 end 升序排序
        Arrays.sort(intvs, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[1] - b[1];
            }
        });
        // 至少有一個區間不相交
        int count = 1;
        // 排序後，第一個區間就是 x
        int x_end = intvs[0][1];
        for (int[] interval : intvs) {
            int start = interval[0];
            // 把 >= 改成 > 就行了
            if (start > x_end) {
                count++;
                x_end = interval[1];
            }
        }
        return count;
    }
}
