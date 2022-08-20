package LeetcodeMaster.BinaryTree;

import java.util.Deque;
import java.util.LinkedList;

public class Q06_MaxDepth {
//    104.二叉樹的最大深度
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0104.%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E6%9C%80%E5%A4%A7%E6%B7%B1%E5%BA%A6.md
//
//    給定一個二叉樹，找出其最大深度。
//
//    二叉樹的深度為根節點到最遠葉子節點的最長路徑上的節點數。
//
//    說明: 葉子節點是指沒有子節點的節點。

    /**
     * 遞歸法
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }


    /**
     * 叠代法，使用層序遍歷
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        int depth = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            depth++; // 記錄深度
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();

                if (node.left != null) {
                    deque.offer(node.left);
                }

                if (node.right != null) {
                    deque.offer(node.right);
                }
            }
        }
        return depth;
    }

    // LC559.n叉樹的最大深度
    class NTreeNode {
        public int value;
        public NTreeNode[] children;

        public NTreeNode(int value) {
            this.value = value;
        }
    }

    public int maxDepthNTree(NTreeNode root) {
        if (root == null) return 0;
        int depth = 0;
        for (int i = 0; i < root.children.length; i++) {
            depth = Math.max(depth, maxDepthNTree(root.children[i]));
        }
        return 1 + depth;
    }

    public int maxDepthNTree2(NTreeNode root) {
        if (root == null) return 0;
        Deque<NTreeNode> que = new LinkedList<>();
        NTreeNode cur;
        int depth = 0;
        while (!que.isEmpty()) {
            int size = que.size();
            depth++;
            for (int i = 0; i < size; i++) {
                cur = que.poll();
                for (int j = 0; j < cur.children.length; j++) {
                    if (cur.children[j] != null) que.offer(cur.children[j]);
                }
            }
        }
        return depth;
    }
}
