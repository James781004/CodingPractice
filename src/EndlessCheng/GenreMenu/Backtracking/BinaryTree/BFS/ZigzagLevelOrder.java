package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.*;

public class ZigzagLevelOrder {

    // https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/solutions/2049827/bfs-wei-shi-yao-yao-yong-dui-lie-yi-ge-s-xlv3/
    // 兩個數組
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) return List.of();
        List<List<Integer>> ans = new ArrayList<>();
        List<TreeNode> cur = new ArrayList<>();
        cur.add(root);
        while (!cur.isEmpty()) {
            List<TreeNode> nxt = new ArrayList<>();
            List<Integer> vals = new ArrayList<>(cur.size()); // 容量已知
            for (TreeNode node : cur) {
                vals.add(node.val);
                if (node.left != null) nxt.add(node.left);
                if (node.right != null) nxt.add(node.right);
            }
            cur = nxt;
            if (ans.size() % 2 > 0) Collections.reverse(vals);
            ans.add(vals);
        }
        return ans;
    }


    // 一個隊列
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        if (root == null) return List.of();
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            int n = q.size();
            List<Integer> vals = new ArrayList<>(n); // 容量已知
            while (n-- > 0) {
                TreeNode node = q.poll();
                vals.add(node.val);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            if (ans.size() % 2 > 0) Collections.reverse(vals);
            ans.add(vals);
        }
        return ans;
    }


}
