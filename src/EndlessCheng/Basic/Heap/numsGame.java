package EndlessCheng.Basic.Heap;

import java.util.PriorityQueue;

public class numsGame {

    // https://leetcode.cn/problems/5TxKeK/solutions/2627350/zhuan-huan-zhong-wei-shu-tan-xin-dui-din-7r9b/
    public int[] numsGame(int[] nums) {
        final int MOD = 1_000_000_007;
        int[] ans = new int[nums.length];
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> b - a); // 維護較小的一半，大根堆
        PriorityQueue<Integer> right = new PriorityQueue<>(); // 維護較大的一半，小根堆
        long leftSum = 0, rightSum = 0;
        for (int i = 0; i < nums.length; i++) {
            int b = nums[i] - i;
            if (i % 2 == 0) { // 前綴長度是奇數，在插入 b 之前，left 比 right 少一個元素，考慮先把 b 插入 right
                if (!left.isEmpty() && b < left.peek()) { // 如果 b 已經比較小一半最大值還大，乾脆直接放進較大一半
                    leftSum -= left.peek() - b;
                    left.offer(b);
                    b = left.poll();
                }
                rightSum += b;
                right.offer(b);
                ans[i] = (int) ((rightSum - right.peek() - leftSum) % MOD);
            } else { // 前綴長度是偶數，在插入 b 之前，left 和 right 大小相等，考慮先把 b 插入 left
                if (b > right.peek()) { // 如果 b 已經比較大一半最小值還小，乾脆直接放進較小一半
                    rightSum += b - right.peek();
                    right.offer(b);
                    b = right.poll();
                }
                leftSum += b;
                left.offer(b);
                ans[i] = (int) ((rightSum - leftSum) % MOD);
            }
        }
        return ans;
    }


}
