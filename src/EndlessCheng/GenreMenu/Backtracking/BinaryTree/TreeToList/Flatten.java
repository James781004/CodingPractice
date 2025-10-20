package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeToList;

import EndlessCheng.TreeNode;

public class Flatten {

    // https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/solutions/2992172/liang-chong-fang-fa-tou-cha-fa-fen-zhi-p-h9bg/
    private TreeNode head;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.right);
        flatten(root.left);
        root.left = null;
        root.right = head; // 頭插法，相當於鏈表的 root.next = head
        head = root; // 現在鏈表頭節點是 root
    }


    public void flatten2(TreeNode root) {
        dfs(root);
    }

    private TreeNode dfs(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode leftTail = dfs(root.left);
        TreeNode rightTail = dfs(root.right);
        if (leftTail != null) {
            leftTail.right = root.right; // 左子樹鏈表 -> 右子樹鏈表
            root.right = root.left; // 當前節點 -> 左右子樹合並後的鏈表
            root.left = null;
        }

        // 鏈表合並完成後，返回合並後的鏈表的尾節點，也就是右子樹鏈表的尾節點
        // 如果右子樹是空的，則返回左子樹鏈表的尾節點
        // 如果左右子樹都是空的，返回當前節點
        return rightTail != null ? rightTail : leftTail != null ? leftTail : root;
    }


}
