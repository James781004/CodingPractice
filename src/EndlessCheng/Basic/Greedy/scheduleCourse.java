package EndlessCheng.Basic.Greedy;

import java.util.Arrays;
import java.util.PriorityQueue;

public class scheduleCourse {

    // https://leetcode.cn/problems/course-schedule-iii/solutions/2436667/tan-xin-huan-neng-fan-hui-pythonjavacgoj-lcwp/
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]); // 按照 lastDay 從小到大排序
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // 最大堆
        int day = 0; // 已消耗時間
        for (int[] c : courses) {
            int duration = c[0], lastDay = c[1];
            if (day + duration <= lastDay) { // 沒有超過 lastDay，直接學習
                day += duration;
                pq.offer(duration);
            } else if (!pq.isEmpty() && duration < pq.peek()) { // 該課程的時間比之前的最長時間要短
                // 反悔，撤銷之前 duration 最長的課程，改為學習該課程
                // 節省出來的時間，能在後面上完更多的課程
                day -= pq.poll() - duration;
                pq.offer(duration);
            }
        }
        return pq.size();
    }


}
