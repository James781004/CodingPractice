package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.ArrayDeque;
import java.util.Deque;

public class CountPartitions {

    // https://leetcode.cn/problems/count-partitions-with-max-min-difference-at-most-k/solutions/3695716/hua-fen-xing-dp-dan-diao-dui-lie-you-hua-9rtj/
    public int countPartitions(int[] nums, int k) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        Deque<Integer> minQ = new ArrayDeque<>();
        Deque<Integer> maxQ = new ArrayDeque<>();
        int[] f = new int[n + 1]; // 定義 f[i+1] 表示前綴 nums[0] 到 nums[i] 在題目約束下，分割出的最少（最多）子數組個數
        f[0] = 1; // 初始值 f[0]=1，空子數組算一個方案
        long sumF = 0; // 窗口中的 f[i] 之和
        int left = 0;

        for (int i = 0; i < n; i++) {
            // 1. 入
            sumF += f[i];

            int x = nums[i];
            while (!minQ.isEmpty() && x <= nums[minQ.peekLast()]) {
                minQ.pollLast();
            }
            minQ.addLast(i);

            while (!maxQ.isEmpty() && x >= nums[maxQ.peekLast()]) {
                maxQ.pollLast();
            }
            maxQ.addLast(i);

            // 2. 出
            while (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > k) {
                sumF -= f[left];
                left++;
                if (minQ.peekFirst() < left) {
                    minQ.pollFirst();
                }
                if (maxQ.peekFirst() < left) {
                    maxQ.pollFirst();
                }
            }

            // 3. 更新答案
            f[i + 1] = (int) (sumF % MOD);
        }

        return f[n];
    }


}
