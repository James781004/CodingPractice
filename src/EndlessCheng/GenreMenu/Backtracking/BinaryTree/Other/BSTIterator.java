package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class BSTIterator {

    // https://leetcode.cn/problems/binary-search-tree-iterator/solutions/684405/xiang-jie-ru-he-dui-die-dai-ban-de-zhong-4rxj/
    Deque<TreeNode> d = new ArrayDeque<>();

    public BSTIterator(TreeNode root) {
        // 步驟 1
        dfsLeft(root);
    }

    public int next() {
        // 步驟 2
        TreeNode root = d.pollLast();
        int ans = root.val;
        // 步驟 3
        root = root.right;
        // 步驟 1
        dfsLeft(root);
        return ans;
    }

    void dfsLeft(TreeNode root) {
        while (root != null) {
            d.addLast(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !d.isEmpty();
    }


}
