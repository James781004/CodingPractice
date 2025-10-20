package EndlessCheng.GenreMenu.Backtracking.BinaryTree.InsertDeleteTreeNode;

import EndlessCheng.TreeNode;

public class TrimBST {

    // https://leetcode.cn/problems/trim-a-binary-search-tree/solutions/868113/dai-ma-sui-xiang-lu-dai-ni-xue-tou-er-ch-mebi/
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }
        if (root.val < low) {
            return trimBST(root.right, low, high);
        }
        if (root.val > high) {
            return trimBST(root.left, low, high);
        }
        // root在[low,high]范圍內
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }
}
