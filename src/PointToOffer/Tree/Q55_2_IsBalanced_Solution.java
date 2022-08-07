package PointToOffer.Tree;

public class Q55_2_IsBalanced_Solution {
    private boolean isBalanced = true;

    public boolean IsBalanced_Solution(TreeNode root) {
        height(root);
        return isBalanced;
    }

    private int height(TreeNode root) {
        if (root == null || isBalanced) return 0;
        int left = height(root.left);
        int right = height(root.right);
        // 判断左右子树的深度之差有没有超过1，若超过了则不是平衡的，反之则为平衡二叉树。
        if (Math.abs(left - right) > 1) isBalanced = false;
        return 1 + Math.max(left, right);
    }

}
