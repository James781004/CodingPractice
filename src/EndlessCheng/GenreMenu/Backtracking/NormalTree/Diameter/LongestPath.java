package EndlessCheng.GenreMenu.Backtracking.NormalTree.Diameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestPath {

    // https://leetcode.cn/problems/longest-path-with-different-adjacent-characters/solutions/1427611/by-endlesscheng-92fw/
    private List<Integer>[] g;
    private char[] s;
    private int ans;

    public int longestPath(int[] parent, String s) {
        this.s = s.toCharArray();
        int n = parent.length;
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i);
        }
        dfs(0);
        return ans + 1; // 本題求的是點的個數，所以答案為最長路徑的長度加一
    }

    private int dfs(int x) {
        int maxLen = 0;

        // 枚舉子樹 x 的所有子樹 y，維護從 x 出發的最長路徑 maxLen
        for (int y : g[x]) {
            // 更新答案為從 y 出發的最長路徑加上 maxLen，
            // 再加上 1（邊 x−y），即合並從 x 出發的兩條路徑
            int len = dfs(y) + 1;
            if (s[y] != s[x]) {
                ans = Math.max(ans, maxLen + len);
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }


}
