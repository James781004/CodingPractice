package LeetcodeMaster.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q30_SameTree {
//    100. 相同的樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0100.%E7%9B%B8%E5%90%8C%E7%9A%84%E6%A0%91.md
//
//    給定兩個二叉樹，編寫一個函數來檢驗它們是否相同。
//
//    如果兩個樹在結構上相同，並且節點具有相同的值，則認為它們是相同的。


    // 递归法
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        else if (p == null || q == null) return false;
        else if (p.value != q.value) return false;
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
            if (leftNode == null || rightNode == null || leftNode.value != rightNode.value) return false;
            que.offer(leftNode.left);
            que.offer(rightNode.left);
            que.offer(leftNode.right);
            que.offer(rightNode.right);
        }
        return true;
    }
}
