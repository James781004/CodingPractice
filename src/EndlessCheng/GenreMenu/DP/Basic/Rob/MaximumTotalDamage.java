package EndlessCheng.GenreMenu.DP.Basic.Rob;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaximumTotalDamage {

    // https://leetcode.cn/problems/maximum-total-damage-with-spell-casting/solutions/2812389/tao-lu-da-jia-jie-she-pythonjavacgo-by-e-p9b5/
    public long maximumTotalDamage(int[] power) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : power) {
            cnt.merge(x, 1, Integer::sum);
        }

        int n = cnt.size();
        int[] a = new int[n];
        int k = 0;
        for (int x : cnt.keySet()) {
            a[k++] = x;
        }
        Arrays.sort(a);

        long[] memo = new long[n];
        Arrays.fill(memo, -1);
        return dfs(a, cnt, memo, n - 1);
    }

    private long dfs(int[] a, Map<Integer, Integer> cnt, long[] memo, int i) {
        if (i < 0) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int x = a[i];
        int j = i;
        while (j > 0 && a[j - 1] >= x - 2) {
            j--;
        }
        return memo[i] = Math.max(dfs(a, cnt, memo, i - 1), dfs(a, cnt, memo, j - 1) + (long) x * cnt.get(x));
    }


    public long maximumTotalDamageDP(int[] power) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : power) {
            cnt.merge(x, 1, Integer::sum);
        }

        int n = cnt.size();
        int[] a = new int[n];
        int k = 0;
        for (int x : cnt.keySet()) {
            a[k++] = x;
        }
        Arrays.sort(a);

        long[] f = new long[n + 1];
        int j = 0;
        for (int i = 0; i < n; i++) {
            int x = a[i];
            while (a[j] < x - 2) {
                j++;
            }
            f[i + 1] = Math.max(f[i], f[j] + (long) x * cnt.get(x));
        }
        return f[n];
    }


}
