package Grind.Ch01_Array;

import java.util.Arrays;
import java.util.Comparator;

public class Q23_NonOverlappingIntervals {
    // https://leetcode.com/problems/non-overlapping-intervals/
    // 思路：區間總數減掉互不相交的區間數
    public int eraseOverlapIntervals(int[][] intervals) {
        int n = intervals.length;
        return n - intervalSchedule(intervals);
    }

    // 區間調度算法，算出 intvs 中最多有幾個互不相交的區間
    int intervalSchedule(int[][] intvs) {
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
            if (start >= x_end) {
                // 如果當前區間 start 大於前一個區間 end，就找到下一個選擇的區間了
                count++;
                // 更新區間 end
                x_end = interval[1];
            }
        }
        return count;
    }


    // https://leetcode.cn/problems/non-overlapping-intervals/solutions/541919/435-wu-zhong-die-qu-jian-tan-xin-jing-di-qze0/
    // 按左邊排序，不管右邊順序。相交的時候取最小的右邊。
    public int eraseOverlapIntervals2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            return Integer.compare(a[0], b[0]);
        });
        int remove = 0;
        int pre = intervals[0][1]; // 記錄區間分割點
        for (int i = 1; i < intervals.length; i++) {
            if (pre > intervals[i][0]) { // 重疊情況
                remove++;
                pre = Math.min(pre, intervals[i][1]);
            } else pre = intervals[i][1]; // 無重疊的情況
        }
        return remove;
    }
}
