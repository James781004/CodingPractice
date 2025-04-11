package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

import java.util.*;

public class levelOrderBottom {

    // https://leetcode.cn/problems/binary-tree-level-order-traversal-ii/solutions/2640451/bfs-wei-shi-yao-yao-yong-dui-lie-yi-ge-s-wmul/
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return List.of();
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
        Collections.reverse(ans);
        return ans;
    }

}
