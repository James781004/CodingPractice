package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class PruneTree {

    // https://leetcode.cn/problems/binary-tree-pruning/solutions/1685957/er-cha-shu-jianzhi-by-capital-worker-vj5w/
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if (root.val == 0 && root.left == null && root.right == null) {
            return null;
        } else {
            return root;
        }
    }


}
