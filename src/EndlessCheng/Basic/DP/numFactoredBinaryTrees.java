package EndlessCheng.Basic.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class numFactoredBinaryTrees {

    // https://leetcode.cn/problems/binary-trees-with-factors/solutions/2416115/cong-ji-yi-hua-sou-suo-dao-di-tui-jiao-n-nbk6/
    public int numFactoredBinaryTrees(int[] arr) {
        final long MOD = (long) 1e9 + 7;
        Arrays.sort(arr);
        int n = arr.length;
        Map<Integer, Integer> idx = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            idx.put(arr[i], i);
        }

        long[] memo = new long[n];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        long ans = 0;
        for (int i = 0; i < n; i++) { // 從根節點開始遞歸構建二叉樹
            ans += dfs(i, arr, memo, idx);
        }
        return (int) (ans % MOD);
    }

    private long dfs(int i, int[] arr, long[] memo, Map<Integer, Integer> idx) {
        if (memo[i] != -1) // 之前計算過
            return memo[i];
        int val = arr[i];
        long res = 1;
        for (int j = 0; j < i; ++j) { // 因為有排序過，所以 val 的因子一定比 val 小
            int x = arr[j];
            if (val % x == 0 && idx.containsKey(val / x)) { // 另一個因子 val/x 必須在 arr 中
                res += dfs(j, arr, memo, idx) * dfs(idx.get(val / x), arr, memo, idx);
            }
        }
        return memo[i] = res; // 記憶化
    }


    public int numFactoredBinaryTreesDP(int[] arr) {
        final long MOD = (long) 1e9 + 7;
        Arrays.sort(arr);
        int n = arr.length;
        Map<Integer, Integer> idx = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            idx.put(arr[i], i);
        }
        long ans = 0;
        long[] f = new long[n];
        for (int i = 0; i < n; i++) {
            int val = arr[i];
            f[i] = 1;
            for (int j = 0; j < i; ++j) { // val 的因子一定比 val 小
                int x = arr[j];
                if (val % x == 0 && idx.containsKey(val / x)) { // 另一個因子 val/x 必須在 arr 中
                    f[i] += f[j] * f[idx.get(val / x)];
                }
            }
            ans += f[i];
        }
        return (int) (ans % MOD);
    }


}
