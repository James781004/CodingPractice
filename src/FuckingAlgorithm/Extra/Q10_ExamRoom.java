package FuckingAlgorithm.Extra;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Q10_ExamRoom {
//    https://leetcode.cn/problems/exam-room/
//    855. 考場就座
//    在考場裡，一排有 N 個座位，分別編號為 0, 1, 2, ..., N-1 。
//
//    當學生進入考場後，他必須坐在能夠使他與離他最近的人之間的距離達到最大化的座位上。
//    如果有多個這樣的座位，他會坐在編號最小的座位上。(另外，如果考場裡沒有人，那麼學生就坐在 0 號座位上。)
//
//    返回 ExamRoom(int N) 類，它有兩個公開的函數：
//    其中，函數 ExamRoom.seat() 會返回一個 int （整型數據），代表學生坐的位置；
//    函數 ExamRoom.leave(int p) 代表坐在座位 p 上的學生現在離開了考場。
//    每次調用 ExamRoom.leave(p) 時都保證有學生坐在座位 p 上。


    class ExamRoom {
        // 將端點 p 映射到以 p 為左端點的線段
        private Map<Integer, int[]> startMap;
        // 將端點 p 映射到以 p 為右端點的線段
        private Map<Integer, int[]> endMap;
        // 根據線段長度從小到大存放所有線段
        private TreeSet<int[]> pq;
        private int N;

        // 構造函數，傳入座位總數 N
        public ExamRoom(int N) {
            this.N = N;
            startMap = new HashMap<>();
            endMap = new HashMap<>();
            pq = new TreeSet<>((a, b) -> {
                // 算出兩個線段的長度
                int distA = distance(a);
                int distB = distance(b);
                // 如果長度相同，就比較索引
                if (distA == distB) return b[0] - a[0];
                // 長度更長的更大，排後面
                return distA - distB;
            });
            // 在有序集合中先放一個虛擬線段
            addInterval(new int[]{-1, N});
        }

        // 來了一名考生，返回你給他分配的座位
        public int seat() {
            // 從有序集合拿出最長的線段
            int[] longest = pq.last();
            int x = longest[0];
            int y = longest[1];
            int seat;
            if (x == -1) { // 情況一
                seat = 0;
            } else if (y == N) { // 情況二
                seat = N - 1;
            } else { // 情況三
                seat = (y - x) / 2 + x;
            }
            // 將最長的線段分成兩段
            int[] left = new int[]{x, seat};
            int[] right = new int[]{seat, y};
            removeInterval(longest);
            addInterval(left);
            addInterval(right);
            return seat;
        }

        // 坐在 p 位置的考生離開了
        // 可以認為 p 位置一定坐有考生
        public void leave(int p) {
            // 將 p 左右的線段找出來
            int[] right = startMap.get(p);
            int[] left = endMap.get(p);
            // 合並兩個線段成為一個線段
            int[] merged = new int[]{left[0], right[1]};
            removeInterval(left);
            removeInterval(right);
            addInterval(merged);
        }

        /* 去除一個線段 */
        private void removeInterval(int[] intv) {
            pq.remove(intv);
            startMap.remove(intv[0]);
            endMap.remove(intv[1]);
        }

        /* 增加一個線段 */
        private void addInterval(int[] intv) {
            pq.add(intv);
            startMap.put(intv[0], intv);
            endMap.put(intv[1], intv);
        }

        /* 計算一個線段的長度 */
        private int distance(int[] intv) {
            int x = intv[0];
            int y = intv[1];
            if (x == -1) return y;
            if (y == N) return N - 1 - x;
            // 中點和端點之間的長度
            return (y - x) / 2;
        }
    }
}
