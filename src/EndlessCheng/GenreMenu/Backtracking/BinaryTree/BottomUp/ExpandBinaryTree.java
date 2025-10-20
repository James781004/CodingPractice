package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class ExpandBinaryTree {

    // https://leetcode.cn/problems/KnLfVT/solutions/1875286/jijian-by-endlesscheng-4oqo/
    public TreeNode expandBinaryTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) return root;

        // 題目要求：若一個節點擁有父節點，則在該節點和他的父節點之間插入一個燈飾
        // （即插入一個值為 -1 的節點）。
        // 父節點左子樹之間與新插入一個值為 -1 的節點，原本左子樹設為新節點的左子樹即可
        if (root.left != null) root.left = new TreeNode(-1, expandBinaryTree(root.left), null);

        // 父節點右子樹之間新插入一個值為 -1 的節點，原本右子樹設為新節點的右子樹即可
        if (root.right != null) root.right = new TreeNode(-1, null, expandBinaryTree(root.right));

        return root;
    }
}
