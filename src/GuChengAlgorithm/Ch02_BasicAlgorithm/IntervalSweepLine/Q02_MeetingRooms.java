package GuChengAlgorithm.Ch02_BasicAlgorithm.IntervalSweepLine;


import java.util.*;

public class Q02_MeetingRooms {

    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g885522199d_0_41
    // LC 252
    public boolean canMeeting(Interval[] intervals) {
        Arrays.sort(intervals, (a, b) -> a.start - b.start);
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i].end > intervals[i + 1].start) return false;
        }

        return true;
    }

    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g885522199d_0_54
    // LC 253
    // 就是數飛機算法
    public int countOfRooms(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(new int[]{interval[0], 1});  // meeting開始+1
            list.add(new int[]{interval[1], -1});  // meeting結束-1
        }
        Collections.sort(list, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int res = 0, count = 0;
        for (int[] point : list) {
            count += point[1];
            res = Math.max(res, count);
        }
        return res;
    }


    public int countOfRoomsPQ(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        if (intervals.length != 0) heap.offer(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = heap.poll(); // 彈出隊頭的room資訊
            if (cur[1] <= intervals[i][0]) cur[1] = intervals[i][1];  // 前一個meeting已結束，room可以重複利用
            else heap.offer(intervals[i]); // 前一個meeting未結束，room要多一間，加入pq
            heap.offer(cur); // 重新放回原本隊頭的room資訊
        }
        return heap.size();
    }


    private class Interval {
        public int start;
        public int end;

        public Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

}
