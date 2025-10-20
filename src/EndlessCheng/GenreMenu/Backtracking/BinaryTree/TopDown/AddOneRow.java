package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class AddOneRow {

    // https://leetcode.cn/problems/add-one-row-to-tree/solutions/1723129/by-tong-zhu-xdks/
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) {
            return new TreeNode(val, root, null);
        }
        dfs(root, val, depth, 1); // 根節點 root 位於深度 1
        return root;
    }

    private void dfs(TreeNode node, int val, int depth, int level) {
        if (node == null) {
            return;
        }

        // 到了 depth -1 層做特殊邏輯處理
        if (depth - 1 == level) {
            node.left = new TreeNode(val, node.left, null);
            node.right = new TreeNode(val, null, node.right);
            return;
        }

        dfs(node.left, val, depth, level + 1);
        dfs(node.right, val, depth, level + 1);
    }


    public TreeNode addOneRowBFS(TreeNode root, int val, int depth) {
        if (depth == 1) {
            return new TreeNode(val, root, null);
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                // 只需要在層序遍歷的基礎上加這個特殊判斷
                if (depth - 1 == level) {
                    node.left = new TreeNode(val, node.left, null);
                    node.right = new TreeNode(val, null, node.right);
                } else {
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
            }
        }

        return root;
    }

}
