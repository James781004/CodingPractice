package 劍指offer.Tree;

public class Q27_Mirror {

    public TreeNode Mirror(TreeNode root) {
        if (root == null) return null;
        swap(root);
        Mirror(root.left);
        Mirror(root.right);
        return root;
    }

    private void swap(TreeNode root) {
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
    }
}
