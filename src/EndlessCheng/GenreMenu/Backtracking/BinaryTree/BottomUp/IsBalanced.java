package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class IsBalanced {

    // https://leetcode.cn/problems/balanced-binary-tree/solutions/8737/balanced-binary-tree-di-gui-fang-fa-by-jin40789108/

    // 後序遍歷 + 剪枝 （從底至頂）
    public boolean isBalanced(TreeNode root) {
        return recur(root) != -1;
    }

    private int recur(TreeNode root) {
        if (root == null) return 0;
        int left = recur(root.left);
        if (left == -1) return -1;
        int right = recur(root.right);
        if (right == -1) return -1;

        // 左右子樹高度差大於1，return -1表示已經不是平衡樹了
        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
    }


    // 先序遍歷 + 判斷深度 （從頂至底）
    public boolean isBalanced2(TreeNode root) {
        if (root == null) return true;

        // 比較某子樹的左右子樹的深度差 abs(depth(root.left) - depth(root.right)) <= 1
        // 若所有子樹都平衡，則此樹平衡
        return Math.abs(depth(root.left) - depth(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    // 獲取當前子樹的深度
    private int depth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(depth(root.left), depth(root.right)) + 1;
    }

}
