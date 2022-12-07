package FuckingAlgorithm.Extra;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q02_IntervalProblems {
//    https://www.cnblogs.com/labuladong/p/13975851.html

//    https://leetcode.cn/problems/remove-covered-intervals/
//    1288. 刪除被覆蓋區間
//    給你一個區間列表，請你刪除列表中被其他區間所覆蓋的區間。
//
//    只有當 c <= a 且 b <= d 時，我們才認為區間 [a,b) 被區間 [c,d) 覆蓋。
//
//    在完成所有刪除操作後，請你返回列表中剩余區間的數目。

    public int removeCoveredIntervals(int[][] intervals) {
        // 按照起點升序排列，起點相同時降序排列
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        // 記錄合並區間的起點和終點
        int left = intervals[0][0];
        int right = intervals[0][1];

        int res = 0;
        for (int i = 1; i < intervals.length; i++) {
            int[] intv = intervals[i];
            // 情況一，找到覆蓋區間
            if (left <= intv[0] && right >= intv[1]) {
                res++;
            }
            // 情況二，找到相交區間，合並
            if (right >= intv[0] && right <= intv[1]) {
                right = intv[1];
            }
            // 情況三，完全不相交，更新起點和終點
            if (right < intv[0]) {
                left = intv[0];
                right = intv[1];
            }
        }

        return intervals.length - res;
    }


//    https://leetcode.cn/problems/merge-intervals/
//    56. 合並區間
//    以數組 intervals 表示若干個區間的集合，其中單個區間為 intervals[i] = [starti, endi] 。
//    請你合並所有重疊的區間，並返回 一個不重疊的區間數組，該數組需恰好覆蓋輸入中的所有區間 。

    public int[][] mergeIntervals(int[][] intervals) {
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
                // 處理下一個待合並區間
                res.add(curr);
            }
        }
        return res.toArray(new int[0][0]);
    }


//    https://leetcode.cn/problems/interval-list-intersections/
//    986. 區間列表的交集
//    給定兩個由一些 閉區間 組成的列表，firstList 和 secondList ，
//    其中 firstList[i] = [starti, endi] 而 secondList[j] = [startj, endj] 。
//    每個區間列表都是成對 不相交 的，並且 已經排序 。
//
//    返回這 兩個區間列表的交集 。
//
//    形式上，閉區間 [a, b]（其中 a <= b）表示實數 x 的集合，而 a <= x <= b 。
//
//    兩個閉區間的 交集 是一組實數，要麼為空集，要麼為閉區間。例如，[1, 3] 和 [2, 4] 的交集為 [2, 3] 。

    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> res = new LinkedList<>();
        int i = 0, j = 0;
        while (i < A.length && j < B.length) {
            int a1 = A[i][0], a2 = A[i][1];
            int b1 = B[j][0], b2 = B[j][1];

            if (b2 >= a1 && a2 >= b1) {
                res.add(new int[]{
                        Math.max(a1, b1), Math.min(a2, b2)
                });
            }
            if (b2 < a2) {
                j++;
            } else {
                i++;
            }
        }
        return res.toArray(new int[0][0]);
    }
}
