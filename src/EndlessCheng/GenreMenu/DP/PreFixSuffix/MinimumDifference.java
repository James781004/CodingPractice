package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.PriorityQueue;

public class MinimumDifference {

    // https://leetcode.cn/problems/minimum-difference-in-sums-after-removal-of-elements/solutions/1247074/qian-zhui-zui-xiao-he-hou-zhui-zui-da-he-yz3d/
    public long minimumDifference(int[] nums) {
        int m = nums.length;
        int n = m / 3;
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        long sum = 0;
        for (int i = m - 1; i >= m - n; i--) {
            minPQ.offer(nums[i]);
            sum += nums[i];
        }

        long[] sufMax = new long[m - n + 1]; // 後綴最大和
        sufMax[m - n] = sum;
        for (int i = m - n - 1; i >= n; i--) {
            int v = nums[i];
            if (v > minPQ.peek()) {
                sum += v - minPQ.poll();
                minPQ.offer(v);
            }
            sufMax[i] = sum;
        }

        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((a, b) -> b - a);
        long preMin = 0; // 前綴最小和
        for (int i = 0; i < n; ++i) {
            maxPQ.offer(nums[i]);
            preMin += nums[i];
        }

        long ans = preMin - sufMax[n]; // i=n-1 時的答案
        for (int i = n; i < m - n; i++) {
            int v = nums[i];
            if (v < maxPQ.peek()) {
                preMin += v - maxPQ.poll();
                maxPQ.offer(v);
            }
            ans = Math.min(ans, preMin - sufMax[i + 1]);
        }
        return ans;
    }

}
