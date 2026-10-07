package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestArithSeqLength {

    // https://leetcode.cn/problems/longest-arithmetic-subsequence/solutions/2239191/ji-yi-hua-sou-suo-di-tui-chang-shu-you-h-czvx/
    private Map<Integer, Integer>[] maxLen;
    private int[] a;
    private int ans;

    public int longestArithSeqLength(int[] nums) {
        a = nums;
        int n = a.length;
        maxLen = new HashMap[n];
        for (int i = 1; i < n; ++i)
            dfs(i);
        return ans;
    }

    private Map<Integer, Integer> dfs(int i) {
        if (maxLen[i] != null) return maxLen[i]; // 之前算過了
        // i=0 時不會進入循環
        maxLen[i] = new HashMap<>();
        for (int j = i - 1; j >= 0; --j) {
            int d = a[i] - a[j]; // 公差
            if (!maxLen[i].containsKey(d)) {
                maxLen[i].put(d, dfs(j).getOrDefault(d, 1) + 1);
                ans = Math.max(ans, maxLen[i].get(d));
            }
        }
        return maxLen[i];
    }


    public int longestArithSeqLengthDP(int[] a) {
        int ans = 0, n = a.length;
        Map<Integer, Integer>[] f = new HashMap[n];
        Arrays.setAll(f, e -> new HashMap<>());
        for (int i = 1; i < n; ++i)
            for (int j = i - 1; j >= 0; --j) {
                int d = a[i] - a[j]; // 公差
                if (!f[i].containsKey(d)) {
                    f[i].put(d, f[j].getOrDefault(d, 1) + 1);
                    ans = Math.max(ans, f[i].get(d));
                }
            }
        return ans;
    }


    public int longestArithSeqLengthDP2(int[] a) {
        int ans = 0, n = a.length;
        var f = new int[n][1001];
        for (int i = 1; i < n; ++i)
            for (int j = i - 1; j >= 0; --j) {
                int d = a[i] - a[j] + 500; // +500 防止出現負數
                if (f[i][d] == 0) {
                    f[i][d] = f[j][d] + 1; // 默認的 1 在下面返回時加上
                    ans = Math.max(ans, f[i][d]);
                }
            }
        return ans + 1;
    }


}
