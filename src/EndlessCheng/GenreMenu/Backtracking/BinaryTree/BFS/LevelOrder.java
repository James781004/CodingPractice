package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LevelOrder {

    // https://leetcode.cn/problems/binary-tree-level-order-traversal/solutions/2049807/bfs-wei-shi-yao-yao-yong-dui-lie-yi-ge-s-xlpz/
    // 兩個數組
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        List<List<Integer>> ans = new ArrayList<>();
        List<TreeNode> cur = List.of(root);
        while (!cur.isEmpty()) {
            List<TreeNode> nxt = new ArrayList<>();
            List<Integer> vals = new ArrayList<>(cur.size()); // 預分配空間
            for (TreeNode node : cur) {
                vals.add(node.val);
                if (node.left != null) nxt.add(node.left);
                if (node.right != null) nxt.add(node.right);
            }
            cur = nxt;
            ans.add(vals);
        }
        return ans;
    }


    // 隊列
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            int n = q.size();
            List<Integer> vals = new ArrayList<>(n); // 預分配空間
            while (n-- > 0) {
                TreeNode node = q.poll();
                vals.add(node.val);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            ans.add(vals);
        }
        return ans;
    }

}
