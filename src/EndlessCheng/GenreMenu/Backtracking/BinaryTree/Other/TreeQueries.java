package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class TreeQueries {

    // https://leetcode.cn/problems/height-of-binary-tree-after-subtree-removal-queries/solutions/1936338/liang-bian-dfspythonjavacgo-by-endlessch-vvs4/
    private Map<TreeNode, Integer> height = new HashMap<>(); // 每棵子樹的高度
    private int[] res; // 每個節點的答案

    public int[] treeQueries(TreeNode root, int[] queries) {
        getHeight(root);
        height.put(null, 0); // 簡化 dfs 的代碼，這樣不用寫 getOrDefault
        res = new int[height.size()];
        dfs(root, -1, 0);
        for (var i = 0; i < queries.length; i++)
            queries[i] = res[queries[i]];
        return queries;
    }

    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        var h = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        height.put(node, h);
        return h;
    }

    private void dfs(TreeNode node, int depth, int restH) {
        if (node == null) return;
        ++depth;
        res[node.val] = restH;
        dfs(node.left, depth, Math.max(restH, depth + height.get(node.right)));
        dfs(node.right, depth, Math.max(restH, depth + height.get(node.left)));
    }


}
