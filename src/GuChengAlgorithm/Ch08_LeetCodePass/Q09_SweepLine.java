package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.*;

public class Q09_SweepLine {
    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g10a07351424_0_1
    class MeetingRooms {
        public boolean canAttendMeetings(int[][] intervals) {
            if (intervals.length == 0) return true;
            Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
            for (int i = 0; i < intervals.length - 1; i++) {
                if (intervals[i][1] > intervals[i + 1][0]) return false;
            }
            return true;
        }


        public boolean canAttendMeetings2(int[][] intervals) {
            if (intervals == null || intervals.length == 0) return true;
            PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> a.start - b.start);
            for (int[] i : intervals) pq.add(new Interval(i[0], i[1]));
            Interval pre = pq.poll();
            for (int i = 1; i < intervals.length; i++) {
                Interval cur = pq.poll();
                if (pre.end > cur.start) return false;
                pre = cur;
            }
            return true;

        }

        class Interval {
            int start, end;

            Interval(int s, int e) {
                start = s;
                end = e;
            }
        }
    }


    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g10a07351424_2_2
    class MeetingRoomsII {
        public int minMeetingRoomsPQ(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
            PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
            heap.offer(intervals[0]);
            for (int i = 1; i < intervals.length; i++) {
                if (heap.peek()[1] <= intervals[i][0]) heap.poll();
                heap.offer(intervals[i]);
            }
            return heap.size();
        }

        public int minMeetingRooms(int[][] intervals) {
            List<int[]> list = new ArrayList<>();
            for (int[] interval : intervals) {
                list.add(new int[]{interval[0], 1});
                list.add(new int[]{interval[1], -1});
            }
            Collections.sort(list, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
            int res = 0, count = 0;
            for (int[] point : list) {
                count += point[1];
                res = Math.max(res, count);
            }
            return res;
        }


        public int minMeetingRooms2(int[][] intervals) {
            int[] starts = new int[intervals.length], ends = new int[intervals.length];
            for (int i = 0; i < intervals.length; i++) {
                starts[i] = intervals[i][0];
                ends[i] = intervals[i][1];
            }

            Arrays.sort(starts);
            Arrays.sort(ends);
            int room = 0, end = 0;
            for (int i = 0; i < starts.length; i++) {
                room++;
                if (ends[end] <= starts[i]) {
                    room--;
                    end++;
                }
            }
            return room;
        }
    }


    // https://docs.google.com/presentation/d/1V8uag9bdHtzfjSrrs1Wo9mNFxmkYsr89nTOiyNkZsUA/edit#slide=id.g10a07351424_8_0
    class SkylineProblem {
        public List<List<Integer>> getSkyline(int[][] buildings) {
            List<List<Integer>> res = new ArrayList<>();
            List<int[]> height = new ArrayList<>();
            for (int[] b : buildings) {
                height.add(new int[]{b[0], -b[2]});  // 先按照時間序排列遍歷時間點
                height.add(new int[]{b[1], b[2]});   // 時間點相同的先遍歷起飛(左上)負高度，後遍歷降落(右上)高度
            }

            Collections.sort(height, (a, b) -> (a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]));
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));  // pq存放高度，從大排到小
            pq.offer(0);
            int preMax = 0;
            for (int[] h : height) {
                if (h[1] < 0) pq.offer(-h[1]);  // 高度為負數，表示遇到新房子，加入pq
                else pq.remove(h[1]);           // 遇到舊房子就拿掉
                int curMax = pq.peek();
                if (curMax != preMax) {  // 當高度出現變化，開始記錄頂點
                    res.add(Arrays.asList(h[0], curMax));  // 記錄x座標以及高度
                    preMax = curMax;
                }
            }
            return res;
        }
    }
}
