package EndlessCheng.GenreMenu.MonoStack.Contribution;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaxSumMinProduct {

    // https://leetcode.cn/problems/maximum-subarray-min-product/solutions/2372186/tu-wen-xiang-jie-gong-xian-fa-de-si-kao-3p1hh/
    public static final long MOD = (long) 1e9 + 7;

    public int maxSumMinProduct(int[] nums) {
        int n = nums.length;

        // 前綴和
        long[] pre = new long[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = (long) (pre[i] + nums[i]);
        }

        int[] left = new int[n];
        Deque<Integer> stk = new ArrayDeque<>();
        // 如果元素左側沒有元素比其小，則左邊界為開區間 -1
        stk.addLast(-1);
        for (int i = 0; i < n; i++) {
            // 將大於等於nums[i] 的數全部出棧
            while (stk.size() > 1 && nums[stk.getLast()] >= nums[i]) stk.pollLast();
            // 棧頂元素即為 nums[i] 的左邊界
            left[i] = stk.getLast();
            stk.addLast(i);
        }

        int[] right = new int[n];
        stk.clear();
        // 如果元素右側沒有元素比其小，則右邊界為開區間 n = nums.length
        stk.addLast(n);
        for (int i = n - 1; i >= 0; i--) {
            // 將大於等於nums[i] 的數全部出棧
            while (stk.size() > 1 && nums[stk.getLast()] >= nums[i]) stk.pollLast();
            // 棧頂元素即為 nums[i] 的右邊界
            right[i] = stk.getLast();
            stk.addLast(i);
        }

        long ans = 0L;
        for (int i = 0; i < n; i++) {
            // 子數組的和
            long sum = (long) (pre[right[i]] - pre[left[i] + 1]);
            // 最小乘積
            long s = (long) (nums[i] * sum);
            // “貢獻” 給最終答案
            ans = Math.max(ans, s);
        }
        // 取模
        return (int) (ans % MOD);
    }


}
