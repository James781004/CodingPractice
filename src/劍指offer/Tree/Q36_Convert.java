package 劍指offer.Tree;

public class Q36_Convert {
    private TreeNode pre = null;
    private TreeNode head = null;

    public TreeNode Convert(TreeNode root) {
        inOrder(root);
        return head;
    }

    private void inOrder(TreeNode node) {
        if (node == null) return;
        inOrder(node.left);
        node.left = pre;
        if (pre != null) pre.right = node;

        pre = node;
        if (head == null) head = node;
        inOrder(node.right);
    }
}
