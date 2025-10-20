package EndlessCheng.GenreMenu.Backtracking.NormalTree.DivideAndConquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxTargetNodes {

    // https://leetcode.cn/problems/maximize-the-number-of-target-nodes-after-connecting-trees-i/solutions/3006334/nao-jin-ji-zhuan-wan-bao-li-mei-ju-pytho-ua6k/
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;

        int max2 = 0;
        if (k > 0) {
            List<Integer>[] g = buildTree(edges2);
            diameter = 0;
            dfs(0, -1, g);

            if (diameter < k) {
                max2 = m; // 第二棵樹的每個節點都是目標節點
            } else {
                for (int i = 0; i < m; i++) {
                    max2 = Math.max(max2, dfs(i, -1, 0, g, k - 1));
                }
            }
        }

        List<Integer>[] g = buildTree(edges1);
        diameter = 0;
        dfs(0, -1, g);

        int[] ans = new int[n];
        if (diameter <= k) {
            Arrays.fill(ans, n + max2); // 第一棵樹的每個節點都是目標節點
        } else {
            for (int i = 0; i < ans.length; i++) {
                ans[i] = dfs(i, -1, 0, g, k) + max2;
            }
        }
        return ans;
    }

    private List<Integer>[] buildTree(int[][] edges) {
        List<Integer>[] g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        return g;
    }

    private int diameter;

    // 求樹的直徑
    private int dfs(int x, int fa, List<Integer>[] g) {
        int maxLen = 0;
        for (int y : g[x]) {
            if (y != fa) {
                int subLen = dfs(y, x, g) + 1;
                diameter = Math.max(diameter, maxLen + subLen);
                maxLen = Math.max(maxLen, subLen);
            }
        }
        return maxLen;
    }

    private int dfs(int x, int fa, int d, List<Integer>[] g, int k) {
        if (d > k) {
            return 0;
        }
        int cnt = 1;
        for (int y : g[x]) {
            if (y != fa) {
                cnt += dfs(y, x, d + 1, g, k);
            }
        }
        return cnt;
    }


}
