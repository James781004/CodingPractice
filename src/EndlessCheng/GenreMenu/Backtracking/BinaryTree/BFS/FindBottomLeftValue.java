package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class FindBottomLeftValue {

    // https://leetcode.cn/problems/find-bottom-left-tree-value/solutions/2049776/bfs-wei-shi-yao-yao-yong-dui-lie-yi-ge-s-f34y/
    // BFS 這棵二叉樹，先把右兒子入隊，再把左兒子入隊，這樣最後一個出隊的節點就是左下角的節點了
    public int findBottomLeftValue(TreeNode root) {
        TreeNode node = root;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            node = q.poll();
            if (node.right != null) q.add(node.right);
            if (node.left != null) q.add(node.left);
        }
        return node.val;
    }


}
