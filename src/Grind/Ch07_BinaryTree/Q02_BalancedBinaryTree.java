package Grind.Ch07_BinaryTree;

import java.util.Stack;

public class Q02_BalancedBinaryTree {
    // https://leetcode.cn/problems/balanced-binary-tree/solutions/866942/dai-ma-sui-xiang-lu-dai-ni-xue-tou-er-ch-x3bv/

    /**
     * 遞歸法
     */
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = getHeight(root.right);
        if (rightHeight == -1) {
            return -1;
        }
        // 左右子樹高度差大於1，return -1表示已經不是平衡樹了
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * 優化迭代法，針對暴力迭代法的getHeight方法做優化，利用TreeNode.val來保存當前結點的高度，這樣就不會有重復遍歷
     * 獲取高度算法時間復雜度可以降到O(1)，總的時間復雜度降為O(n)。
     * 時間復雜度：O(n)
     */
    public boolean isBalancedBFS(TreeNode root) {
        if (root == null) {
            return true;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            TreeNode inNode = stack.peek();
            // 右結點為null或已經遍歷過
            if (inNode.right == null || inNode.right == pre) {
                // 輸出
                if (Math.abs(getHeight2(inNode.left) - getHeight2(inNode.right)) > 1) {
                    return false;
                }
                stack.pop();
                pre = inNode;
                root = null;// 當前結點下，沒有要遍歷的結點了
            } else {
                root = inNode.right;// 右結點還沒遍歷，遍歷右結點
            }
        }
        return true;
    }


    /**
     * 求結點的高度
     */
    public int getHeight2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = root.left != null ? root.left.val : 0;
        int rightHeight = root.right != null ? root.right.val : 0;
        int height = Math.max(leftHeight, rightHeight) + 1;
        root.val = height;// 用TreeNode.val來保存當前結點的高度
        return height;
    }
}
