package LeetcodeMaster.BinaryTree;

import java.util.Deque;
import java.util.LinkedList;

public class Q07_MinDepth {
//    111.二叉樹的最小深度
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0111.%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E6%9C%80%E5%B0%8F%E6%B7%B1%E5%BA%A6.md
//
//    給定一個二叉樹，找出其最小深度。
//
//    最小深度是從根節點到最近葉子節點的最短路徑上的節點數量。
//
//    說明: 葉子節點是指沒有子節點的節點。


    /**
     * 遞歸法，相比求MaxDepth要覆雜點
     * 因為最小深度是從根節點到最近**葉子節點**的最短路徑上的節點數量
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);

        // 如果一個左子樹為空，但右不為空，這時並不是最低點
        if (root.left == null) {
            return rightDepth + 1;
        }

        // 如果一個右子樹為空，但左不為空，這時並不是最低點
        if (root.right == null) {
            return leftDepth + 1;
        }

        return Math.min(leftDepth, rightDepth) + 1;
    }

    /**
     * 叠代法，層序遍歷
     */
    public int minDepth2(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> que = new LinkedList<>();
        que.offer(root);
        int depth = 0;
        TreeNode cur;
        while (!que.isEmpty()) {
            int size = que.size();
            depth++;
            for (int i = 0; i < size; i++) {
                cur = que.poll();

                // 左右為空表示是葉子結點，直接返回depth，
                // 因為從上往下層序遍歷，該值必定就是最小值，不用再往下找
                if (cur.left == null && cur.right == null) return depth;

                if (cur.left != null) que.offer(cur.left);
                if (cur.right != null) que.offer(cur.right);
            }
        }

        return depth;
    }
}
