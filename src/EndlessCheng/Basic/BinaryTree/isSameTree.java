package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

public class isSameTree {

    // https://leetcode.cn/problems/same-tree/solutions/2015056/ru-he-ling-huo-yun-yong-di-gui-lai-kan-s-empk/
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q; // 必須都是 null
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

}
