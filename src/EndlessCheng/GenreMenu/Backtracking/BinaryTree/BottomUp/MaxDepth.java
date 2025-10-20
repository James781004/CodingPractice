package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class MaxDepth {

    // https://leetcode.cn/problems/maximum-depth-of-binary-tree/solutions/10740/hua-jie-suan-fa-104-er-cha-shu-de-zui-da-shen-du-b/
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int left = maxDepth(root.left);
            int right = maxDepth(root.right);
            return Math.max(left, right) + 1;
        }
    }

}
