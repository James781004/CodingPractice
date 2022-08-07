package PointToOffer.StackQueueHeap;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Q59_MaxInWindow {
    public List<Integer> maxInWindows(int[] num, int size) {
        List<Integer> ret = new ArrayList<>();
        if (size > num.length || size <= 0) return ret;

        /* 大顶堆 */
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        for (int i = 0; i < size; i++) {
            heap.add(num[i]);
        }

        ret.add(heap.peek());

        for (int i = 0, j = i + size; j < num.length; i++, j++) {  /* 维护一个大小为 size 的大顶堆 */
            heap.remove(num[i]);
            heap.add(num[j]);
            ret.add(heap.peek());
        }

        return ret;
    }

}
