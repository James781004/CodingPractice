package GuChengAlgorithm.Ch01_BasicDataStructure.StackAndQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Q07_MaximumFrequencyStack {
//    https://www.cnblogs.com/grandyang/p/10989417.html
//    Implement `FreqStack`, a class which simulates the operation of a stack-like data structure.
//    FreqStack has two functions:
//
//    push(int x), which pushes an integer xonto the stack.
//    pop(), which removes and returns the most frequent element in the stack.
//    If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.

    class FreqStack {
        Map<Integer, Integer> map;
        Map<Integer, Stack<Integer>> freqToElement;
        int maxFreq;

        public FreqStack() {
            maxFreq = 0;
            map = new HashMap<>();
            freqToElement = new HashMap<>();
        }

        public void push(int x) {
            map.put(x, map.getOrDefault(x, 0) + 1);
            int curFreq = map.get(x);
            maxFreq = Math.max(maxFreq, curFreq);
//            freqToElement.computeIfAbsent(curFreq, v -> new Stack<>()).push(x);
            if (!freqToElement.containsKey(curFreq)) {
                freqToElement.put(curFreq, new Stack<>());
            }

            freqToElement.get(curFreq).push(x);
        }

        public int pop() {
            int x = freqToElement.get(maxFreq).pop(); // target element
            if (freqToElement.get(maxFreq).size() == 0) maxFreq--;
            map.put(x, map.get(x) - 1);
            return x;
        }
    }


    class FreqStackPQ {
        int pushCount;
        PriorityQueue<int[]> pq; // [element, frequency, insertSequence]
        Map<Integer, Integer> map;

        public FreqStackPQ() {
            pushCount = 0;
            pq = new PriorityQueue<int[]>((a, b) -> a[1] == b[1] ? b[2] - a[2] : b[1] - a[1]);
            map = new HashMap<>();
        }

        public void push(int x) {
            map.put(x, map.getOrDefault(x, 0) + 1);
            pq.offer(new int[]{x, map.get(x), pushCount++});
        }

        public int pop() {
            int mostFreqElement = pq.peek()[0];
            map.put(mostFreqElement, map.get(mostFreqElement) - 1);
            return pq.poll()[0];
        }
    }
}
