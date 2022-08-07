package PointToOffer.StackQueueHeap;

import java.util.LinkedList;
import java.util.Queue;

public class Q41_2_NotRepeat {
    private Queue<Character> queue = new LinkedList<>();
    private int[] cnts = new int[128];

    public void Insert(char c) {
        cnts[c]++;
        queue.add(c);
        while (!queue.isEmpty() && cnts[queue.peek()] > 1) {
            queue.poll();
        }
    }

    public char Find() {
        return queue.isEmpty() ? '#' : queue.peek();
    }
}
