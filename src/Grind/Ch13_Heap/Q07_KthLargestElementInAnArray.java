package Grind.Ch13_Heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Q07_KthLargestElementInAnArray {
    // https://leetcode.cn/problems/kth-largest-element-in-an-array/solutions/19607/partitionfen-er-zhi-zhi-you-xian-dui-lie-java-dai-/
    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        // 使用一個含有 k 個元素的最小堆，PriorityQueue 底層是動態數組，為了防止數組擴容產生消耗，可以先指定數組的長度
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k, Comparator.comparingInt(a -> a));
        // Java 裡沒有 heapify ，因此逐個將前 k 個元素添加到 minHeap 裡
        for (int i = 0; i < k; i++) {
            minHeap.offer(nums[i]);
        }

        for (int i = k; i < len; i++) {
            // 看一眼，不拿出，因為有可能沒有必要替換
            Integer topElement = minHeap.peek();
            // 只要當前遍歷的元素比堆頂元素大，堆頂彈出，遍歷的元素進去
            if (nums[i] > topElement) {
                // Java 沒有 replace()，所以得先 poll() 出來，然後再放回去
                minHeap.poll();
                minHeap.offer(nums[i]);
            }
        }
        return minHeap.peek();
    }


    // 快速排序
    private final static Random random = new Random(System.currentTimeMillis());

    public int findKthLargest2(int[] nums, int k) {
        // 第 1 大的數，下標是 len - 1;
        // 第 2 大的數，下標是 len - 2;
        // ...
        // 第 k 大的數，下標是 len - k;
        int len = nums.length;
        int target = len - k;

        int left = 0;
        int right = len - 1;

        while (true) {
            int pivotIndex = partition(nums, left, right);
            if (pivotIndex == target) {
                return nums[pivotIndex];
            } else if (pivotIndex < target) {
                left = pivotIndex + 1;
            } else {
                // pivotIndex > target
                right = pivotIndex - 1;
            }
        }
    }

    private int partition(int[] nums, int left, int right) {
        int randomIndex = left + random.nextInt(right - left + 1);
        swap(nums, left, randomIndex);


        // all in nums[left + 1..le) <= pivot;
        // all in nums(ge..right] >= pivot;
        int pivot = nums[left];
        int le = left + 1;
        int ge = right;

        while (true) {
            while (le <= ge && nums[le] < pivot) {
                le++;
            }

            while (le <= ge && nums[ge] > pivot) {
                ge--;
            }

            if (le >= ge) {
                break;
            }
            swap(nums, le, ge);
            le++;
            ge--;
        }

        swap(nums, left, ge);
        return ge;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
}
