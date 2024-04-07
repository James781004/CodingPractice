package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_392 {
    // https://leetcode.cn/problems/longest-strictly-increasing-or-strictly-decreasing-subarray/solutions/2727306/yi-ci-bian-li-by-sleepy-herschelouf-j8q0/
    public int longestMonotonicSubarray(int[] a) {
        int ans = 1, n = a.length;
        int cnt1 = 1, cnt2 = 1;
        for (int i = 1; i < n; ++i) {
            // 分組討論
            // 統計遞減數組長度
            if (a[i] < a[i - 1])
                cnt1++;
            else // 遞減數組結束，重設長度
                cnt1 = 1;

            // 統計遞增數組長度
            if (a[i] > a[i - 1])
                cnt2++;
            else // 遞增數組結束，重設長度
                cnt2 = 1;

            // 保存最長數組長度
            ans = Math.max(ans, Math.max(cnt1, cnt2));
        }
        return ans;
    }


    // https://leetcode.cn/problems/lexicographically-smallest-string-after-operations-with-constraint/solutions/2727203/tan-xin-pythonjavacgo-by-endlesscheng-vzgo/
    public String getSmallestString(String s, int k) {
        char[] t = s.toCharArray();

        // 從左到右遍歷 s
        for (int i = 0; i < t.length; i++) {
            int dis = Math.min(t[i] - 'a', 'z' - t[i] + 1);

            // 如果 s[i] 到 a 的操作次數超過 k，把 s[i] 減少 k，退出循環
            if (dis > k) {
                t[i] -= k;
                break;
            }

            // 如果 s[i] 到 a 的操作次數不超過 k，那麼把 s[i] 變成 a，同時 k 減去操作次數
            t[i] = 'a';
            k -= dis;
        }
        return new String(t);
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-median-of-array-equal-to-k/solutions/2727218/pai-xu-kuai-su-xuan-ze-pythonjavacgo-by-23yt5/
    public long minOperationsToMakeMedianK(int[] nums, int k) {
        Arrays.sort(nums);
        long ans = 0;
        int m = nums.length / 2;
        if (nums[m] > k) {
            for (int i = m; i >= 0 && nums[i] > k; i--) {
                ans += nums[i] - k;
            }
        } else {
            for (int i = m; i < nums.length && nums[i] < k; i++) {
                ans += k - nums[i];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-cost-walk-in-weighted-graph/solutions/2727290/xian-xing-zuo-fa-dfspythonjavacgo-by-end-i0gg/
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1], w = e[2];
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }

        int[] ids = new int[n]; // 記錄每個點所在連通塊的編號
        Arrays.fill(ids, -1);
        List<Integer> ccAnd = new ArrayList<>(); // 記錄每個連通塊的邊權的 AND
        for (int i = 0; i < n; i++) {
            if (ids[i] < 0) {
                ccAnd.add(dfs(i, ccAnd.size(), g, ids));
            }
        }

        int[] ans = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            int s = query[i][0], t = query[i][1];
            ans[i] = s == t ? 0 : ids[s] != ids[t] ? -1 : ccAnd.get(ids[s]);
        }
        return ans;
    }

    private int dfs(int x, int curId, List<int[]>[] g, int[] ids) {
        ids[x] = curId; // 記錄每個點所在連通塊的編號
        int and = -1;
        for (int[] e : g[x]) {
            and &= e[1];
            if (ids[e[0]] < 0) { // 沒有訪問過
                and &= dfs(e[0], curId, g, ids);
            }
        }
        return and;
    }

    public int[] minimumCostUF(int n, int[][] edges, int[][] query) {
        int[] fa = new int[n];
        for (int i = 0; i < n; i++) {
            fa[i] = i;
        }
        int[] and_ = new int[n];
        Arrays.fill(and_, -1);

        for (int[] e : edges) {
            int x = find(e[0], fa);
            int y = find(e[1], fa);
            and_[y] &= e[2];
            if (x != y) {
                and_[y] &= and_[x];
                fa[x] = y;
            }
        }

        int[] ans = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            int s = query[i][0], t = query[i][1];
            ans[i] = s == t ? 0 : find(s, fa) != find(t, fa) ? -1 : and_[find(s, fa)];
        }
        return ans;
    }

    private int find(int x, int[] fa) {
        if (fa[x] != x) {
            fa[x] = find(fa[x], fa);
        }
        return fa[x];
    }
}


