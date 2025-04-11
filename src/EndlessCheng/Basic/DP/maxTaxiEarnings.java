package EndlessCheng.Basic.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class maxTaxiEarnings {

    // https://leetcode.cn/problems/maximum-earnings-from-taxi/solutions/2558504/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-k15a/
    public long maxTaxiEarnings(int n, int[][] rides) {
        List<int[]>[] groups = new ArrayList[n + 1];
        for (int[] r : rides) {
            int start = r[0], end = r[1], tip = r[2];
            if (groups[end] == null) {
                groups[end] = new ArrayList<>();
            }
            groups[end].add(new int[]{start, end - start + tip}); // [起點, 盈利]
        }

        long[] memo = new long[n + 1];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs(n, memo, groups);
    }

    private long dfs(int i, long[] memo, List<int[]>[] groups) {
        if (i == 1) {
            return 0;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        long res = dfs(i - 1, memo, groups);
        if (groups[i] != null) {
            for (int[] p : groups[i]) {
                res = Math.max(res, dfs(p[0], memo, groups) + p[1]);
            }
        }
        return memo[i] = res; // 記憶化
    }


    public long maxTaxiEarningsDP(int n, int[][] rides) {
        List<int[]>[] groups = new ArrayList[n + 1];
        for (int[] r : rides) {
            int start = r[0], end = r[1], tip = r[2];
            if (groups[end] == null) {
                groups[end] = new ArrayList<>();
            }
            groups[end].add(new int[]{start, end - start + tip});
        }

        long[] f = new long[n + 1];
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1];
            if (groups[i] != null) {
                for (int[] p : groups[i]) {
                    f[i] = Math.max(f[i], f[p[0]] + p[1]);
                }
            }
        }
        return f[n];
    }


}
