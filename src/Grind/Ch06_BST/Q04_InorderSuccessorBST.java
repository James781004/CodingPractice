package Grind.Ch06_BST;

public class Q04_InorderSuccessorBST {
    // https://ttzztt.gitbooks.io/lc/content/inorder-successor-in-bst.html
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode suc = null;
        while (root != null) {
            if (p.val < root.val) { // 當前 root 確定在 p 右邊，姑且先把 root 設定為前驅節點
                suc = root;
                root = root.left; // root 繼續向左走（縮小），看看有沒有更小的前驅節點
            } else {
                root = root.right; // root 太小，向右走找更大的選項
            }
        }
        return suc;
    }
}
