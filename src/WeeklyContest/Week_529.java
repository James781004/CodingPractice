package WeeklyContest;

import java.util.*;

public class Week_529 {

    // https://leetcode.cn/problems/count-dominant-elements/solutions/4059602/bian-li-pythonjavacgo-by-endlesscheng-ki8t/
    public int countDominantElements(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 1;
        }
        int ans = 0;
        if (nums[0] >= nums[1]) {
            ans = 1;
        }
        for (int i = 1; i < n - 1; i++) {
            if (nums[i] * 2 >= nums[i - 1] + nums[i + 1]) {
                ans++;
            }
        }
        if (nums[n - 1] >= nums[n - 2]) {
            ans++;
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-cost-to-make-all-array-elements-equal/solutions/4059601/bian-li-lian-xu-xiang-tong-duan-xiu-gai-qj9ew/
    public long minCost(int[] nums) {
        int n = nums.length;
        long ans = Long.MAX_VALUE;
        int start = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (i == n - 1 || x != nums[i + 1]) { // i 是連續相同段的末尾
                if (i > 0 && start < n - 1) { // 這一段不能只有 nums[0] 或者只有 nums[n-1]
                    ans = Math.min(ans, (long) (start + n - i - 1) * x);
                }
                start = i + 1; // 下一段的開始位置
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/earliest-signal-activation-times/solutions/4059592/zui-xiao-dui-mo-ni-pythonjavacgo-by-endl-7av5/
    public long[] earliestActivationTimes(int n, int[][] edges, int[] requiredSignals) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            g[e[0]].add(new int[]{e[1], e[2]});
        }

        long[] ans = new long[n];
        Arrays.fill(ans, -1);
        requiredSignals[0] = 1;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(p -> p[0]));
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long t = top[0];
            int x = (int) top[1];
            requiredSignals[x]--;
            if (requiredSignals[x] != 0) { // x 尚未激活或者已經激活
                continue;
            }
            ans[x] = t;
            for (int[] e : g[x]) {
                pq.offer(new long[]{t + e[1], e[0]});
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/minimum-cost-to-delete-array/solutions/4059598/nao-jin-ji-zhuan-wan-pai-xu-kuai-su-xuan-oe8u/
    public long minCostII(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums, 1, n - 1);

        long ans = nums[0] + nums[n - 1];
        for (int i = 2; i < n - 1 && nums[i - 1] + nums[i] < 0; i += 2) {
            ans += nums[i - 1] + nums[i];
        }
        return ans;
    }


}










