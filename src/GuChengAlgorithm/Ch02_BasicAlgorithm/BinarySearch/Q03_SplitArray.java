package GuChengAlgorithm.Ch02_BasicAlgorithm.BinarySearch;

import java.util.Arrays;

public class Q03_SplitArray {
    // https://docs.google.com/presentation/d/1w3FRwGmgbJBQP8odS5-TLlkheGm3c9U44I8hNdhexGo/edit#slide=id.g9cebf34a0f_0_35
    public int splitArray(int[] nums, int m) {
        int sum = Arrays.stream(nums).sum();
        int max = Arrays.stream(nums).max().getAsInt();
        return binary(nums, m, sum, max);
    }

    private int binary(int[] nums, int m, int high, int low) {
        int mid = 0;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (valid(nums, m, mid)) high = mid - 1;
            else low = mid + 1;
        }
        return low;
    }

    private boolean valid(int[] nums, int m, int max) {
        int curSum = 0, count = 1;
        for (int num : nums) {
            curSum += num;
            if (curSum > max) {  // 當子集和大於當前max，就要開始進行分割
                curSum = num; // 最後加上的數字之前子集和算做一個部份，切割count + 1
                count++;
                if (count > m) return false; // 如果分割數大於限制的m，就是無效答案
            }
        }
        return true;
    }
}
