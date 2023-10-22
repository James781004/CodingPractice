package Grind.Ch07_BinaryTree;

public class Q18_SubtreeOfAnotherTree {
    // https://leetcode.cn/problems/subtree-of-another-tree/solutions/235760/java-di-gui-ban-by-kelly2018/
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (t == null) return true;   // t 為 null 一定都是 true
        if (s == null) return false;  // 這裡 t 一定不為 null, 只要 s 為 null，肯定是 false
        return isSubtree(s.left, t) || isSubtree(s.right, t) || isSameTree(s, t);
    }

    /**
     * 判斷兩棵樹是否相同
     */
    public boolean isSameTree(TreeNode s, TreeNode t) {
        if (s == null && t == null) return true;
        if (s == null || t == null) return false;
        if (s.val != t.val) return false;
        return isSameTree(s.left, t.left) && isSameTree(s.right, t.right);
    }
}
