package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class IsUnivalTree {

    // https://leetcode.cn/problems/univalued-binary-tree/solutions/1514160/pythonjavajavascriptgo-by-himymben-tvtg/
    public boolean isUnivalTree(TreeNode root) {
        return dfs(root, root.val);
    }

    private boolean dfs(TreeNode node, int val) {
        return node == null || (node.val == val && dfs(node.left, val) && dfs(node.right, val));
    }

}
