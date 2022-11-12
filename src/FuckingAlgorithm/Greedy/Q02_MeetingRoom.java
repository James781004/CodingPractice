package FuckingAlgorithm.Greedy;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Q02_MeetingRoom {
//    https://labuladong.github.io/algo/3/29/100/

    // 掃描線算法
    public int minMeetingRooms(int[][] meetings) {
        int n = meetings.length;
        int[] begin = new int[n];
        int[] end = new int[n];

        // 把左端點和右端點單獨拿出來
        for (int i = 0; i < n; i++) {
            begin[i] = meetings[i][0];
            end[i] = meetings[i][1];
        }

        // 排序後就是圖中的紅點
        Arrays.sort(begin);
        // 排序後就是圖中的綠點
        Arrays.sort(end);

        // 掃描過程中的計數器
        int count = 0;
        // 雙指針技巧
        int res = 0, i = 0, j = 0;
        while (i < n && j < n) {
            if (begin[i] < end[j]) {
                // 掃描到一個紅點
                count++;
                i++;
            } else {
                // 掃描到一個綠點
                count--;
                j++;
            }
            // 記錄掃描過程中的最大值
            res = Math.max(res, count);
        }

        return res;
    }


    // PriorityQueue
    public int minMeetingRoomsPQ(int[][] intervals) {
        if (intervals.length == 0) return 0;

        // 最小堆
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(intervals.length, (a, b) -> a - b);

        // 對時間表按照開始時間從小到大排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // 添加第一場會議的結束時間
        pq.add(intervals[0][1]);

        // 遍歷除第一場之外的所有會議
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= pq.peek()) {
                // 如果當前會議的開始時間大於前面已經開始的會議中最晚結束的時間
                // 說明有會議室空閒出來了，可以直接重復利用
                // 當前時間已經是 intervals[i][0]，因此把已經結束的會議刪除
                pq.poll();
            }
            // 把當前會議的結束時間加入最小堆中
            pq.add(intervals[i][1]);
        }

        // 當所有會議遍歷完畢，還在最小堆裡面的，說明會議還沒結束，此時的數量就是會議室的最少數量
        return pq.size();
    }
}
