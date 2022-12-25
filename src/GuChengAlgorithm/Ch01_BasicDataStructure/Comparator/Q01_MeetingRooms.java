package GuChengAlgorithm.Ch01_BasicDataStructure.Comparator;

import java.util.*;

public class Q01_MeetingRooms {

    // LC 252
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length == 0) return true;

        // 1. 使用外部Comparator
        // Arrays.sort(intervals, new MyComparator());

        // 2. 使用內部Comparator
        // Arrays.sort(intervals, new Comparator<int[]>() {
        //            @Override
        //            public int compare(int[] a, int[] b) {
        //                return a[0] - b[0];
        //            }
        //        });

        // 3. 使用內部lambda
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        // Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < intervals.length; i++) {
            if (intervals[i][1] > intervals[i + 1][0]) return false;
        }

        return true;
    }


    class MyComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
//            return a[0] - b[0];
            return Integer.compare(a[0], b[0]);
        }
    }


    // LC 253
    public int minMeetingRooms(int[][] intervals) {
        List<Node> list = new ArrayList<>();

        // 掃瞄線算法
        for (int[] interval : intervals) {
            list.add(new Node(interval[0], 1));
            list.add(new Node(interval[1], -1));
        }
        Collections.sort(list);

        int res = 0, count = 0;
        for (Node node : list) {
            count += node.value;
            res = Math.max(res, count);
        }

        return res;
    }


    class Node implements Comparable<Node> {
        int time;
        int value;

        public Node(int time, int value) {
            this.time = time;
            this.value = value;
        }

        @Override
        public int compareTo(Node other) {
            return this.time == other.time ? this.value - other.value : this.time - other.time;
        }
    }


    public int minMeetingRooms2(int[][] intervals) {
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
}
