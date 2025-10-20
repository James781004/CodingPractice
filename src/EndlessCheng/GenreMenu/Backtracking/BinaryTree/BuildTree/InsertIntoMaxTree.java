package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

public class InsertIntoMaxTree {

    // https://leetcode.cn/problems/maximum-binary-tree-ii/solutions/1785544/by-ac_oier-v82s/
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        // val 是當前樹的最大值
        if (root == null || val > root.val) {
            TreeNode node = new TreeNode(val);
            node.left = root;
            return node;
        } else {
            // 遞歸查找插入位置
            root.right = insertIntoMaxTree(root.right, val);
            return root;
        }
    }


}
