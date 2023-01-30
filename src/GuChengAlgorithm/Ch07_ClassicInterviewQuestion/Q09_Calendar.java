package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Q09_Calendar {
    // https://docs.google.com/presentation/d/1q6BbotySEgtWLHul6UvlJQ2ATXgLAg6w6jjcGiMS1Ao/edit#slide=id.g1837713480b_0_56
    class MyCalendar {
        TreeMap<Integer, Integer> calendar;

        MyCalendar() {
            calendar = new TreeMap<>();
        }

        // prev, next拿到的是离我们最近的两个interval，我们要看自己和周围有没有重合
        public boolean book(int start, int end) {
            Integer prev = calendar.floorKey(start), next = calendar.ceilingKey(start);
            if ((prev == null || calendar.get(prev) < start) &&
                    (next == null || end <= next)) {
                calendar.put(start, end);  // key是开始时间，value是结束时间
                return true;
            }
            return false;
        }
    }


    // https://docs.google.com/presentation/d/1q6BbotySEgtWLHul6UvlJQ2ATXgLAg6w6jjcGiMS1Ao/edit#slide=id.g1837713480b_1_7
    class MyCalendarII {
        TreeMap<Integer, Integer> delta;

        MyCalendarII() {
            delta = new TreeMap<>();
        }

        // TreeMap掃描線
        public boolean book(int start, int end) {
            delta.put(start, delta.getOrDefault(start, 0) + 1);
            delta.put(end, delta.getOrDefault(end, 0) - 1);

            int active = 0;
            for (int d : delta.values()) {
                active += 0;
                if (active >= 3) {
                    delta.put(start, delta.get(start) - 1);
                    delta.put(end, delta.get(end) + 1);
                    if (delta.get(start) == 0) {
                        delta.remove(start);
                    }
                    return false;
                }
            }
            return true;
        }


        // 手動掃描線
        List<int[]> calendar = new ArrayList<>();
        List<int[]> overlaps = new ArrayList<>();

        public boolean book2(int start, int end) {
            for (int[] o : overlaps) {
                if (start < o[1] && end > o[0]) return false;
            }

            for (int[] interval : calendar) {
                if (start < interval[1] && end > interval[0]) {
                    overlaps.add(new int[]{Math.max(start, interval[0]), Math.min(end, interval[1])});
                }
            }
            calendar.add(new int[]{start, end});
            return true;
        }
    }


    // https://docs.google.com/presentation/d/1q6BbotySEgtWLHul6UvlJQ2ATXgLAg6w6jjcGiMS1Ao/edit#slide=id.g1837713480b_2_20
    class MyCalendarIII {
        TreeMap<Integer, Integer> map;

        public MyCalendarIII() {
            map = new TreeMap<>();
        }

        public int book(int start, int end) {
            // TreeMap自動排列key，value就是start+1 或者 end-1，這樣可以判斷任何一個時間段有多少overlap
            map.put(start, map.getOrDefault(start, 0) + 1);
            map.put(end, map.getOrDefault(end, 0) - 1);

            int sum = 0, res = 0;
            for (Integer value : map.values()) {
                sum += value;
                res = Math.max(res, sum);
            }
            return res;
        }
    }
}
