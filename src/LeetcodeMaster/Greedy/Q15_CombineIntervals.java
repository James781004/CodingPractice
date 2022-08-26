package LeetcodeMaster.Greedy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q15_CombineIntervals {
//    56. 合併區間
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0056.%E5%90%88%E5%B9%B6%E5%8C%BA%E9%97%B4.md
//
//    給出一個區間的集合，請合併所有重疊的區間。
//
//    示例 1:
//
//    輸入: intervals = [[1,3],[2,6],[8,10],[15,18]]
//    輸出: [[1,6],[8,10],[15,18]]
//    解釋: 區間 [1,3] 和 [2,6] 重疊, 將它們合並為 [1,6].
//    示例 2:
//
//    輸入: intervals = [[1,4],[4,5]]
//    輸出: [[1,5]]
//    解釋: 區間 [1,4] 和 [4,5] 可被視為重疊區間。


    public int[][] merge(int[][] intervals) {
        List<int[]> res = new LinkedList<>();
        // 按照左邊界排序
        Arrays.sort(intervals, (x, y) -> Integer.compare(x[0], y[0]));
        // initial start 是最小左邊界
        int start = intervals[0][0];
        int rightmostRightBound = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            // 如果當前左邊界大於之前最大右邊界，說明前一個區間已經可以確定了，必須放入結果集
            if (intervals[i][0] > rightmostRightBound) {
                // 加入前一個確定區間，並且更新start以及rightmostRightBound
                res.add(new int[]{start, rightmostRightBound});
                start = intervals[i][0];
                rightmostRightBound = intervals[i][1];
            } else {
                // 重疊狀況，更新最大右邊界
                rightmostRightBound = Math.max(rightmostRightBound, intervals[i][1]);
            }
        }
        res.add(new int[]{start, rightmostRightBound});
        return res.toArray(new int[res.size()][]);
    }


    public int[][] merge2(int[][] intervals) {
        LinkedList<int[]> res = new LinkedList<>();
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[0], o2[0]));
        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= res.getLast()[1]) { // 重疊狀況，更新結果集隊尾的最大右邊界
                int start = res.getLast()[0];
                int end = Math.max(intervals[i][1], res.getLast()[1]);
                res.removeLast();
                res.add(new int[]{start, end});
            } else {
                res.add(intervals[i]); // 如果當前區間左邊界大於之前區間(結果集隊尾)最大右邊界，就先作為一個新的區間加入結果集
            }
        }
        return res.toArray(new int[res.size()][]);
    }
}
