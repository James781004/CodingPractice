package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxKDivisibleComponents {

    // https://leetcode.cn/problems/maximum-number-of-k-divisible-components/solutions/2464687/pan-duan-zi-shu-dian-quan-he-shi-fou-wei-uvsg/
    private List<Integer>[] g;
    private int[] values;
    private int k, ans;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        this.values = values;
        this.k = k;
        dfs(0, -1);
        return ans;
    }

    private long dfs(int x, int fa) {
        long sum = values[x];
        for (int y : g[x]) {
            if (y != fa) {
                sum += dfs(y, x);
            }
        }
        ans += sum % k == 0 ? 1 : 0;
        return sum;
    }


}
