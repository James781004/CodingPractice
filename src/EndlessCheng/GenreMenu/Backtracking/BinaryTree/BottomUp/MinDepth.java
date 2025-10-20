package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class MinDepth {

    // https://leetcode.cn/problems/minimum-depth-of-binary-tree/solutions/2730984/liang-chong-fang-fa-zi-ding-xiang-xia-zi-0sxz/
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == root.right) { // root 是葉子
            return 1;
        }
        int leftDepth = root.left != null ? minDepth(root.left) : Integer.MAX_VALUE;
        int rightDepth = root.right != null ? minDepth(root.right) : Integer.MAX_VALUE;
        return Math.min(leftDepth, rightDepth) + 1;
    }

}
