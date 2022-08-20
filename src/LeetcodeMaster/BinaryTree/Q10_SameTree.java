package LeetcodeMaster.BinaryTree;

public class Q10_SameTree {
    // LC 100
    public boolean isSameTree(TreeNode r1, TreeNode r2) {
        if (r1 == null && r2 == null) return true;
        if (r1 == null || r2 == null || r1.value != r2.value) return false;
        return isSameTree(r1.left, r2.left) && isSameTree(r1.right, r2.right);
    }
}
