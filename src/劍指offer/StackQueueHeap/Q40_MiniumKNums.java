package 劍指offer.StackQueueHeap;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Q40_MiniumKNums {

    public List<Integer> GetLeastNumbers_Solution_PriorityQueue(int[] nums, int k) {
        if (k > nums.length || k <= 0) return null;

        // PriorityQueue 默认是小顶堆
        // 可以在在初始化时使用 Lambda 表达式 (o1, o2) -> o2 - o1 来实现大顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int num : nums) {
            maxHeap.add(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        return new ArrayList<>(maxHeap);
    }

    public List<Integer> GetLeastNumbers_Solution_QuickSort(int[] nums, int k) {
        if (k > nums.length || k <= 0) return null;
        List<Integer> list = new ArrayList<>();
        findKthSmallest(nums, k - 1);
        /* findKthSmallest 会改变数组，使得前 k 个数都是最小的 k 个数 */
        for (int i = 0; i < k; i++)
            list.add(nums[i]);
        return list;
    }

    private void findKthSmallest(int[] nums, int k) {
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int j = partition(nums, l, h);
            if (j == k) break;
            if (j > k) h = j - 1;
            else l = j + 1;
        }
    }

    private int partition(int[] nums, int l, int h) {
        int p = nums[l];  // 切分元素
        int i = l, j = h + 1;
        while (true) {
            while (i != h && nums[++i] < p) ;
            while (j != l && nums[--j] > p) ;
            if (i >= j)
                break;
            swap(nums, i, j);
        }
        swap(nums, l, j);
        return j;
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
