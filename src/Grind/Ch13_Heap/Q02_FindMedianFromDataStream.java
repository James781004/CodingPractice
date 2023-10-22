package Grind.Ch13_Heap;

import java.util.PriorityQueue;

public class Q02_FindMedianFromDataStream {
    // https://leetcode.cn/problems/find-median-from-data-stream/solutions/1229993/qing-xi-tu-jie-jian-zhi-offer41-shu-ju-l-rpen/
    class MedianFinder {
        // 大頂堆存儲較小一半的值
        PriorityQueue<Integer> maxHeap;
        // 小頂堆存儲較大一般的值
        PriorityQueue<Integer> minHeap;

        /**
         * initialize your data structure here.
         */
        public MedianFinder() {
            maxHeap = new PriorityQueue<Integer>((x, y) -> (y - x));
            minHeap = new PriorityQueue<Integer>();
        }


        public void addNum(int num) {
            // 長度為奇數時先放入小頂堆,重新排序後插入到大頂堆
            if (maxHeap.size() != minHeap.size()) {
                minHeap.add(num);
                maxHeap.add(minHeap.poll());
            } else {
                // 長度為偶數時先放入大頂堆,重新排序後插入到小頂堆
                maxHeap.add(num);
                minHeap.add(maxHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() != minHeap.size()) {
                return minHeap.peek();
            } else {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            }
        }

    }
}
