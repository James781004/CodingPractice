package Grind.Ch01_Array;

import java.util.Arrays;
import java.util.Comparator;

public class Q12_MeetingRooms {
    // https://aaronice.gitbook.io/lintcode/sweep-line/meeting-rooms
    public boolean canAttendMeetings(Interval[] intervals) {
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval i1, Interval i2) {
                return i1.start - i2.start;
            }
        });
        Interval last = null;
        for (Interval i : intervals) {
            if (last != null && i.start < last.end) {
                return false;
            }
            last = i;
        }
        return true;
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
