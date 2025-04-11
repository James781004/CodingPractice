package EndlessCheng.Basic.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class longestPath {

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
        return ans + 1;
    }

    private int dfs(int x) {
        int maxLen = 0;
        for (int y : g[x]) {
            int len = dfs(y) + 1; // 從 y 出發的最長路徑加上 maxLen，再加上 1（邊 x−y）
            if (s[y] != s[x]) { // 本題限制相鄰節點字符都不同
                ans = Math.max(ans, maxLen + len); // 更新答案為從 y 出發的最長路徑加上 maxLen，再加上 1（邊 x−y）
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }


}
