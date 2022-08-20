package LeetcodeMaster.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Q11_SumOfLeftLeaves {
//    404.左葉子之和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0404.%E5%B7%A6%E5%8F%B6%E5%AD%90%E4%B9%8B%E5%92%8C.md
//
//    計算給定二叉樹的所有左葉子之和。

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        int leftValue = sumOfLeftLeaves(root.left);
        int rightValue = sumOfLeftLeaves(root.right);
        int midValue = 0;

        // 找到左葉節點，midValue賦值
        if (root.left != null && root.left.left == null && root.left.right == null) {
            midValue = root.left.value;
        }

        int sum = midValue + leftValue + rightValue;
        return sum;
    }


    public int sumOfLeftLeavesStack(TreeNode root) {
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        int res = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null && node.left.left == null && node.left.right == null) {
                res += node.left.value; // 找到左葉節點，res賦值，加上node的左節點值
            }
            if (node.right != null) stack.add(node.right);
            if (node.left != null) stack.add(node.left);
        }
        return res;
    }


    public int sumOfLeftLeavesLevel(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode cur = null;
        int res = 0, size = 0;
        while (!queue.isEmpty()) {
            size = queue.size();
            while (size-- > 0) {
                cur = queue.poll();
                if (cur.left != null) {
                    queue.offer(cur.left);
                    if (cur.left.left == null || cur.left.right == null) {
                        res += cur.left.value; // 找到左葉節點，res賦值，加上cur的左節點值
                    }
                }

                if (cur.right != null) queue.offer(cur.right);
            }
        }
        return res;
    }

}
