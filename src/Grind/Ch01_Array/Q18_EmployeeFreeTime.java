package Grind.Ch01_Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q18_EmployeeFreeTime {
    // https://aaronice.gitbook.io/lintcode/sweep-line/employee-free-time
    // https://www.cnblogs.com/grandyang/p/8552586.html
    public List<Interval> employeeFreeTime(List<List<Interval>> avails) {
        List<Interval> result = new ArrayList<>();
        List<Interval> timeLine = new ArrayList<>();
        avails.forEach(e -> timeLine.addAll(e)); // 所有的區間排個序，按照起始位置從小到大來排
        Collections.sort(timeLine, ((a, b) -> a.start - b.start));

        Interval temp = timeLine.get(0); // 取出第一個區間
        for (Interval each : timeLine) { // 開始遍歷所有的區間
            if (temp.end < each.start) { // 二者沒有交集，那麼把不相交的部分加入結果result中
                result.add(new Interval(temp.end, each.start));
                temp = each; // 然後把當前區間 each 賦值給 temp
            } else {
                // 如果區間 temp 和區間 each 有交集，那麼我們更新temp 的結束位置為二者中的較大值，
                // 因為按順序遍歷區間的時候，區間t的結束位置是比較的基准，越大越容易和後面的區間進行合並
                temp = temp.end < each.end ? each : temp;
            }
        }
        return result;
    }

    public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }
}
