package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class IncreasingBST {

    // https://leetcode.cn/problems/increasing-order-search-tree/solutions/742305/gong-shui-san-xie-yi-ti-shuang-jie-di-gu-yc8l/
    List<TreeNode> list = new ArrayList<>();

    public TreeNode increasingBST(TreeNode root) {
        dfs(root);
        TreeNode dummy = new TreeNode(-1);
        TreeNode cur = dummy;
        for (TreeNode node : list) {
            cur.right = node;
            node.left = null;
            cur = node;
        }
        return dummy.right;
    }

    void dfs(TreeNode root) {
        if (root == null) return;
        dfs(root.left);
        list.add(root);
        dfs(root.right);
    }


    // 迭代
    //    List<TreeNode> list = new ArrayList<>();

    public TreeNode increasingBS2(TreeNode root) {
        Deque<TreeNode> d = new ArrayDeque<>();
        while (root != null || !d.isEmpty()) {
            while (root != null) {
                d.add(root);
                root = root.left;
            }
            root = d.pollLast();
            list.add(root);
            root = root.right;
        }
        TreeNode dummy = new TreeNode(-1);
        TreeNode cur = dummy;
        for (TreeNode node : list) {
            cur.right = node;
            node.left = null;
            cur = node;
        }
        return dummy.right;
    }


}
