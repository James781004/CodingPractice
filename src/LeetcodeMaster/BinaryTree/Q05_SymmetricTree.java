package LeetcodeMaster.BinaryTree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Q05_SymmetricTree {
//    101. 對稱二叉樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0101.%E5%AF%B9%E7%A7%B0%E4%BA%8C%E5%8F%89%E6%A0%91.md
//
//    給定一個二叉樹，檢查它是否是鏡像對稱的。


    /**
     * 遞歸法
     */
    public boolean isSymmetric1(TreeNode root) {
        return compare(root.left, root.right);
    }

    private boolean compare(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null || left.value != right.value) return false;

        // 比較外側
        boolean compareOutside = compare(left.left, right.right);

        // 比較內側
        boolean compareInside = compare(left.right, right.left);

        return compareOutside && compareInside;
    }


    /**
     * 叠代法
     * 使用雙端隊列，相當於兩個棧
     */
    public boolean isSymmetric2(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root.left);
        deque.offer(root.right);

        while (!deque.isEmpty()) {
            TreeNode leftNode = deque.pollFirst();
            TreeNode rightNode = deque.pollLast();
            if (leftNode == null && rightNode == null) {
                continue;
            }

//            if (leftNode == null && rightNode != null) {
//                return false;
//            }
//            if (leftNode != null && rightNode == null) {
//                return false;
//            }
//            if (leftNode.val != rightNode.val) {
//                return false;
//            }
            // 以上三個判斷條件合並
            if (leftNode == null || rightNode == null || leftNode.value != rightNode.value) {
                return false;
            }

            deque.offerFirst(leftNode.left);
            deque.offerFirst(leftNode.right);
            deque.offerLast(rightNode.right);
            deque.offerLast(rightNode.left);
        }
        return true;
    }


    /**
     * 叠代法
     * 使用普通隊列
     */
    public boolean isSymmetric3(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        while (!queue.isEmpty()) {
            TreeNode leftNode = queue.poll();
            TreeNode rightNode = queue.poll();
            if (leftNode == null && rightNode == null) {
                continue;
            }
//            if (leftNode == null && rightNode != null) {
//                return false;
//            }
//            if (leftNode != null && rightNode == null) {
//                return false;
//            }
//            if (leftNode.val != rightNode.val) {
//                return false;
//            }
            // 以上三個判斷條件合並
            if (leftNode == null || rightNode == null || leftNode.value != rightNode.value) {
                return false;
            }

            // 這里順序與使用Deque不同
            queue.offer(leftNode.left);
            queue.offer(rightNode.right);
            queue.offer(leftNode.right);
            queue.offer(rightNode.left);
        }
        return true;
    }
}
