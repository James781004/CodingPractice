package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaxLevelSum {

    // https://leetcode.cn/problems/maximum-level-sum-of-a-binary-tree/solutions/1710307/pythonjavatypescriptgo-by-himymben-dhhb/
    // BFS統計每一層的和，返回和最大且層數最小的那個即可
    public int maxLevelSum(TreeNode root) {
        int ans = 1, num = Integer.MIN_VALUE, level = 1;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int sum = 0;
            for (int i = 0, size = queue.size(); i < size; i++) {
                TreeNode node = queue.removeFirst();
                sum += node.val;
                if (node.left != null) {
                    queue.addLast(node.left);
                }
                if (node.right != null) {
                    queue.addLast(node.right);
                }
            }
            if (sum > num) {
                ans = level;
                num = sum;
            }
            level++;
        }
        return ans;
    }


}
