package WeeklyContest;

import java.util.*;

public class Week_481 {

    // https://leetcode.cn/problems/mirror-distance-of-an-integer/solutions/3862116/fan-zhuan-shu-zi-pythonjavacgo-by-endles-6ego/
    public int mirrorDistance(int n) {
        int rev = 0;
        for (int x = n; x > 0; x /= 10) {
            rev = rev * 10 + x % 10;
        }
        return Math.abs(n - rev);
    }


    // https://leetcode.cn/problems/minimum-deletion-cost-to-make-all-characters-equal/solutions/3862111/an-zhao-xiang-tong-zi-mu-fen-zu-pythonja-2pg5/
    public long minCost(String s, int[] cost) {
        long total = 0;
        long[] sum = new long[26];
        long maxSum = 0;
        for (int i = 0; i < cost.length; i++) {
            total += cost[i];
            int idx = s.charAt(i) - 'a';
            sum[idx] += cost[i];
            maxSum = Math.max(maxSum, sum[idx]);
        }
        return total - maxSum;
    }


    // https://leetcode.cn/problems/minimum-swaps-to-avoid-forbidden-values/solutions/3862082/jie-lun-ti-pythonjavacgo-by-endlesscheng-a1pe/
    public int minSwaps(int[] nums, int[] forbidden) {
        int n = nums.length;
        Map<Integer, Integer> total = new HashMap<>();
        for (int x : nums) {
            total.merge(x, 1, Integer::sum);
        }

        Map<Integer, Integer> cnt = new HashMap<>();
        int k = 0;
        int mx = 0;
        for (int i = 0; i < n; i++) {
            int x = forbidden[i];
            int c = total.merge(x, 1, Integer::sum);
            if (c > n) {
                return -1;
            }
            if (x == nums[i]) {
                k++;
                c = cnt.merge(x, 1, Integer::sum);
                mx = Math.max(mx, c);
            }
        }

        return Math.max((k + 1) / 2, mx);
    }

    // https://leetcode.cn/problems/total-sum-of-interaction-cost-in-tree-groups/solutions/3862088/gong-xian-fa-pythonjavacgo-by-endlessche-4nxs/
    private long ans = 0;

    public long interactionCosts(int n, int[][] edges, int[] group) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        int mx = 0;
        for (int x : group) {
            mx = Math.max(mx, x);
        }

        int[] total = new int[mx + 1];
        for (int x : group) {
            total[x]++;
        }

        dfs(0, -1, g, group, total, mx);
        return ans;
    }

    private int[] dfs(int x, int fa, List<Integer>[] g, int[] group, int[] total, int mx) {
        int[] cntX = new int[mx + 1];
        cntX[group[x]] = 1;
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            int[] cntY = dfs(y, x, g, group, total, mx);
            for (int i = 0; i <= mx; i++) {
                ans += (long) cntY[i] * (total[i] - cntY[i]);
                cntX[i] += cntY[i];
            }
        }
        return cntX;
    }


}









