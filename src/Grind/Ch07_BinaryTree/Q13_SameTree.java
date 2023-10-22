package Grind.Ch07_BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q13_SameTree {
    // https://leetcode.cn/problems/same-tree/solutions/651957/dai-ma-sui-xiang-lu-100-xiang-tong-de-sh-2k5k/
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        else if (q == null || p == null) return false;
        else if (q.val != p.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // 迭代法
    public boolean isSameTree2(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        Queue<TreeNode> que = new LinkedList<TreeNode>();
        que.offer(p);
        que.offer(q);
        while (!que.isEmpty()) {
            TreeNode leftNode = que.poll();
            TreeNode rightNode = que.poll();
            if (leftNode == null && rightNode == null) continue;
            if (leftNode == null || rightNode == null || leftNode.val != rightNode.val) return false;
            que.offer(leftNode.left);
            que.offer(rightNode.left);
            que.offer(leftNode.right);
            que.offer(rightNode.right);
        }
        return true;
    }
}
