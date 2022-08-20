package LeetcodeMaster.BinaryTree;

import java.util.Stack;

public class Q18_ValidBST {
//    98.驗證二叉搜索樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0098.%E9%AA%8C%E8%AF%81%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91.md
//
//    給定一個二叉樹，判斷其是否是一個有效的二叉搜索樹。
//
//    假設一個二叉搜索樹具有如下特征：
//
//    節點的左子樹只包含小於當前節點的數。
//    節點的右子樹只包含大於當前節點的數。
//    所有左子樹和右子樹自身必須也是二叉搜索樹。


    // 遞歸
    TreeNode max;

    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;

        // 左
        boolean left = isValidBST(root.left);
        if (!left) {
            return false;
        }
        // 中
        if (max != null && root.value <= max.value) {
            return false;
        }
        max = root;

        // 右
        boolean right = isValidBST(root.right);
        return right;
    }

    // 迭代
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;// 左
            }
            // 中，處理
            TreeNode pop = stack.pop();
            if (pre != null && pop.value <= pre.value) {
                return false;
            }
            pre = pop;

            root = pop.right;// 右
        }
        return true;
    }
}
