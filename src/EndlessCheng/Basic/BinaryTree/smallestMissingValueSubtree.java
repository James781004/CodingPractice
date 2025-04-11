package EndlessCheng.Basic.BinaryTree;

import java.util.*;

public class smallestMissingValueSubtree {

    // https://leetcode.cn/problems/smallest-missing-genetic-value-in-each-subtree/solutions/2505883/tu-jie-yi-zhang-tu-miao-dong-duo-chong-x-q095/
    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        int n = parents.length;
        int[] ans = new int[n];
        Arrays.fill(ans, 1);
        int node = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                node = i; // 出發點
                break;
            }
        }
        if (node < 0) { // 不存在基因值為 1 的節點
            return ans;
        }

        // 建樹
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 1; i < n; ++i) {
            g[parents[i]].add(i);
        }

        Set<Integer> vis = new HashSet<>();
        int mex = 2; // 缺失的最小基因值
        while (node >= 0) {
            dfs(node, g, vis, nums);
            while (vis.contains(mex)) { // node 子樹包含這個基因值
                mex++;
            }
            ans[node] = mex; // 缺失的最小基因值
            node = parents[node]; // 往上走
        }
        return ans;
    }

    // 遍歷 x 子樹
    private void dfs(int x, List<Integer>[] g, Set<Integer> vis, int[] nums) {
        vis.add(nums[x]); // 標記基因值
        for (int son : g[x]) {
            if (!vis.contains(nums[son])) {
                dfs(son, g, vis, nums);
            }
        }
    }


}
