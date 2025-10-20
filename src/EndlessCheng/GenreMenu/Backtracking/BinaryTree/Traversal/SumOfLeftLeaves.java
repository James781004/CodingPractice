package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Traversal;

import EndlessCheng.TreeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SumOfLeftLeaves {

    // https://leetcode.cn/problems/sum-of-left-leaves/solutions/866969/dai-ma-sui-xiang-lu-dai-ni-xue-tou-er-ch-j6f9/
    public int sumOfLeftLeavesDFS(TreeNode root) {
        if (root == null) return 0;
        int leftValue = sumOfLeftLeavesDFS(root.left);    // 左
        int rightValue = sumOfLeftLeavesDFS(root.right);  // 右

        int midValue = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) {
            midValue = root.left.val;
        }
        int sum = midValue + leftValue + rightValue;  // 中
        return sum;
    }


    public int sumOfLeftLeavesStack(TreeNode root) {
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        int result = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null && node.left.left == null && node.left.right == null) {
                result += node.left.val;
            }
            if (node.right != null) stack.add(node.right);
            if (node.left != null) stack.add(node.left);
        }
        return result;
    }

    public int sumOfLeftLeavesBFS(TreeNode root) {
        int sum = 0;
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) { // 左節點不為空
                    queue.offer(node.left);
                    if (node.left.left == null && node.left.right == null) { // 左葉子節點
                        sum += node.left.val;
                    }
                }
                if (node.right != null) queue.offer(node.right);
            }
        }
        return sum;
    }

}
