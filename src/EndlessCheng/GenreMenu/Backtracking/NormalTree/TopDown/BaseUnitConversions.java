package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseUnitConversions {

    // https://leetcode.cn/problems/unit-conversion-i/solutions/3663077/zi-ding-xiang-xia-dfspythonjavacgo-by-en-muwq/
    public int[] baseUnitConversions(int[][] conversions) {
        int n = conversions.length + 1;
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : conversions) {
            g[e[0]].add(new int[]{e[1], e[2]});
        }

        int[] ans = new int[n];
        dfs(0, 1, g, ans);
        return ans;
    }

    private void dfs(int x, long mul, List<int[]>[] g, int[] ans) {
        ans[x] = (int) mul;
        for (int[] e : g[x]) {
            dfs(e[0], mul * e[1] % 1_000_000_007, g, ans);
        }
    }


}
