package FuckingAlgorithm.Greedy;

import java.util.Arrays;

public class Q04_JumpGame {
//    https://leetcode.cn/problems/jump-game/
//    55. 跳躍游戲
//    給定一個非負整數數組 nums ，你最初位於數組的 第一個下標 。
//
//    數組中的每個元素代表你在該位置可以跳躍的最大長度。
//
//    判斷你是否能夠到達最後一個下標。

    public boolean canJump(int[] nums) {
        int n = nums.length;
        int end = 0;
        for (int i = 0; i < n - 1; i++) {
            // 不斷計算能跳到的最遠距離
            end = Math.max(end, i + nums[i]);

            // 可能碰到了 0，卡住跳不動了
            if (end <= i) return false;
        }
        return end >= n - 1;
    }


//    https://leetcode.cn/problems/jump-game-ii/
//    45. 跳躍游戲 II
//    給你一個非負整數數組 nums ，你最初位於數組的第一個位置。
//
//    數組中的每個元素代表你在該位置可以跳躍的最大長度。
//
//    你的目標是使用最少的跳躍次數到達數組的最後一個位置。
//
//    假設你總是可以到達數組的最後一個位置。


    public int jumpIIMemo(int[] nums) {
        int n = nums.length;

        // 備忘錄都初始化為 n，相當於 INT_MAX
        // 因為從 0 跳到 n - 1 最多 n - 1 步
        int[] memo = new int[n];
        Arrays.fill(memo, n);

        return process(nums, 0, memo);
    }

    private int process(int[] nums, int p, int[] memo) {
        int n = nums.length;
        // base case
        if (p >= n - 1) {
            return 0;
        }
        // 子問題已經計算過
        if (memo[p] != n) {
            return memo[p];
        }
        int steps = nums[p];

        // 你可以選擇跳 1 步，2 步...
        for (int i = 1; i <= steps; i++) {
            // 窮舉每一個選擇
            // 計算每一個子問題的結果
            int subProblem = process(nums, p + i, memo);
            // 取其中最小的作為最終結果
            memo[p] = Math.min(memo[p], subProblem + 1);
        }
        return memo[p];
    }


    public int jumpIIGreedy(int[] nums) {
        int n = nums.length;
        int end = 0, farthest = 0;
        int jumps = 0;
        for (int i = 0; i < n - 1; i++) {
            // 當前格子可能抵達的最遠點
            farthest = Math.max(farthest, nums[i] + i);

            // 抵達前一跳的最遠點end時，就要更新邊界end並且再跳一步，
            // 邊界end更新為這一跳的最遠點farthest
            if (end == i) {
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }
}
