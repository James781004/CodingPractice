package GuChengAlgorithm.Ch02_BasicAlgorithm.IntervalSweepLine;

import java.util.*;

public class Q04_SlotSummaryRanges {
    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g88ae7bb983_0_55
    // LC 352
    TreeSet<int[]> set = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

    public void addNum(int val) {
        int[] interval = new int[]{val, val};
        if (set.contains(interval)) return;
        int[] low = set.lower(interval), high = set.higher(interval);
        if (high != null && high[0] == val) return;
        if (low != null && low[1] + 1 == val && high != null && val + 1 == high[0]) { // 新插入的時間正好連接兩個slot
            low[1] = high[1];
            set.remove(high);
        } else if (low != null && low[1] + 1 >= val) low[1] = Math.max(low[1], val);  // 新插入的時間和左邊小的slot連接
        else if (high != null && val + 1 == high[0]) high[0] = val;  // 新插入的時間和右邊大的slot連接
        else set.add(interval);  // 新插入的時間和slot不相鄰，獨立插入
    }

    public int[][] getIntervals() {
        List<int[]> res = new ArrayList<>();
        for (int[] interval : set) res.add(interval);
        return res.toArray(new int[res.size()][]);
    }


    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g88ae7bb983_0_7
    // LC 1229
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        Arrays.sort(slots1, (a, b) -> a[0] - b[0]);
        Arrays.sort(slots2, (a, b) -> a[0] - b[0]);
        int i = 0, j = 0;
        int n1 = slots1.length, n2 = slots2.length;
        while (i < n1 && j < n2) {
            int intersectStart = Math.max(slots1[i][0], slots2[j][0]); // 共同開始時間，選擇較晚的一個開始
            int intersectEnd = Math.min(slots1[i][1], slots2[j][1]); // 共同結束時間，選擇較早的一個結束
            if (intersectEnd - intersectStart >= duration) {  // 共同開會時間大於要求的duration
                return Arrays.asList(intersectStart, intersectStart + duration);
            } else if (slots1[i][1] < slots2[j][1]) i++;  // 結束時間較早的就更換到下一個slot
            else j++;
        }
        return new ArrayList<>();
    }


    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g88ae7bb983_0_38
    // LC 986
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < A.length && j < B.length) {
            int low = Math.max(A[i][0], B[i][0]);
            int high = Math.min(A[i][1], B[i][1]);
            if (low <= high) res.add(new int[]{low, high});  // 有交集的狀況，res加入交集區間
            if (A[i][1] < B[j][1]) i++; // 否則end較小那方往後移動，看下一個區間的狀況
            else j++;
        }
        return res.toArray(new int[res.size()][]);
    }


    // https://docs.google.com/presentation/d/1RGF03Syyw2rhU7MojUWT3G-ejw8NFHEANbgnY2AuDEo/edit#slide=id.g88ae7bb983_0_16
    // LC 759
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> res = new ArrayList<>();
        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> (a.start - b.start));
        for (List<Interval> list : schedule) {
            for (Interval interval : list) pq.add(interval);
        }

        Interval cur = pq.poll();
        while (!pq.isEmpty()) {
            if (cur.end >= pq.peek().start) {
                cur.end = Math.max(cur.end, pq.poll().end);  // 兩個工作slot交集，沒有free time，所以合併工作時間
            } else {
                res.add(new Interval(cur.end, pq.peek().start)); // 兩個工作slot沒有交集，中間就有free time可以加進res
                cur = pq.poll(); // 移動到下一個slot
            }
        }
        return res;
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
