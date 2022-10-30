package FuckingAlgorithm.StructureDesign;

import java.util.PriorityQueue;

public class Q04_MedianFinder {
//    https://leetcode.cn/problems/find-median-from-data-stream/
//    295. 數據流的中位數
//    中位數是有序列表中間的數。如果列表長度是偶數，中位數則是中間兩個數的平均值。
//
//    例如，
//
//            [2,3,4] 的中位數是 3
//
//            [2,3] 的中位數是 (2 + 3) / 2 = 2.5
//
//    設計一個支持以下兩種操作的數據結構：
//
//    void addNum(int num) - 從數據流中添加一個整數到數據結構中。
//    double findMedian() - 返回目前所有元素的中位數。
//    示例：
//
//    addNum(1)
//    addNum(2)
//    findMedian() -> 1.5
//    addNum(3)
//    findMedian() -> 2
//    進階:
//
//    如果數據流中所有整數都在 0 到 100 范圍內，你將如何優化你的算法？
//    如果數據流中 99% 的整數都在 0 到 100 范圍內，你將如何優化你的算法？

    class MedianFinder {
        private PriorityQueue<Integer> large;
        private PriorityQueue<Integer> small;

        public MedianFinder() {
            // 小頂堆
            large = new PriorityQueue<>();
            // 大頂堆
            small = new PriorityQueue<>((a, b) -> {
                return b - a;
            });
        }

        public double findMedian() {
            // 如果元素不一樣多，多的那個堆的堆頂元素就是中位數
            if (large.size() < small.size()) return small.peek();
            if (large.size() > small.size()) return large.peek();

            // 如果元素一樣多，兩個堆堆頂元素的平均數是中位數
            return (small.peek() + large.peek()) / 2.0;
        }

        public void addNum(int num) {
            if (small.size() >= large.size()) {
                small.offer(num);
                large.offer(small.poll());
            } else {
                large.offer(num);
                small.offer(large.poll());
            }
        }

    }
}
