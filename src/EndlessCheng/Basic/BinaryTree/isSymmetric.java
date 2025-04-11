package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

public class isSymmetric {

    // https://leetcode.cn/problems/symmetric-tree/solutions/2015063/ru-he-ling-huo-yun-yong-di-gui-lai-kan-s-6dq5/
    public boolean isSymmetric(TreeNode root) {
        return isSameTree(root.left, root.right);
    }

    // 在【100. 相同的樹】的基礎上稍加改動
    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;
        return p.val == q.val && isSameTree(p.left, q.right) && isSameTree(p.right, q.left);
    }

}
