package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class IsCompleteTree {

    // https://leetcode.cn/problems/check-completeness-of-a-binary-tree/solutions/614651/ceng-xu-bian-li-by-dian-dao-de-hu-die-681d/
    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean reachedEnd = false;
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();

            // 對於一個完全二叉樹，層序遍歷的過程中遇到第一個空節點之後不應該再出現非空節點
            if (reachedEnd && cur != null) {
                return false;
            }
            if (cur == null) {
                reachedEnd = true;
                continue;
            }
            q.offer(cur.left);
            q.offer(cur.right);
        }
        return true;
    }

}
