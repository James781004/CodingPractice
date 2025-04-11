package EndlessCheng.Basic.MonoQueue;

import java.util.ArrayDeque;

public class maximumRobots {

    // https://leetcode.cn/problems/maximum-number-of-robots-within-budget/solutions/1798725/by-endlesscheng-7ukp/
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        var ans = 0;
        var q = new ArrayDeque<Integer>();
        var sum = 0L;
        // 枚舉區間右端點 right，計算區間左端點 left 的最小值
        for (int left = 0, right = 0; right < chargeTimes.length; right++) {
            // 及時清除隊列中的無用數據，保證隊列的單調性
            while (!q.isEmpty() && chargeTimes[right] >= chargeTimes[q.peekLast()])
                q.pollLast();
            q.addLast(right);
            sum += runningCosts[right];
            // 如果左端點 left 不滿足要求，就不斷右移 left
            while (!q.isEmpty() && chargeTimes[q.peekFirst()] + (right - left + 1) * sum > budget) {
                // 及時清除隊列中的無用數據，保證隊列的單調性
                if (q.peekFirst() == left) q.pollFirst();
                sum -= runningCosts[left++];
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


}
