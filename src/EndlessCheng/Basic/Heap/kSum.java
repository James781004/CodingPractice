package EndlessCheng.Basic.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class kSum {

    // https://leetcode.cn/problems/find-the-k-sum-of-an-array/solutions/2676838/javapython3cshu-xue-pai-xu-you-xian-dui-ki3cq/
    // 將問題轉換為 找到絕對值數組的第k個最小子序列和
    public long kSum(int[] nums, int k) {
        int n = nums.length;
        int[] backupNums = Arrays.copyOf(nums, n);   // 為不直接修改輸入，備份一個數組
        long maxSum = 0;       // 最大子序列和，所有正數和
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                maxSum += nums[i];
            }
            backupNums[i] = Math.abs(nums[i]);   // 得到元素的絕對值數組
        }
        Arrays.sort(backupNums); // 對絕對值數組排序

        Queue<long[]> pq = new PriorityQueue<>((a, b) -> a[0] > b[0] ? 1 : -1);   // 小頂堆，隊列中存儲的是<子序列和，子序列最後一個元素的索引>
        pq.offer(new long[]{0, -1});  // 初始子序列和為0，最後一個索引為-1，表示空子序列
        // 循環k-1次，彈出絕對值前k-1最小子序列和
        for (int i = 0; i < k - 1; i++) {
            long[] p = pq.poll();
            int curr = (int) p[1];    // 當前子序列最後一個元素索引
            if (curr == n - 1) continue;   // 到達末尾跳過處理
            long subSum = p[0] + backupNums[curr + 1];  // 當前子序列和
            if (curr >= 0)
                pq.offer(new long[]{subSum - backupNums[curr], curr + 1});    // 非空子序列，以最後一個元素的下一個元素替換最後一個元素
            pq.offer(new long[]{subSum, curr + 1});   // 加入最後一個元素的下一個元素
        }
        return maxSum - pq.peek()[0];     // 最終堆頂的即為第k小的絕對值和，差值即為第k大子序列和
    }


}
