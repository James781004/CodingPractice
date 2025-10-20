package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class WidthOfBinaryTree {

    // https://leetcode.cn/problems/maximum-width-of-binary-tree/solutions/1779284/by-muse-77-7hwf/
    public int widthOfBinaryTree(TreeNode root) {
        int result = 0;
        Deque<TreeNode> deque = new ArrayDeque();
        deque.addLast(new TreeNode(1, root.left, root.right));
        while (!deque.isEmpty()) {
            int count = deque.size(), startIndex = -1, endIndex = -1;
            for (int i = 0; i < count; i++) {
                TreeNode node = deque.pop();
                endIndex = node.val;
                if (startIndex == -1) startIndex = node.val;
                if (node.left != null) deque.addLast(new TreeNode(node.val * 2, node.left.left, node.left.right));
                if (node.right != null)
                    deque.addLast(new TreeNode(node.val * 2 + 1, node.right.left, node.right.right));
            }
            result = Math.max(result, endIndex - startIndex + 1);
        }
        return result;
    }


    // 深度優先 + 節點編號
    int result = 0;
    Map<Integer, Integer> minValue = new HashMap();

    public int widthOfBinaryTreeDFS(TreeNode root) {
        depth(root, 1, 0);
        return result;
    }

    public void depth(TreeNode node, int nodeIndex, int level) {
        if (node == null) return;
        minValue.putIfAbsent(level, nodeIndex);
        result = Math.max(result, nodeIndex - minValue.get(level) + 1);
        depth(node.left, 2 * nodeIndex, level + 1);
        depth(node.right, 2 * nodeIndex + 1, level + 1);
    }

}
