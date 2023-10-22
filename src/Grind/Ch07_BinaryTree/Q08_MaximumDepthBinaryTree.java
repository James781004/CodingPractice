package Grind.Ch07_BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q08_MaximumDepthBinaryTree {
    // https://leetcode.cn/problems/maximum-depth-of-binary-tree/solutions/1323631/acm-xuan-shou-tu-jie-leetcode-er-cha-shu-ckf4/
    // https://leetcode.cn/problems/maximum-depth-of-binary-tree/solutions/1323631/acm-xuan-shou-tu-jie-leetcode-er-cha-shu-ckf4/
    public int maxDepth(TreeNode root) {
        // 節點為空，高度為 0
        if (root == null) {
            return 0;
        }
        // 遞歸計算左子樹的最大深度
        int leftHeight = maxDepth(root.left);
        // 遞歸計算右子樹的最大深度
        int rightHeight = maxDepth(root.right);
        // 二叉樹的最大深度 = 子樹的最大深度 + 1（1 是根節點）
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int maxDepthBFS(TreeNode root) {
        // 空樹，高度為 0
        if (root == null) {
            return 0;
        }
        // 初始化隊列和層次
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;

        // 當隊列不為空
        while (!queue.isEmpty()) {
            // 當前層的節點數
            int n = queue.size();
            // 彈出當前層的所有節點，並將所有子節點入隊列
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            depth++;
        }
        // 二叉樹最大層次即為二叉樹最深深度
        return depth;
    }
}
