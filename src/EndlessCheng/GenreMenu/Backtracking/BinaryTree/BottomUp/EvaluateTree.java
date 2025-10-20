package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class EvaluateTree {

    // https://leetcode.cn/problems/evaluate-boolean-binary-tree/solutions/1658331/by-endlesscheng-391l/
    public boolean evaluateTree(TreeNode root) {
        // 等價於 if(root.left == null && root.right == null)
        if (root.left == root.right) {
            return root.val == 1;
        }

        if (root.val == 2) {
            return evaluateTree(root.left) || evaluateTree(root.right);
        }

        return evaluateTree(root.left) && evaluateTree(root.right);
    }

}
