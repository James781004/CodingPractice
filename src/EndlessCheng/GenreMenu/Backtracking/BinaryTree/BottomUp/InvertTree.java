package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class InvertTree {

    // https://leetcode.cn/problems/invert-binary-tree/solutions/2713610/shi-pin-shen-ru-li-jie-di-gui-pythonjava-zhqh/
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left); // 翻轉左子樹的結果
        TreeNode right = invertTree(root.right); // 翻轉右子樹的結果
        root.left = right; // 交換左右兒子
        root.right = left;
        return root;
    }

    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tmp = root.left; // 交換左右兒子
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left); // 翻轉左子樹
        invertTree(root.right); // 翻轉右子樹
        return root;
    }

}
