package EndlessCheng.GenreMenu.DP.StateCompression.ContourLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PyramidTransition {

    // https://leetcode.cn/problems/pyramid-transition-matrix/solutions/3866474/tu-jie-hui-su-vis-you-hua-pythonjavacgo-9kcb9/
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        List<Integer>[][] groups = new ArrayList[7][7];
        for (List<Integer>[] row : groups) {
            Arrays.setAll(row, v -> new ArrayList<>());
        }
        for (String S : allowed) {
            char[] s = S.toCharArray();
            // A~F -> 1~6
            groups[s[0] & 31][s[1] & 31].add(s[2] & 31);
        }

        char[] s = bottom.toCharArray();
        int n = s.length;
        int[] pyramid = new int[n];
        for (int i = 0; i < n; i++) {
            pyramid[n - 1] |= (s[i] & 31) << (i * 3); // 等價於 pyramid[n-1][i] = s[i]&31
        }

        boolean[] vis = new boolean[1 << ((n - 1) * 3)];

        return dfs(n - 2, 0, pyramid, vis, groups);
    }

    private boolean dfs(int i, int j, int[] pyramid, boolean[] vis, List<Integer>[][] groups) {
        if (i < 0) {
            return true;
        }

        if (vis[pyramid[i]]) {
            return false;
        }

        if (j == i + 1) {
            vis[pyramid[i]] = true;
            return dfs(i - 1, 0, pyramid, vis, groups);
        }

        for (int top : groups[pyramid[i + 1] >> (j * 3) & 7][pyramid[i + 1] >> ((j + 1) * 3) & 7]) {
            if (j > 0 && groups[pyramid[i] >> ((j - 1) * 3) & 7][top].isEmpty()) {
                continue;
            }
            pyramid[i] &= ~(7 << (j * 3)); // 清除之前填的字母，等價於 pyramid[i][j] = 0
            pyramid[i] |= top << (j * 3); // 等價於 pyramid[i][j] = top
            if (dfs(i, j + 1, pyramid, vis, groups)) {
                return true;
            }
        }
        return false;
    }


}
