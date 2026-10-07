package EndlessCheng.GenreMenu.DP.Linear.LCS.Advance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MaxRemovals {


    // https://leetcode.cn/problems/find-maximum-removals-from-source-string/solutions/2948631/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-h94l/
    public int maxRemovals(String source, String pattern, int[] targetIndices) {
        Set<Integer> targetSet = new HashSet<>();
        for (int index : targetIndices) {
            targetSet.add(index);
        }
        int n = source.length();
        int m = pattern.length();
        int[][] memo = new int[n][m + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, m - 1, source.toCharArray(), pattern.toCharArray(), targetSet, memo);
    }

    private int dfs(int i, int j, char[] source, char[] pattern, Set<Integer> targetSet, int[][] memo) {
        if (i < j) {
            return Integer.MIN_VALUE;
        }
        if (i < 0) {
            return 0;
        }
        // j+1 避免數組越界
        if (memo[i][j + 1] != -1) { // 之前計算過
            return memo[i][j + 1];
        }
        int res = dfs(i - 1, j, source, pattern, targetSet, memo) + (targetSet.contains(i) ? 1 : 0);
        if (j >= 0 && source[i] == pattern[j]) {
            res = Math.max(res, dfs(i - 1, j - 1, source, pattern, targetSet, memo));
        }
        return memo[i][j + 1] = res; // 記憶化
    }


}
