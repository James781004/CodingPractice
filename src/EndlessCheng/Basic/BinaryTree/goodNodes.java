package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

public class goodNodes {

    // https://leetcode.cn/problems/count-good-nodes-in-binary-tree/solutions/2403677/jian-ji-xie-fa-pythonjavacgojs-by-endles-gwxt/
    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE); // 也可以寫 root.val
    }

    private int dfs(TreeNode root, int mx) {
        if (root == null) return 0;
        int left = dfs(root.left, Math.max(mx, root.val));
        int right = dfs(root.right, Math.max(mx, root.val));
        return left + right + (mx <= root.val ? 1 : 0);
    }

}
