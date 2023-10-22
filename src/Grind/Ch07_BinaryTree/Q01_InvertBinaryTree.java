package Grind.Ch07_BinaryTree;

import java.util.LinkedList;

public class Q01_InvertBinaryTree {
    // https://leetcode.cn/problems/invert-binary-tree/solutions/73159/dong-hua-yan-shi-liang-chong-shi-xian-226-fan-zhua/
    public TreeNode invertTree(TreeNode root) {
        // 遞歸函數的終止條件，節點為空時返回
        if (root == null) {
            return null;
        }
        // 下面三句是將當前節點的左右子樹交換
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = tmp;
        // 遞歸交換當前節點的 左子樹
        invertTree(root.left);
        // 遞歸交換當前節點的 右子樹
        invertTree(root.right);
        // 函數返回時就表示當前這個節點，以及它的左右子樹
        // 都已經交換完了
        return root;
    }


    public TreeNode invertTreeBFS(TreeNode root) {
        if (root == null) {
            return null;
        }
        // 將二叉樹中的節點逐層放入隊列中，再迭代處理隊列中的元素
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 每次都從隊列中拿一個節點，並交換這個節點的左右子樹
            TreeNode tmp = queue.poll();
            TreeNode left = tmp.left;
            tmp.left = tmp.right;
            tmp.right = left;
            // 如果當前節點的左子樹不為空，則放入隊列等待後續處理
            if (tmp.left != null) {
                queue.add(tmp.left);
            }
            // 如果當前節點的右子樹不為空，則放入隊列等待後續處理
            if (tmp.right != null) {
                queue.add(tmp.right);
            }

        }
        //返回處理完的根節點
        return root;
    }
}
