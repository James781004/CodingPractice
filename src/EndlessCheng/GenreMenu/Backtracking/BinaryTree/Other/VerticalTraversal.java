package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VerticalTraversal {

    // https://leetcode.cn/problems/vertical-order-traversal-of-a-binary-tree/solutions/2638913/si-chong-xie-fa-dfsha-xi-biao-shuang-shu-tg6q/
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        Map<Integer, List<int[]>> groups = new TreeMap<>();
        dfs(root, 0, 0, groups);

        List<List<Integer>> ans = new ArrayList<>(groups.size());
        for (List<int[]> g : groups.values()) {
            g.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
            List<Integer> vals = new ArrayList<>(g.size());
            for (int[] p : g) {
                vals.add(p[1]);
            }
            ans.add(vals);
        }
        return ans;
    }

    private void dfs(TreeNode node, int row, int col, Map<Integer, List<int[]>> groups) {
        if (node == null) {
            return;
        }
        // col 相同的分到同一組
        groups.computeIfAbsent(col, k -> new ArrayList<>()).add(new int[]{row, node.val});
        dfs(node.left, row + 1, col - 1, groups);
        dfs(node.right, row + 1, col + 1, groups);
    }


}
