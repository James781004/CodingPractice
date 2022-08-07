package 程序员代码面试指南.ch09;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Q28_MadianQuick {
//    CD80 數據流中的中位數
//    描述
//    如何得到一個數據流中的中位數？如果從數據流中讀出奇數個數值，那麽中位數就是所有數值排序之後位於中間的數值。
//    如果從數據流中讀出偶數個數值，那麽中位數就是所有數值排序之後中間兩個數的平均值。
//    我們使用Insert()方法讀取數據流，使用GetMedian()方法獲取當前讀取數據的中位數。
//
//    數據範圍：數據流中數個數滿足1≤n≤1000  ，大小滿足1≤val≤1000
//
//    進階： 空間覆雜度O(n)  ， 時間覆雜度O(nlogn)

    // 小根堆從小排到大
    public static class MinHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    // 大根堆從大排到小
    public static class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    public static class MedianHolder {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;

        public MedianHolder() {
            maxHeap = new PriorityQueue<Integer>(new MaxHeapComparator());
            minHeap = new PriorityQueue<Integer>(new MinHeapComparator());
        }

        public void addNumber(Integer num) {
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) { // 新元素優先考慮加入大堆maxHeap
                maxHeap.add(num);
            } else {
                minHeap.add(num);
            }
            modifyTwoHeaps(); // 加完新元素後進行調整
        }

        // 中位數在大根堆頭和小根堆頭之間，看目前元素個數決定怎麼處理
        public Integer getMedian() {
            if (maxHeap.isEmpty()) return null;

            if (maxHeap.size() == minHeap.size()) {
                return (maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
            }
        }

        // 兩個堆的儲存量不可以超過2
        private void modifyTwoHeaps() {
            if (maxHeap.size() == minHeap.size() + 2) { // 把大堆頭移動到小堆
                minHeap.add(maxHeap.poll());
            }
            if (minHeap.size() == maxHeap.size() + 2) { // 把小堆頭移動到大堆
                maxHeap.add(minHeap.poll());
            }
        }

    }

    // for test
    public static int[] getRandomArray(int maxLen, int maxValue) {
        int[] res = new int[(int) (Math.random() * maxLen) + 1];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue);
        }
        return res;
    }

    // for test, this method is ineffective but absolutely right
    public static int getMedianOfArray(int[] arr) {
        int[] newArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(newArr);
        int mid = (newArr.length - 1) / 2;
        if ((newArr.length & 1) == 0) {
            return (newArr[mid] + newArr[mid + 1]) / 2;
        } else {
            return newArr[mid];
        }
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        boolean err = false;
        int testTimes = 2000000;
        for (int i = 0; i != testTimes; i++) {
            int len = 30;
            int maxValue = 1000;
            int[] arr = getRandomArray(len, maxValue);
            MedianHolder medianHold = new MedianHolder();
            for (int j = 0; j != arr.length; j++) {
                medianHold.addNumber(arr[j]);
            }
            if (medianHold.getMedian() != getMedianOfArray(arr)) {
                err = true;
                printArray(arr);
                break;
            }
        }
        System.out.println(err ? "Oops..what a fuck!"
                : "today is a beautiful day^_^");

    }
}
