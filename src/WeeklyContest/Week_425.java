package WeeklyContest;

import java.util.*;

public class Week_425 {

    // https://leetcode.cn/problems/minimum-positive-sum-subarray/solutions/2998908/liang-chong-fang-fa-bao-li-mei-ju-qian-z-ndz5/
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        Integer[] a = nums.toArray(Integer[]::new);
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= a.length - l; i++) {
            int s = 0;
            for (int j = i; j < a.length && j - i + 1 <= r; j++) {
                s += a[j];
                if (s > 0 && j - i + 1 >= l) {
                    ans = Math.min(ans, s);
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 前綴和 + 有序集合
    // 找到一個小於 s[j] 且離 s[j] 最近的前綴和 s[i]，滿足 l≤j−i≤r
    // 枚舉 j，那麼 i 需要滿足 j−r≤i≤j−l
    public int minimumSumSubarrayw(List<Integer> nums, int l, int r) {
        Integer[] a = nums.toArray(Integer[]::new);
        int ans = Integer.MAX_VALUE;
        int n = a.length;
        int[] s = new int[n + 1];
        TreeMap<Integer, Integer> cnt = new TreeMap<>(); // 有序集合
        for (int j = 1; j <= n; j++) {
            s[j] = s[j - 1] + a[j - 1]; // 前綴和
            if (j < l) {
                continue;
            }
            cnt.merge(s[j - l], 1, Integer::sum); // cnt[s[j-l]]++
            Integer lower = cnt.lowerKey(s[j]);
            if (lower != null) {
                ans = Math.min(ans, s[j] - lower);
            }
            if (j >= r) {
                int v = s[j - r];
                int c = cnt.get(v);
                if (c == 1) {
                    cnt.remove(v);
                } else {
                    cnt.put(v, c - 1);
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    // https://leetcode.cn/problems/rearrange-k-substrings-to-form-target-string/solutions/2998881/pai-xu-pythonjavacgo-by-endlesscheng-wslh/
    public boolean isPossibleToRearrange(String s, String t, int k) {
        List<String> a = new ArrayList<>(k); // 預分配空間
        List<String> b = new ArrayList<>(k);
        int n = s.length();
        k = n / k;
        for (int i = k; i <= n; i += k) {
            a.add(s.substring(i - k, i));
            b.add(t.substring(i - k, i));
        }
        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }


    // https://leetcode.cn/problems/minimum-array-sum/solutions/2998867/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-0pc5/
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int n = nums.length;
        int[][][] memo = new int[n][op1 + 1][op2 + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示沒有計算過
            }
        }
        return dfs(n - 1, op1, op2, k, nums, memo);
    }

    private int dfs(int i, int op1, int op2, int k, int[] nums, int[][][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][op1][op2] != -1) { // 之前計算過
            return memo[i][op1][op2];
        }
        int x = nums[i];
        int res = dfs(i - 1, op1, op2, k, nums, memo) + x; // 不操作
        if (op1 > 0) { // 執行操作 1
            res = Math.min(res, dfs(i - 1, op1 - 1, op2, k, nums, memo) + (x + 1) / 2);
        }
        if (op2 > 0 && x >= k) {
            res = Math.min(res, dfs(i - 1, op1, op2 - 1, k, nums, memo) + x - k); // 執行操作 2
            if (op1 > 0) { // 執行操作 1 和操作 2
                int y = (x - k + 1) / 2; // 先減再除
                if ((x + 1) / 2 >= k) {
                    y = Math.min(y, (x + 1) / 2 - k); // 先除再減
                }
                res = Math.min(res, dfs(i - 1, op1 - 1, op2 - 1, k, nums, memo) + y);
            }
        }
        return memo[i][op1][op2] = res; // 記憶化
    }


    // https://leetcode.cn/problems/maximize-sum-of-weights-after-edge-removals/solutions/2998845/shu-xing-dp-tan-xin-pythonjavacgo-by-end-i3g3/
    public long maximizeSumOfWeights(int[][] edges, int k) {
        List<int[]>[] g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            g[x].add(new int[]{y, wt});
            g[y].add(new int[]{x, wt});
        }
        long[] res = dfs(0, -1, g, k);
        return Math.max(res[0], res[1]);
    }

    private long[] dfs(int x, int fa, List<int[]>[] g, int k) {
        long notChoose = 0;
        List<Long> inc = new ArrayList<>();
        for (int[] e : g[x]) {
            int y = e[0];
            if (y == fa) {
                continue;
            }
            long[] res = dfs(y, x, g, k);
            notChoose += res[0]; // 先都不選
            long d = res[1] + e[1] - res[0];
            if (d > 0) {
                inc.add(d);
            }
        }

        // 再選增量最大的 k 個或者 k-1 個
        inc.sort(Collections.reverseOrder());
        for (int i = 0; i < Math.min(inc.size(), k - 1); i++) {
            notChoose += inc.get(i);
        }
        long choose = notChoose;
        if (inc.size() >= k) {
            notChoose += inc.get(k - 1);
        }
        return new long[]{notChoose, choose};
    }


}






