package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoodSubtreeSum {

    // https://leetcode.cn/problems/maximum-good-subtree-score/solutions/3695614/shu-xing-dp-mei-ju-zi-ji-by-endlesscheng-e2je/
    private static final int MOD = 1_000_000_007;
    private static final int D = 10;
    private long ans = 0;

    public int goodSubtreeSum(int[] vals, int[] par) {
        int n = par.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[par[i]].add(i);
        }
        dfs(0, vals, g);
        return (int) (ans % MOD);
    }

    private int[] dfs(int x, int[] vals, List<Integer>[] g) {
        int[] f = new int[1 << D];

        // 計算 vals[x] 的數位集合 mask
        int mask = 0;
        for (int v = vals[x]; v > 0; v /= D) {
            int d = v % D;
            if ((mask >> d & 1) > 0) { // d 在集合 mask 中
                mask = 0; // 不符合要求
                break;
            }
            mask |= 1 << d; // 把 d 加到集合 mask 中
        }

        if (mask > 0) {
            f[mask] = vals[x];
        }

        // 同一個集合 i 至多選一個，直接取 max
        for (int y : g[x]) {
            int[] fy = dfs(y, vals, g);
            for (int i = 0; i < f.length; i++) {
                f[i] = Math.max(f[i], fy[i]);
            }
        }

        int mx = 0;
        for (int i = 3; i < f.length; i++) {
            // 枚舉集合 i 的非空真子集 sub
            for (int sub = i & (i - 1); sub > 0; sub = (sub - 1) & i) {
                f[i] = Math.max(f[i], f[sub] + f[i ^ sub]);
            }
            mx = Math.max(mx, f[i]);
        }
        ans += mx;

        return f;
    }


}
