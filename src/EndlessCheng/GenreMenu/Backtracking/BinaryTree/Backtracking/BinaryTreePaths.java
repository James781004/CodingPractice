package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Backtracking;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {

    // https://leetcode.cn/problems/binary-tree-paths/solutions/3038189/liang-chong-xie-fa-lu-jing-wei-can-shu-h-q2wz/
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ans = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(root, ans, path);
        return ans;
    }

    private void dfs(TreeNode node, List<String> ans, List<String> path) {
        if (node == null) {
            return;
        }
        path.add(String.valueOf(node.val));
        if (node.left == node.right) { // 葉子節點
            ans.add(String.join("->", path));
        } else {
            dfs(node.left, ans, path);
            dfs(node.right, ans, path);
        }
        path.remove(path.size() - 1); // 恢復現場
    }

}
