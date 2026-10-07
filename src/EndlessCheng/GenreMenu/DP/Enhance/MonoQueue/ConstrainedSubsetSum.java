package EndlessCheng.GenreMenu.DP.Enhance.MonoQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class ConstrainedSubsetSum {

    // https://leetcode.cn/problems/constrained-subsequence-sum/solutions/221059/dong-tai-gui-hua-hua-dong-chuang-kou-dan-diao-dui-/
    public int constrainedSubsetSum(int[] nums, int k) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        Deque<Integer> deque = new ArrayDeque();//單調遞減隊列，隊首是窗口的最大值，
        deque.addFirst(dp[0]);
        for (int i = 1; i < k; i++) //當窗口大小沒有達到k的時候
        {
            dp[i] = Math.max(deque.getFirst() + nums[i], nums[i]);
            res = Math.max(res, dp[i]);
            while (!deque.isEmpty() && deque.getLast() < dp[i]) {
                deque.removeLast();
            }
            deque.addLast(dp[i]);
        }
        for (int i = k; i < nums.length; i++)//當窗口大小達到k後
        {
            dp[i] = Math.max(deque.getFirst() + nums[i], nums[i]);
            res = Math.max(res, dp[i]);
            while (!deque.isEmpty() && deque.getLast() < dp[i]) {
                deque.removeLast();
            }
            deque.addLast(dp[i]);
            if (dp[i - k] == deque.getFirst()) deque.removeFirst();
        }
        return res;
    }


}
