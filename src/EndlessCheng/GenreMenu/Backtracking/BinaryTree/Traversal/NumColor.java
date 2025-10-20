package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Traversal;

import EndlessCheng.TreeNode;

import java.util.HashSet;
import java.util.Set;

public class NumColor {

    // https://leetcode.cn/problems/sZ59z6/solutions/1016949/go-dfsha-xi-biao-by-endlesscheng-lqli/
    public int numColor(TreeNode root) {
        Set<Integer> s = new HashSet<>();
        dfs(root, s);
        return s.size();
    }

    private void dfs(TreeNode node, Set<Integer> s) {
        if (node == null) {
            return;
        }
        s.add(node.val);
        dfs(node.left, s);
        dfs(node.right, s);
    }

}
