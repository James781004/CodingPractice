package LeetcodeMaster.Greedy;

import java.util.Arrays;

public class Q13_EraseOverlapIntervals {
//    435. 無重疊區間
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0435.%E6%97%A0%E9%87%8D%E5%8F%A0%E5%8C%BA%E9%97%B4.md
//
//    給定一個區間的集合，找到需要移除區間的最小數量，使剩余區間互不重疊。
//
//    注意: 可以認為區間的終點總是大於它的起點。 區間 [1,2] 和 [2,3] 的邊界相互“接觸”，但沒有相互重疊。
//
//    示例 1:
//
//    輸入: [ [1,2], [2,3], [3,4], [1,3] ]
//    輸出: 1
//    解釋: 移除 [1,3] 後，剩下的區間沒有重疊。
//    示例 2:
//
//    輸入: [ [1,2], [1,2], [1,2] ]
//    輸出: 2
//    解釋: 你需要移除兩個 [1,2] 來使剩下的區間沒有重疊。
//    示例 3:
//
//    輸入: [ [1,2], [2,3] ]
//    輸出: 0
//    解釋: 你不需要移除任何區間，因為它們已經是無重疊的了。


    public int eraseOverlapIntervals(int[][] intervals) {
        // 按照區間右邊界升序排序
        Arrays.sort(intervals, (a, b) -> {
            return a[1] - b[1];
        });

        int count = 0;
        int edge = Integer.MIN_VALUE;
        for (int i = 0; i < intervals.length; i++) {
            // 若上一個區間的右邊界小於或等於當前區間的左邊界，說明無交集
            if (edge <= intervals[i][0]) {
                edge = intervals[i][1];
            } else {
                count++;  // 有相交情況，需要移除的數量+1
            }
        }

        return count;
    }

    // 按左邊排序，不管右邊順序。相交的時候取最小的右邊。
    public int eraseOverlapIntervals2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            return a[0] - b[0];
        });

        int remove = 0;
        int pre = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (pre > intervals[i][0]) {
                remove++; // 前一個右邊界小於目前左邊界，表示有相交情況，需要移除的數量+1
                pre = Math.min(pre, intervals[i][1]); // 更新右邊界，取較小的那一方
            } else {
                pre = intervals[i][1];
            }
        }
        return remove;
    }
}
