package LeetcodeMaster.BinaryTree;

import java.util.ArrayDeque;

public class Q04_InvertTree {
    //    226.翻轉二叉樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0226.%E7%BF%BB%E8%BD%AC%E4%BA%8C%E5%8F%89%E6%A0%91.md
//
//    翻轉一棵二叉樹。


    /**
     * 前後序遍歷都可以
     * 中序不行，因為先左孩子交換孩子，再根交換孩子（做完後，右孩子已經變成了原來的左孩子），再右孩子交換孩子（此時其實是對原來的左孩子做交換）
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        invertTree(root.left);
        invertTree(root.right);
        swap(root);
        return root;
    }


    public TreeNode invertTreeBFS(TreeNode root) {
        if (root == null) return null;
        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            while (size-- > 0) {
                TreeNode node = deque.poll();
                swap(node);
                if (node.left != null) {
                    deque.offer(node.left);
                }
                if (node.right != null) {
                    deque.offer(node.right);
                }
            }
        }
        return root;
    }

    private void swap(TreeNode root) {
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }

}
