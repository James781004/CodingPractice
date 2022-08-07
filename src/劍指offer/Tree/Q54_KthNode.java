package 劍指offer.Tree;

public class Q54_KthNode {
    private TreeNode ret;
    private int cnt = 0;

    public TreeNode KthNode(TreeNode pRoot, int k) {
        inOrder(pRoot, k);
        return ret;
    }

    private void inOrder(TreeNode root, int k) {
        if (root == null || cnt >= k) return;
        // 利用二叉查找树中序遍历有序的特点。
        inOrder(root.left, k);
        cnt++;
        if (cnt == k) ret = root;
        inOrder(root.right, k);
    }
}

