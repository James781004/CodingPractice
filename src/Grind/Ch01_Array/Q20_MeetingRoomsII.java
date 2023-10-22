package Grind.Ch01_Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Q20_MeetingRoomsII {
    // https://www.cnblogs.com/grandyang/p/5244720.html
    // https://aaronice.gitbook.io/lintcode/sweep-line/meeting-rooms-ii
    public int minMeetingRooms(Interval[] intervals) {
        // 掃描線算法
        List<Point> points = new ArrayList<>(intervals.length * 2);
        for (Interval i : intervals) {
            points.add(new Point(i.start, 1));
            points.add(new Point(i.end, 0)); // 也可以直接設成 -1
        }

        // 需要注意的是如果有兩個 interval 首尾相接，要把結束的那個排在 array 的前面，先把房間騰出來；
        // 否則的話會認為首尾相接的兩個 meeting 需要占 2 個房間，這是錯誤的。
        Comparator<Point> cmp = new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                if (a.time == b.time) {
                    return a.type - b.type;
                } else {
                    return a.time - b.time;
                }
            }
        };
        Collections.sort(points, cmp);

        int maxOverlap = 0;
        int ongoing = 0;
        for (Point p : points) {
            if (p.type == 1) {
                ongoing++;
            } else if (p.type == 0) { // 如果前面把結束的type設為 -1，這邊ongoing直接加上type即可
                ongoing--;
            }
            maxOverlap = Math.max(maxOverlap, ongoing);
        }

        return maxOverlap;
    }

    class Point {
        int type; // start: 1, end: 0
        int time;

        Point(int time, int type) {
            this.time = time;
            this.type = type;
        }
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
