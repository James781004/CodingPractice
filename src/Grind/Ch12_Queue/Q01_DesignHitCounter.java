package Grind.Ch12_Queue;

import java.util.LinkedList;
import java.util.Queue;

public class Q01_DesignHitCounter {
    // https://blog.csdn.net/qq_46105170/article/details/105964189
    public class HitCounter {
        private Queue<Integer> queue;

        /**
         * Initialize your data structure here.
         */
        public HitCounter() {
            queue = new LinkedList<>();
        }

        /**
         * Record a hit.
         *
         * @param timestamp - The current timestamp (in seconds granularity).
         */
        public void hit(int timestamp) {
            queue.offer(timestamp);
        }

        /**
         * Return the number of hits in the past 5 minutes.
         *
         * @param timestamp - The current timestamp (in seconds granularity).
         */
        public int getHits(int timestamp) {
            while (!queue.isEmpty() && queue.peek() <= timestamp - 300) {
                queue.poll(); // 把隊首的過期的數字刪掉即可
            }

            return queue.size();
        }
    }
}
